package com.aptopayments.core.data.config

import android.graphics.Color
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.extension.ColorParserImpl
import com.aptopayments.core.repository.config.remote.entities.*
import java.io.Serializable
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PRIVATE)
val DEFAULT_THEME = UITheme.THEME_2

data class ProjectBranding (
        val uiBackgroundPrimaryColor: Int,
        val uiBackgroundSecondaryColor: Int,
        val iconPrimaryColor: Int,
        val iconSecondaryColor: Int,
        val iconTertiaryColor: Int,
        val textPrimaryColor: Int,
        val textSecondaryColor: Int,
        val textTertiaryColor: Int,
        val textTopBarPrimaryColor: Int,
        val textTopBarSecondaryColor: Int,
        val textLinkColor: Int,
        val textLinkUnderlined: Boolean,
        val textButtonColor: Int,
        val buttonCornerRadius: Float,
        val uiPrimaryColor: Int,
        val uiSecondaryColor: Int,
        val uiTertiaryColor: Int,
        val uiErrorColor: Int,
        val uiSuccessColor: Int,
        val uiNavigationPrimaryColor: Int,
        val uiNavigationSecondaryColor: Int,
        val textMessageColor: Int,
        val badgeBackgroundPositiveColor: Int,
        val badgeBackgroundNegativeColor: Int,
        val showToastTitle: Boolean,
        val transactionDetailsCollapsable: Boolean,
        val disclaimerBackgroundColor: Int,
        val uiStatusBarStyle: UIStatusBarStyle,
        val logoUrl: String?,
        var uiTheme: UITheme
) : Serializable {
    companion object {
        fun createDefault(): ProjectBranding {
            val colorParser = ColorParserImpl()

            return ProjectBranding(
                uiBackgroundPrimaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_PRIMARY_COLOR),
                uiBackgroundSecondaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_SECONDARY_COLOR),
                iconPrimaryColor = colorParser.fromHexString(DEFAULT_ICON_PRIMARY_COLOR),
                iconSecondaryColor = colorParser.fromHexString(DEFAULT_ICON_SECONDARY_COLOR),
                iconTertiaryColor = colorParser.fromHexString(DEFAULT_ICON_TERTIARY_COLOR),
                textPrimaryColor = colorParser.fromHexString(DEFAULT_TEXT_PRIMARY_COLOR),
                textSecondaryColor = colorParser.fromHexString(DEFAULT_TEXT_SECONDARY_COLOR),
                textTertiaryColor = colorParser.fromHexString(DEFAULT_TEXT_TERTIARY_COLOR),
                textTopBarPrimaryColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR),
                textTopBarSecondaryColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR),
                textLinkColor = colorParser.fromHexString(DEFAULT_TEXT_LINK_COLOR),
                textLinkUnderlined = true,
                textButtonColor = Color.WHITE,
                buttonCornerRadius = DEFAULT_BUTTON_CORNER_RADIUS,
                uiPrimaryColor = colorParser.fromHexString(DEFAULT_UI_PRIMARY_COLOR),
                uiSecondaryColor = colorParser.fromHexString(DEFAULT_UI_SECONDARY_COLOR),
                uiTertiaryColor = colorParser.fromHexString(DEFAULT_UI_TERTIARY_COLOR),
                uiErrorColor = colorParser.fromHexString(DEFAULT_UI_ERROR_COLOR),
                uiSuccessColor = colorParser.fromHexString(DEFAULT_UI_SUCCESS_COLOR),
                uiNavigationPrimaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_PRIMARY_COLOR),
                uiNavigationSecondaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_SECONDARY_COLOR),
                textMessageColor = colorParser.fromHexString(DEFAULT_TEXT_MESSAGE_COLOR),
                badgeBackgroundPositiveColor = colorParser.fromHexString(DEFAULT_BADGE_BG_POSITIVE_COLOR),
                badgeBackgroundNegativeColor = colorParser.fromHexString(DEFAULT_BADGE_BG_NEGATIVE_COLOR),
                showToastTitle = true,
                transactionDetailsCollapsable = true,
                disclaimerBackgroundColor = colorParser.fromHexString(DEFAULT_DISCLAIMER_BACKGROUND_COLOR),
                uiStatusBarStyle = UIStatusBarStyle.AUTO,
                logoUrl = "",
                uiTheme = DEFAULT_THEME
            )
        }

        fun createDarkDefault(): ProjectBranding {
            val colorParser = ColorParserImpl()

            return ProjectBranding(
                uiBackgroundPrimaryColor = colorParser.fromHexString("333333", DEFAULT_UI_BACKGROUND_PRIMARY_COLOR),
                uiBackgroundSecondaryColor = colorParser.fromHexString("3c3c3c", DEFAULT_UI_BACKGROUND_SECONDARY_COLOR),
                iconPrimaryColor = colorParser.fromHexString("ffffff", DEFAULT_ICON_PRIMARY_COLOR),
                iconSecondaryColor = colorParser.fromHexString("dddddd", DEFAULT_ICON_SECONDARY_COLOR),
                iconTertiaryColor = colorParser.fromHexString("cccccc", DEFAULT_ICON_TERTIARY_COLOR),
                textPrimaryColor = colorParser.fromHexString("ffffff", DEFAULT_TEXT_PRIMARY_COLOR),
                textSecondaryColor = colorParser.fromHexString("dddddd", DEFAULT_TEXT_SECONDARY_COLOR),
                textTertiaryColor = colorParser.fromHexString("cccccc", DEFAULT_TEXT_TERTIARY_COLOR),
                textTopBarPrimaryColor = colorParser.fromHexString("ffffff", DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR),
                textTopBarSecondaryColor = colorParser.fromHexString("dddddd", DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR),
                textLinkColor = colorParser.fromHexString("e4e5f6", DEFAULT_TEXT_LINK_COLOR),
                textLinkUnderlined = true,
                textButtonColor = colorParser.fromHexString("ffffff"),
                buttonCornerRadius = DEFAULT_BUTTON_CORNER_RADIUS,
                uiPrimaryColor = colorParser.fromHexString("ff0000", DEFAULT_UI_PRIMARY_COLOR),
                uiSecondaryColor = colorParser.fromHexString("ffffff", DEFAULT_UI_SECONDARY_COLOR),
                uiTertiaryColor = colorParser.fromHexString("ffffff", DEFAULT_UI_TERTIARY_COLOR),
                uiErrorColor = colorParser.fromHexString(DEFAULT_UI_ERROR_COLOR),
                uiSuccessColor = colorParser.fromHexString(DEFAULT_UI_SUCCESS_COLOR),
                uiNavigationPrimaryColor = colorParser.fromHexString("000000", DEFAULT_UI_NAVIGATION_PRIMARY_COLOR),
                uiNavigationSecondaryColor = colorParser.fromHexString("121212", DEFAULT_UI_NAVIGATION_SECONDARY_COLOR),
                textMessageColor = colorParser.fromHexString("ffffff", DEFAULT_TEXT_MESSAGE_COLOR),
                badgeBackgroundPositiveColor = colorParser.fromHexString("a4e575", DEFAULT_BADGE_BG_POSITIVE_COLOR),
                badgeBackgroundNegativeColor = colorParser.fromHexString("3e4643", DEFAULT_BADGE_BG_NEGATIVE_COLOR),
                showToastTitle = true,
                transactionDetailsCollapsable = true,
                disclaimerBackgroundColor = colorParser.fromHexString("333333", DEFAULT_DISCLAIMER_BACKGROUND_COLOR),
                uiStatusBarStyle = UIStatusBarStyle.AUTO,
                logoUrl = "",
                uiTheme = DEFAULT_THEME
            )
        }
    }
}

