package com.aptopayments.mobile.repository.config.remote.entities

import androidx.annotation.RestrictTo
import com.aptopayments.mobile.data.config.ProjectBranding
import com.aptopayments.mobile.data.config.UIStatusBarStyle
import com.aptopayments.mobile.data.config.UITheme
import com.aptopayments.mobile.extension.ColorParserImpl
import com.google.gson.annotations.SerializedName

internal const val DEFAULT_ICON_PRIMARY_COLOR = "000000"
const val DEFAULT_ICON_SECONDARY_COLOR = "000000"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val DEFAULT_ICON_TERTIARY_COLOR = "000000"
internal const val DEFAULT_TEXT_PRIMARY_COLOR = "FF2B2D35"
internal const val DEFAULT_TEXT_SECONDARY_COLOR = "FF54565F"
internal const val DEFAULT_TEXT_TERTIARY_COLOR = "FFBBBDBD"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR = "202A36"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR = "FFFFFF"
internal const val DEFAULT_TEXT_LINK_COLOR = "FF54565F"
internal const val DEFAULT_TEXT_BUTTON_COLOR = "FFFFFF"
internal const val DEFAULT_BUTTON_CORNER_RADIUS = 12f
internal const val DEFAULT_UI_PRIMARY_COLOR = "F90D00"
internal const val DEFAULT_UI_SECONDARY_COLOR = "FF9500"
internal const val DEFAULT_UI_TERTIARY_COLOR = "FFCC00"
internal const val DEFAULT_UI_ERROR_COLOR = "FFDC4337"
internal const val DEFAULT_UI_SUCCESS_COLOR = "DB1D0E"
internal const val DEFAULT_UI_BACKGROUND_PRIMARY_COLOR = "f2f3f4"
internal const val DEFAULT_UI_BACKGROUND_SECONDARY_COLOR = "f2f3f4"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val DEFAULT_UI_NAVIGATION_PRIMARY_COLOR = "f2f3f4"

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val DEFAULT_UI_NAVIGATION_SECONDARY_COLOR = "202a36"
internal const val DEFAULT_TEXT_MESSAGE_COLOR = "FFFFFF"
internal const val DEFAULT_BADGE_BG_POSITIVE_COLOR = "61ca00"
internal const val DEFAULT_BADGE_BG_NEGATIVE_COLOR = "326700"
internal const val DEFAULT_DISCLAIMER_BACKGROUND_COLOR = "f2f3f4"

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

    @SerializedName("text_topbar_primary_color")
    val textTopBarPrimaryColor: String = DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR,

    @SerializedName("text_topbar_secondary_color")
    val textTopBarSecondaryColor: String = DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR,

    @SerializedName("text_link_color")
    val textLinkColor: String = DEFAULT_TEXT_LINK_COLOR,

    @SerializedName("text_link_underlined")
    val textLinkUnderlined: Boolean = true,

    @SerializedName("text_button_color")
    val textButtonColor: String = DEFAULT_TEXT_BUTTON_COLOR,

    @SerializedName("button_corner_radius")
    val buttonCornerRadius: Float = DEFAULT_BUTTON_CORNER_RADIUS,

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

    @SerializedName("badge_bg_positive_color")
    val badgeBackgroundPositiveColor: String = DEFAULT_BADGE_BG_POSITIVE_COLOR,

    @SerializedName("badge_bg_negative_color")
    val badgeBackgroundNegativeColor: String = DEFAULT_BADGE_BG_NEGATIVE_COLOR,

    @SerializedName("show_toast_title")
    val showToastTitle: Boolean = true,

    @SerializedName("txn_details_collapsable")
    val transactionDetailsCollapsable: Boolean = true,

    @SerializedName("disclaimer_background_color")
    val disclaimerBackgroundColor: String = DEFAULT_DISCLAIMER_BACKGROUND_COLOR,

    @SerializedName("ui_status_bar_style")
    val uiStatusBarStyle: String = "light",

    @SerializedName("logo_url")
    val logoUrl: String? = null,

    @SerializedName("ui_theme")
    val uiTheme: String = "theme_1"

) {

    fun toProjectBranding(): ProjectBranding {
        val colorParser = ColorParserImpl()

        return ProjectBranding(
            uiBackgroundPrimaryColor = colorParser.fromHexString(
                uiBackgroundPrimaryColor,
                DEFAULT_UI_BACKGROUND_PRIMARY_COLOR
            ),
            uiBackgroundSecondaryColor = colorParser.fromHexString(
                uiBackgroundSecondaryColor,
                DEFAULT_UI_BACKGROUND_SECONDARY_COLOR
            ),
            iconPrimaryColor = colorParser.fromHexString(
                iconPrimaryColor,
                DEFAULT_ICON_PRIMARY_COLOR
            ),
            iconSecondaryColor = colorParser.fromHexString(
                iconSecondaryColor,
                DEFAULT_ICON_SECONDARY_COLOR
            ),
            iconTertiaryColor = colorParser.fromHexString(
                iconTertiaryColor,
                DEFAULT_ICON_TERTIARY_COLOR
            ),
            textPrimaryColor = colorParser.fromHexString(
                textPrimaryColor,
                DEFAULT_TEXT_PRIMARY_COLOR
            ),
            textSecondaryColor = colorParser.fromHexString(
                textSecondaryColor,
                DEFAULT_TEXT_SECONDARY_COLOR
            ),
            textTertiaryColor = colorParser.fromHexString(
                textTertiaryColor,
                DEFAULT_TEXT_TERTIARY_COLOR
            ),
            textTopBarPrimaryColor = colorParser.fromHexString(
                textTopBarPrimaryColor,
                DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR
            ),
            textTopBarSecondaryColor = colorParser.fromHexString(
                textTopBarSecondaryColor,
                DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR
            ),
            textLinkColor = colorParser.fromHexString(
                textLinkColor,
                DEFAULT_TEXT_LINK_COLOR
            ),
            textLinkUnderlined = textLinkUnderlined,
            textButtonColor = colorParser.fromHexString(
                textButtonColor,
                DEFAULT_TEXT_BUTTON_COLOR
            ),
            buttonCornerRadius = buttonCornerRadius,
            uiPrimaryColor = colorParser.fromHexString(
                uiPrimaryColor,
                DEFAULT_UI_PRIMARY_COLOR
            ),
            uiSecondaryColor = colorParser.fromHexString(
                uiSecondaryColor,
                DEFAULT_UI_SECONDARY_COLOR
            ),
            uiTertiaryColor = colorParser.fromHexString(
                uiTertiaryColor,
                DEFAULT_UI_TERTIARY_COLOR
            ),
            uiErrorColor = colorParser.fromHexString(
                uiErrorColor,
                DEFAULT_UI_ERROR_COLOR
            ),
            uiSuccessColor = colorParser.fromHexString(
                uiSuccessColor,
                DEFAULT_UI_SUCCESS_COLOR
            ),
            uiNavigationPrimaryColor = colorParser.fromHexString(
                uiNavigationPrimaryColor,
                DEFAULT_UI_NAVIGATION_PRIMARY_COLOR
            ),
            uiNavigationSecondaryColor = colorParser.fromHexString(
                uiNavigationSecondaryColor,
                DEFAULT_UI_NAVIGATION_SECONDARY_COLOR
            ),
            textMessageColor = colorParser.fromHexString(
                textMessageColor,
                DEFAULT_TEXT_MESSAGE_COLOR
            ),
            badgeBackgroundPositiveColor = colorParser.fromHexString(
                badgeBackgroundPositiveColor,
                DEFAULT_BADGE_BG_POSITIVE_COLOR
            ),
            badgeBackgroundNegativeColor = colorParser.fromHexString(
                badgeBackgroundNegativeColor,
                DEFAULT_BADGE_BG_NEGATIVE_COLOR
            ),
            showToastTitle = showToastTitle,
            transactionDetailsCollapsable = transactionDetailsCollapsable,
            disclaimerBackgroundColor = colorParser.fromHexString(
                disclaimerBackgroundColor,
                DEFAULT_DISCLAIMER_BACKGROUND_COLOR
            ),
            uiStatusBarStyle = UIStatusBarStyle.parseStatusBarStyle(uiStatusBarStyle),
            logoUrl = logoUrl,
            uiTheme = UITheme.parseUITheme(uiTheme)
        )
    }
}
