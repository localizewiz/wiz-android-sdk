package com.localizewiz.localizewiz.models

import android.content.Context
import com.localizewiz.localizewiz.Wiz
import com.localizewiz.localizewiz.util.ResourceManager
import com.localizewiz.localizewiz.util.StringCaches
import java.util.*

class Project(
    var id: String,
    var name: String,
    var description: String,
    var languageId: String,
    var platform: String,
    var created: Date,
    var updated: Date,
    var iconUrl: String?,
    var workspaceId: String?,
    var workspace: Workspace?,
    var language: Language?,
    var languages: Array<Language>,
    var isInitialized: Boolean?
) {

    private var localizations = StringCaches
    private var resourceManager: ResourceManager? = null

    override fun toString(): String {
        return """Project: {
            |name=$name, 
            |id=$id,
            |description:$description,
            |languageId=$languageId, 
            |platform=$platform, 
            |created=$created, 
            |updated= $updated,
            |workspace=$workspace,
            |language=$language,
            |languages=$languages,
            |}
        """.trimMargin()
    }

    /**
     * Initializes the project with a set of filenames.
     *
     * @param filenames List of files you want added to this project. For example "strings.xml".
     * Files added to this list get uploaded and automatically localized to available languages.
     */
    fun initialize(filenames: Array<String> = emptyArray()) {
        this.uploadProjectFiles(filenames)
    }

    fun getString(key: String, languageCode: String): String? {
        // get configuration, resource etc.
        return StringCaches.getString(key, languageCode)
    }

    internal fun saveStrings(strings: Array<LocalizedString>, languageCode: String) {
        StringCaches.saveLocalizedStrings(strings, languageCode)
    }

    private fun uploadProjectFiles(filenames: Array<String>) {
        var context = Wiz.instance?.context
        context?.let {context ->
            filenames.forEach {filename ->
                val resId = context.resources.getIdentifier(filename, "string", context.packageName)
                this.readResourceFile(resId)
            }
        }
    }

    private fun readResourceFile(resourceId: Int) {}

}