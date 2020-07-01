package com.aptopayments.mobile.data.config

import java.io.Serializable

data class Branding(val light: ProjectBranding, val dark: ProjectBranding) : Serializable {

    fun getBranding(isDarkTheme: Boolean): ProjectBranding {
        return if (isDarkTheme) dark else light
    }

    companion object {
        fun createDefault(): Branding {
            return Branding(
                ProjectBranding.createDefault(),
                ProjectBranding.createDarkDefault()
            )
        }
    }
}
