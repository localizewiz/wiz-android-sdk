package com.localizewiz.localizewiz

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.Log
import com.localizewiz.localizewiz.models.Project
import com.localizewiz.localizewiz.models.Workspace
import com.localizewiz.localizewiz.models.responses.LocalizedStringEnvelope
import com.localizewiz.localizewiz.network.WizApiService
import com.localizewiz.localizewiz.util.ResourceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Wiz private constructor(internal var context: Context) {

    private val LOG_TAG = "Wizzie"
    private var resources: Resources? = null
    private var config: Config? = null
    private var apiService: WizApiService? = null
    private var resourceManager: ResourceManager? = null
    private var currentLanguageCode = "es"
    var project: Project? = null
        private  set
    var workspace: Workspace? = null
        private set

    var listeners: MutableList<()->Unit> = mutableListOf()

    companion object {
        @Volatile var instance: Wiz? = null
            private set

        private const val KEY_API_KEY = "com.localizewiz.wiz.apiKey"
        private const val KEY_PROJECT_ID = "com.localizewiz.wiz.projectId"

        private fun createInstance(context: Context): Wiz {
            return instance ?: synchronized(this) {
                return instance ?: Wiz(context)
            }
        }

        fun getManifestInstance(context: Context): Wiz =
            instance ?: synchronized(this) {
                instance ?: setup(context).also { instance = it }
            }

        private fun setup(context: Context): Wiz {
            val newInstance = Wiz(context)
            newInstance.config = readConfigSettings(context)
            newInstance.loadProject()
            return newInstance
        }

        private fun readConfigSettings(context: Context) : Config {
            val metadata = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).metaData
            val apiKey = metadata.getString(KEY_API_KEY) ?: ""
            var projectId = metadata.getString(KEY_PROJECT_ID) ?: ""
            Log.d("Wiz.object", "config pros {apiKey = $apiKey, projectId = $projectId}")
            return Config(apiKey, projectId.toString())
        }


        fun configure(context: Context, apiKey: String, projectId: String, language: String? = null) {
            instance = createInstance(context)
            val config = Config(apiKey, projectId)
            instance?.config = config
            instance?.apiService = WizApiService(config = config)
            @Suppress("DEPRECATION") val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales.get(0)
            } else {
                context.resources.configuration.locale
            }
            instance?.currentLanguageCode = language ?: locale.language
            instance?.project = Project(projectId)
            Log.d("Wiz.object", "config props {apiKey = $apiKey, projectId = $projectId}")
            instance?.loadProject()
        }
    }

    fun refresh(completion: (()-> Unit)? = null) {
        val languageCode = currentLanguageCode
        refreshLanguage(languageCode, completion)
    }

    fun refreshLanguage(languageCode: String, completion: (() -> Unit)? = null) {
        project?.let { project ->
            val call = apiService?.getStringTranslations(project.id, languageCode)
            call?.enqueue(object : Callback<LocalizedStringEnvelope> {

                override fun onResponse( call: Call<LocalizedStringEnvelope>, response: Response<LocalizedStringEnvelope>) {
                    if (response.isSuccessful) {
                        val strings = response.body()?.strings
                        Log.d(LOG_TAG, "Fetched project strings: $strings")
                        strings?.let {
                            strings.forEach {
                                Log.d(LOG_TAG, "string => $it")
                            }
                            project.saveStrings(strings, languageCode)
                        }
                    }
                    notifyListeners()
                    completion?.invoke()
                }

                override fun onFailure(call: Call<LocalizedStringEnvelope>, t: Throwable) {
                    Log.e(LOG_TAG, "Get project strings failed with error: $t")
                    completion?.invoke()
                }
            })
        }
    }

    fun setLanguage(languageCode: String, completion: (()-> Unit)? = null) {
        this.currentLanguageCode = languageCode
        this.refresh(completion)
    }

    fun getString(resourceId: Int, languageCode: String): String {
        val resourceKey = context.resources.getResourceEntryName(resourceId)
        val wizString = project?.getString(resourceKey, languageCode)
        Log.d(LOG_TAG, "Getting string for {id=$resourceId, key=$resourceKey, str=$wizString}")
        return wizString ?: context.resources.getString(resourceId)
    }
    fun getString(resourceId: Int): String? {
        return getString(resourceId, currentLanguageCode)
    }

    fun getString(resourceKey: String): String {
        return getString(resourceKey, currentLanguageCode)
    }

    fun getString(resourceKey: String, languageCode: String): String {
        val wizString = project?.getString(resourceKey, languageCode)
        return wizString?: resourceKey
    }

    fun setLocale(locale: Locale) {
        var conf: Configuration = context.resources.configuration
        conf = Configuration(conf)
        conf.setLocale(locale)
        val localizedContext = context.createConfigurationContext(conf)
        this.resources = localizedContext.resources
    }

    internal fun saveCurrentProject() {

    }

    private fun loadProject() {
        config?.let {
            val call = apiService?.getProjectDetails(it.projectId)
            Log.d(LOG_TAG, "about to make network call")
            call?.enqueue(object : Callback<Project> {

                override fun onResponse(call: Call<Project>, response: Response<Project>) {
                    if (response.isSuccessful) {
                        val project = response.body()
                        Log.d(LOG_TAG, "Loading project succeeded with project: $project")
                        this@Wiz.project = project
                        this@Wiz.workspace = project?.workspace

                        this@Wiz.refresh()
                        this@Wiz.saveCurrentProject()
                    }
                }

                override fun onFailure(call: Call<Project>?, t: Throwable?) {
                    Log.e(LOG_TAG, "Loading project failed with error: $t")
                }
            })
        }
    }

    private fun notifyListeners() {
        listeners.forEach { t ->
            t.invoke()
        }
    }
}

/**
 * Usage R.string::class.java.getStringId(resourceName)
 */
internal inline fun <reified T: Class<R.string>> T.getStringId(resourceName: String): Int {
    return try {
        val idField = getDeclaredField (resourceName)
        idField.getInt(idField)
    } catch (e:Exception) {
        e.printStackTrace()
        -1
    }
}
