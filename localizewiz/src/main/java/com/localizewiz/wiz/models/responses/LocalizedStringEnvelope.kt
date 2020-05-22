package com.localizewiz.wiz.models.responses

import com.localizewiz.wiz.models.LocalizedString

data class LocalizedStringEnvelope( var strings: Array<LocalizedString>) {
    override fun toString(): String {
        return """strings: $strings"""
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalizedStringEnvelope

        if (!strings.contentEquals(other.strings)) return false

        return true
    }

    override fun hashCode(): Int {
        return strings.contentHashCode()
    }
}