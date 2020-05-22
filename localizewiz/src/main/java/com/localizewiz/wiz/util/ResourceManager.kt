package com.localizewiz.wiz.util

import android.content.Context
import android.content.res.Configuration
import com.localizewiz.wiz.models.Project
import java.util.*


class ResourceManager (private var context: Context) {

    companion object {
        val instance: ResourceManager? = null
    }

    fun getStringResource(resourceId: Int, languageCode: String): String? {
        return this.getLocalizedContext(languageCode)?.resources?.getString(resourceId)
    }

    private fun getConfiguration(languageCode: String): Configuration {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(Locale(languageCode))
        return configuration
    }

    private fun getBaseConfiguration(project: Project): Configuration {
        return getConfiguration(project.language?.isoCode ?: "")
    }

    private fun getLocalizedContext(languageCode: String): Context? {
        val configuration: Configuration = getConfiguration(languageCode)
        return context.createConfigurationContext(configuration)
    }
}