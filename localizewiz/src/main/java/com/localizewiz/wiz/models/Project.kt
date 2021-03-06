package com.localizewiz.wiz.models

import com.localizewiz.wiz.Wiz
import com.localizewiz.wiz.util.ResourceManager
import com.localizewiz.wiz.util.StringCaches
import java.util.*

class Project(
    var id: String,
    var name: String? = null,
    var description: String? = null,
    var languageId: String? = null,
    var platform: String? = null,
    var created: Date? = null,
    var updated: Date? = null,
    var iconUrl: String? = null,
    var workspaceId: String? = null,
    var workspace: Workspace? = null,
    var language: Language? = null,
    var languages: Array<Language>? = null,
    var isInitialized: Boolean? = false
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