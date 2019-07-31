package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.config.ProjectBranding
import com.aptopayments.core.data.config.UIStatusBarStyle
import com.aptopayments.core.data.config.UITheme
import com.aptopayments.core.extension.ColorParser
import com.aptopayments.core.extension.ColorParserImpl
import com.google.gson.annotations.SerializedName

const val DEFAULT_ICON_PRIMARY_COLOR = "000000"
const val DEFAULT_ICON_SECONDARY_COLOR = "000000"
const val DEFAULT_ICON_TERTIARY_COLOR = "000000"
const val DEFAULT_TEXT_PRIMARY_COLOR = "FF2B2D35"
const val DEFAULT_TEXT_SECONDARY_COLOR = "FF54565F"
const val DEFAULT_TEXT_TERTIARY_COLOR = "FFBBBDBD"
const val DEFAULT_TEXT_TOP_BAR_COLOR = "FFFFFF"
const val DEFAULT_TEXT_LINK_COLOR = "000000"
const val DEFAULT_UI_PRIMARY_COLOR = "F90D00"
const val DEFAULT_UI_SECONDARY_COLOR = "FF9500"
const val DEFAULT_UI_TERTIARY_COLOR = "FFCC00"
const val DEFAULT_UI_ERROR_COLOR = "FFDC4337"
const val DEFAULT_UI_SUCCESS_COLOR = "DB1D0E"
const val DEFAULT_UI_BACKGROUND_PRIMARY_COLOR = "f2f3f4"
const val DEFAULT_UI_BACKGROUND_SECONDARY_COLOR = "f2f3f4"
const val DEFAULT_UI_NAVIGATION_PRIMARY_COLOR = "f2f3f4"
const val DEFAULT_UI_NAVIGATION_SECONDARY_COLOR = "202a36"
const val DEFAULT_TEXT_MESSAGE_COLOR = "FFFFFF"
const val DEFAULT_STATS_POSITIVE_COLOR = "61CA00"
const val DEFAULT_STATS_NEGATIVE_COLOR = "326700"

internal data class ProjectBrandingEntity(

        @SerializedName("icon_primary_color")
        val iconPrimaryColor: String = DEFAULT_ICON_PRIMARY_COLOR,

        @SerializedName("icon_secondary_color")
        val iconSecondaryColor: String = DEFAULT_ICON_SECONDARY_COLOR,

        @SerializedName("icon_tertiary_color")
        val iconTertiaryColor: String = DEFAULT_ICON_TERTIARY_COLOR,

        @SerializedName("text_primary_color")
        val textPrimaryColor: String = DEFAULT_TEXT_PRIMARY_COLOR,

        @SerializedName("text_secondary_color")
        val textSecondaryColor: String = DEFAULT_TEXT_SECONDARY_COLOR,

        @SerializedName("text_tertiary_color")
        val textTertiaryColor: String = DEFAULT_TEXT_TERTIARY_COLOR,

        @SerializedName("text_topbar_color")
        val textTopBarColor: String = DEFAULT_TEXT_TOP_BAR_COLOR,

        @SerializedName("text_link_color")
        val textLinkColor: String = DEFAULT_TEXT_LINK_COLOR,

        @SerializedName("ui_primary_color")
        val uiPrimaryColor: String = DEFAULT_UI_PRIMARY_COLOR,

        @SerializedName("ui_secondary_color")
        val uiSecondaryColor: String = DEFAULT_UI_SECONDARY_COLOR,

        @SerializedName("ui_tertiary_color")
        val uiTertiaryColor: String = DEFAULT_UI_TERTIARY_COLOR,

        @SerializedName("ui_error_color")
        val uiErrorColor: String = DEFAULT_UI_ERROR_COLOR,

        @SerializedName("ui_success_color")
        val uiSuccessColor: String = DEFAULT_UI_SUCCESS_COLOR,

        @SerializedName("ui_bg_primary_color")
        val uiBackgroundPrimaryColor: String = DEFAULT_UI_BACKGROUND_PRIMARY_COLOR,

        @SerializedName("ui_bg_secondary_color")
        val uiBackgroundSecondaryColor: String = DEFAULT_UI_BACKGROUND_SECONDARY_COLOR,

        @SerializedName("ui_nav_primary_color")
        val uiNavigationPrimaryColor: String = DEFAULT_UI_NAVIGATION_PRIMARY_COLOR,

        @SerializedName("ui_nav_secondary_color")
        val uiNavigationSecondaryColor: String = DEFAULT_UI_NAVIGATION_SECONDARY_COLOR,

        @SerializedName("text_message_color")
        val textMessageColor: String = DEFAULT_TEXT_MESSAGE_COLOR,

        @SerializedName("ui_status_bar_style")
        val uiStatusBarStyle: String = "light",

        @SerializedName("logo_url")
        val logoUrl: String? = null,

        @SerializedName("ui_theme")
        val uiTheme: String = "theme_1",

        // This is a dependency, no need to serialize or parse it
        @Transient
        var colorParser: ColorParser = ColorParserImpl()

) {
    fun toProjectBranding(): ProjectBranding {
        return ProjectBranding(
                uiBackgroundPrimaryColor = colorParser.fromHexString(
                        uiBackgroundPrimaryColor,
                        DEFAULT_UI_BACKGROUND_PRIMARY_COLOR),
                uiBackgroundSecondaryColor = colorParser.fromHexString(
                        uiBackgroundSecondaryColor,
                        DEFAULT_UI_BACKGROUND_SECONDARY_COLOR),
                iconPrimaryColor = colorParser.fromHexString(
                        iconPrimaryColor,
                        DEFAULT_ICON_PRIMARY_COLOR),
                iconSecondaryColor = colorParser.fromHexString(
                        iconSecondaryColor,
                        DEFAULT_ICON_SECONDARY_COLOR),
                iconTertiaryColor= colorParser.fromHexString(
                        iconTertiaryColor,
                        DEFAULT_ICON_TERTIARY_COLOR),
                textPrimaryColor= colorParser.fromHexString(
                        textPrimaryColor,
                        DEFAULT_TEXT_PRIMARY_COLOR),
                textSecondaryColor= colorParser.fromHexString(
                        textSecondaryColor,
                        DEFAULT_TEXT_SECONDARY_COLOR),
                textTertiaryColor= colorParser.fromHexString(
                        textTertiaryColor,
                        DEFAULT_TEXT_TERTIARY_COLOR),
                textTopBarColor= colorParser.fromHexString(
                        textTopBarColor,
                        DEFAULT_TEXT_TOP_BAR_COLOR),
                textLinkColor= colorParser.fromHexString(
                        textLinkColor,
                        DEFAULT_TEXT_LINK_COLOR),
                uiPrimaryColor= colorParser.fromHexString(
                        uiPrimaryColor,
                        DEFAULT_UI_PRIMARY_COLOR),
                uiSecondaryColor= colorParser.fromHexString(
                        uiSecondaryColor,
                        DEFAULT_UI_SECONDARY_COLOR),
                uiTertiaryColor= colorParser.fromHexString(
                        uiTertiaryColor,
                        DEFAULT_UI_TERTIARY_COLOR),
                uiErrorColor= colorParser.fromHexString(
                        uiErrorColor,
                        DEFAULT_UI_ERROR_COLOR),
                uiSuccessColor= colorParser.fromHexString(
                        uiSuccessColor,
                        DEFAULT_UI_SUCCESS_COLOR),
                uiNavigationPrimaryColor= colorParser.fromHexString(
                        uiNavigationPrimaryColor,
                        DEFAULT_UI_NAVIGATION_PRIMARY_COLOR),
                uiNavigationSecondaryColor= colorParser.fromHexString(
                        uiNavigationSecondaryColor,
                        DEFAULT_UI_NAVIGATION_SECONDARY_COLOR),
                textMessageColor= colorParser.fromHexString(
                        textMessageColor,
                        DEFAULT_TEXT_MESSAGE_COLOR),
                uiStatusBarStyle = UIStatusBarStyle.parseStatusBarStyle(uiStatusBarStyle),
                logoUrl = logoUrl,
                uiTheme = UITheme.parseUITheme(uiTheme)
        )
    }
}
