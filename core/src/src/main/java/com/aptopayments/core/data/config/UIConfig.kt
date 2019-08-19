package com.aptopayments.core.data.config

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import com.aptopayments.core.extension.ColorParserImpl
import com.aptopayments.core.repository.config.remote.entities.*

private const val UI_COLOR_DISABLED_ALPHA = 0.3
private const val TOP_BAR_DISABLED_ALPHA = 0.4

object UIConfig {

    var colorParser = ColorParserImpl()

    var uiBackgroundPrimaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_PRIMARY_COLOR,
            DEFAULT_UI_BACKGROUND_PRIMARY_COLOR)
    var uiBackgroundSecondaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_SECONDARY_COLOR,
            DEFAULT_UI_BACKGROUND_SECONDARY_COLOR)
    var iconPrimaryColor = colorParser.fromHexString(DEFAULT_ICON_PRIMARY_COLOR, DEFAULT_ICON_PRIMARY_COLOR)
    var iconSecondaryColor = colorParser.fromHexString(DEFAULT_ICON_SECONDARY_COLOR, DEFAULT_ICON_SECONDARY_COLOR)
    var iconTertiaryColor = colorParser.fromHexString(DEFAULT_ICON_TERTIARY_COLOR, DEFAULT_ICON_TERTIARY_COLOR)
    var textPrimaryColor = colorParser.fromHexString(DEFAULT_TEXT_PRIMARY_COLOR, DEFAULT_TEXT_PRIMARY_COLOR)
    var textSecondaryColor = colorParser.fromHexString(DEFAULT_TEXT_SECONDARY_COLOR, DEFAULT_TEXT_SECONDARY_COLOR)
    var textTertiaryColor = colorParser.fromHexString(DEFAULT_TEXT_TERTIARY_COLOR, DEFAULT_TEXT_TERTIARY_COLOR)
    @Deprecated(message = "Replaced with textTopBarPrimaryColor and textTopBarSecondaryColor",
            replaceWith = ReplaceWith(
                    expression = "UIConfig.textTopBarPrimaryColor",
                    imports = ["com.aptopayments.core.data.config"])
    )
    var textTopBarColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR, DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR)
    @Deprecated(message = "Replaced with disabledTextTopBarPrimaryColor and disabledTextTopBarSecondaryColor",
            replaceWith = ReplaceWith(
                    expression = "UIConfig.disabledTextTopBarPrimaryColor",
                    imports = ["com.aptopayments.core.data.config"])
    )
    val disabledTextTopBarColor: Int
        get() = ColorUtils.setAlphaComponent(textTopBarColor, (255*TOP_BAR_DISABLED_ALPHA).toInt())
    var textTopBarPrimaryColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR,
            DEFAULT_TEXT_TOP_BAR_PRIMARY_COLOR)
    val disabledTextTopBarPrimaryColor: Int
        get() = ColorUtils.setAlphaComponent(textTopBarPrimaryColor, (255*TOP_BAR_DISABLED_ALPHA).toInt())
    var textTopBarSecondaryColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR,
            DEFAULT_TEXT_TOP_BAR_SECONDARY_COLOR)
    val disabledTextTopBarSecondaryColor: Int
        get() = ColorUtils.setAlphaComponent(textTopBarSecondaryColor, (255*TOP_BAR_DISABLED_ALPHA).toInt())
    var textLinkColor = colorParser.fromHexString(DEFAULT_TEXT_LINK_COLOR, DEFAULT_TEXT_LINK_COLOR)
    var uiPrimaryColor = colorParser.fromHexString(DEFAULT_UI_PRIMARY_COLOR, DEFAULT_UI_PRIMARY_COLOR)
    val uiPrimaryColorDisabled: Int
        get() = ColorUtils.setAlphaComponent(uiPrimaryColor, (255* UI_COLOR_DISABLED_ALPHA).toInt())
    var uiSecondaryColor = colorParser.fromHexString(DEFAULT_UI_SECONDARY_COLOR, DEFAULT_UI_SECONDARY_COLOR)
    var uiTertiaryColor = colorParser.fromHexString(DEFAULT_UI_TERTIARY_COLOR, DEFAULT_UI_TERTIARY_COLOR)
    var uiErrorColor = colorParser.fromHexString(DEFAULT_UI_ERROR_COLOR, DEFAULT_UI_ERROR_COLOR)
    var uiSuccessColor = colorParser.fromHexString(DEFAULT_UI_SUCCESS_COLOR, DEFAULT_UI_SUCCESS_COLOR)
    var uiNavigationPrimaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_PRIMARY_COLOR,
            DEFAULT_UI_NAVIGATION_PRIMARY_COLOR)
    var uiNavigationSecondaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_SECONDARY_COLOR,
            DEFAULT_UI_NAVIGATION_SECONDARY_COLOR)
    var textMessageColor = colorParser.fromHexString(DEFAULT_TEXT_MESSAGE_COLOR, DEFAULT_TEXT_MESSAGE_COLOR)
    var textButtonColor = Color.WHITE
    var statsDifferenceIncreaseBackgroundColor = colorParser.fromHexString(DEFAULT_BADGE_BG_POSITIVE_COLOR,
            DEFAULT_BADGE_BG_POSITIVE_COLOR)
    var statsDifferenceDecreaseBackgroundColor = colorParser.fromHexString(DEFAULT_BADGE_BG_NEGATIVE_COLOR,
            DEFAULT_BADGE_BG_NEGATIVE_COLOR)
    var uiStatusBarStyle = UIStatusBarStyle.AUTO
    var uiTheme: UITheme = UITheme.THEME_2
    var showToastTitle = true
    var transactionDetailsShowDetailsSectionTitle = true
    var disclaimerBackgroundColor = colorParser.fromHexString(DEFAULT_DISCLAIMER_BACKGROUND_COLOR,
            DEFAULT_DISCLAIMER_BACKGROUND_COLOR)
    var buttonCornerRadius = DEFAULT_BUTTON_CORNER_RADIUS
    var underlineLinks = true

    fun updateUIConfigFrom(projectBranding: ProjectBranding) {
        uiBackgroundPrimaryColor = projectBranding.uiBackgroundPrimaryColor
        uiBackgroundSecondaryColor = projectBranding.uiBackgroundSecondaryColor
        iconPrimaryColor = projectBranding.iconPrimaryColor
        iconSecondaryColor = projectBranding.iconSecondaryColor
        iconTertiaryColor = projectBranding.iconTertiaryColor
        textPrimaryColor = projectBranding.textPrimaryColor
        textSecondaryColor = projectBranding.textSecondaryColor
        textTertiaryColor = projectBranding.textTertiaryColor
        textTopBarColor = projectBranding.textTopBarPrimaryColor
        textTopBarPrimaryColor = projectBranding.textTopBarPrimaryColor
        textTopBarSecondaryColor = projectBranding.textTopBarSecondaryColor
        textLinkColor = projectBranding.textLinkColor
        underlineLinks = projectBranding.textLinkUnderlined
        textButtonColor = projectBranding.textButtonColor
        buttonCornerRadius = projectBranding.buttonCornerRadius
        uiPrimaryColor = projectBranding.uiPrimaryColor
        uiSecondaryColor = projectBranding.uiSecondaryColor
        uiTertiaryColor = projectBranding.uiTertiaryColor
        uiErrorColor = projectBranding.uiErrorColor
        uiSuccessColor = projectBranding.uiSuccessColor
        uiNavigationPrimaryColor = projectBranding.uiNavigationPrimaryColor
        uiNavigationSecondaryColor = projectBranding.uiNavigationSecondaryColor
        textMessageColor = projectBranding.textMessageColor
        statsDifferenceIncreaseBackgroundColor = projectBranding.badgeBackgroundPositiveColor
        statsDifferenceDecreaseBackgroundColor = projectBranding.badgeBackgroundNegativeColor
        showToastTitle = projectBranding.showToastTitle
        transactionDetailsShowDetailsSectionTitle = projectBranding.transactionDetailsCollapsable
        disclaimerBackgroundColor = projectBranding.disclaimerBackgroundColor
        uiStatusBarStyle = projectBranding.uiStatusBarStyle
        uiTheme = projectBranding.uiTheme
    }
}
