package com.localizewiz.wiz.models.responses

import com.localizewiz.wiz.models.Language

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
