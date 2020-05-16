package com.localizewiz.localizewiz.util

import com.localizewiz.localizewiz.models.LocalizedString

object StringCaches {

    private var caches = mutableMapOf<String, Cache>()

    fun getString(key: String, languageCode: String): String? {
        val cache = caches[languageCode]
        return cache?.get(key)?.humanTranslation
    }

    fun saveLocalizedStrings(strings: Array<LocalizedString>, languageCode: String) {
        val cache = Cache()
        strings.forEach { cache.set(it.name, it) }
        caches[languageCode] = cache
    }

    fun cacheForLanguage(languageCode: String): Cache? {
        return caches[languageCode]
    }

    fun restoreLocalizations(languageCode: String) {

    }
}

class Cache {
    private var store = mutableMapOf<String, LocalizedString>()

    fun set(key: String, value: LocalizedString) {
        store[key] = value
    }

    fun get(key: String): LocalizedString? {
        return store[key]
    }

    fun clear() {
        store.clear()
    }

    override fun toString(): String {
        return store.toString()
    }
}