package com.aptopayments.core.data.config

import java.util.Locale

enum class UITheme {
    THEME_1, THEME_2;

    companion object {
        fun parseUITheme(theme: String): UITheme {
            return try {
                valueOf(theme.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                THEME_1
            }
        }
    }

}
