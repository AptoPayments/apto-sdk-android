package com.aptopayments.core.data.geo

import java.io.Serializable
import java.util.Locale

data class Country(var isoCode: String) : Serializable {
    val name: String
        get() {
            val loc = Locale("", isoCode)
            return loc.displayCountry
        }

    val flag: String
        get() = getFlagEmoji()

    private fun getFlagEmoji(): String {
        return if (isoCode.length != 2) {
            ""
        } else {
            val countryCode = isoCode.toUpperCase(Locale.US)
            val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
            String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
    }

    override fun toString() = name
}
