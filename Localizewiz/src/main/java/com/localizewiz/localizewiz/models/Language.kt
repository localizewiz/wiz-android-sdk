package com.localizewiz.localizewiz.models

import java.util.*

class Language (
    var id: String,
    var isoCode: String,
    var englishName: String,
    var localName: String,
    var isChecked: Boolean?,
    var flagUrl: String?,
    var flagUrl2: String?,
    var countryFlag: String?,
    var countryCode: String?,
    var added: Date?,
    var updated: Date?
) {}