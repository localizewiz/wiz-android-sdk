package com.localizewiz.localizewiz.models

import java.util.*

class LocalizedString (
    var id: String?,
    var name: String,
    var value: String,
    var autoTranslation: String?,
    var humanTranslation: String?,
    var comments: String?,
    var metadata: Map<String, String>?,
    var translatable: Boolean?,
    var locale: String?,
    var created: Date?,
    var updated: Date?,
    var translations: Map<String, String>?
) {
    override fun toString(): String {
        return """
            LocalizedString: {
            id: $id,
            name: $name,
            value: $value,
            autoTranslation: $autoTranslation,
            humanTranslation: $humanTranslation,
            comments: $comments,
            metadata: $metadata,
            translatable: $translatable,
            locale: $locale,
            created: $created,
            updated: $updated
            }
        """.trimIndent()
    }
}