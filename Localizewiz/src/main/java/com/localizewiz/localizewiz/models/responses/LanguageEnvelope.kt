package com.localizewiz.localizewiz.models.responses

import com.localizewiz.localizewiz.models.Language

data class LanguageEnvelope(var languages: Array<Language>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LanguageEnvelope

        if (!languages.contentEquals(other.languages)) return false

        return true
    }

    override fun hashCode(): Int {
        return languages.contentHashCode()
    }
}
