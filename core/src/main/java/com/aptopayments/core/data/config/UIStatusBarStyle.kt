package com.aptopayments.core.data.config

enum class UIStatusBarStyle {
    LIGHT, DARK, AUTO;

    companion object {
        fun parseStatusBarStyle(style: String): UIStatusBarStyle {
            return try {
                valueOf(style.toUpperCase())
            } catch (exception: Throwable) {
                AUTO
            }
        }
    }
}
