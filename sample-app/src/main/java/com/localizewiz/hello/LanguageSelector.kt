package com.localizewiz.hello

import com.localizewiz.wiz.models.Language

interface LanguageSelector {
    fun languageSelected(language: Language)
}