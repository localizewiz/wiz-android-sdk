package com.localizewiz.hello

import com.localizewiz.localizewiz.models.Language

interface LanguageSelector {
    fun languageSelected(language: Language)
}