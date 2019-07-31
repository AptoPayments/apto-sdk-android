package com.aptopayments.core.data.config
import android.graphics.Color
import androidx.core.graphics.ColorUtils
import com.aptopayments.core.data.config.UIStatusBarStyle
import com.aptopayments.core.data.config.UITheme
import com.aptopayments.core.extension.ColorParserImpl
import com.aptopayments.core.repository.config.remote.entities.*

object UIConfig {

    var colorParser = ColorParserImpl()

    var uiBackgroundPrimaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_PRIMARY_COLOR, DEFAULT_UI_BACKGROUND_PRIMARY_COLOR)
    var uiBackgroundSecondaryColor = colorParser.fromHexString(DEFAULT_UI_BACKGROUND_SECONDARY_COLOR, DEFAULT_UI_BACKGROUND_SECONDARY_COLOR)
    var iconPrimaryColor = colorParser.fromHexString(DEFAULT_ICON_PRIMARY_COLOR, DEFAULT_ICON_PRIMARY_COLOR)
    var iconSecondaryColor = colorParser.fromHexString(DEFAULT_ICON_SECONDARY_COLOR, DEFAULT_ICON_SECONDARY_COLOR)
    var iconTertiaryColor = colorParser.fromHexString(DEFAULT_ICON_TERTIARY_COLOR, DEFAULT_ICON_TERTIARY_COLOR)
    var textPrimaryColor = colorParser.fromHexString(DEFAULT_TEXT_PRIMARY_COLOR, DEFAULT_TEXT_PRIMARY_COLOR)
    var textSecondaryColor = colorParser.fromHexString(DEFAULT_TEXT_SECONDARY_COLOR, DEFAULT_TEXT_SECONDARY_COLOR)
    var textTertiaryColor = colorParser.fromHexString(DEFAULT_TEXT_TERTIARY_COLOR, DEFAULT_TEXT_TERTIARY_COLOR)
    var textTopBarColor = colorParser.fromHexString(DEFAULT_TEXT_TOP_BAR_COLOR, DEFAULT_TEXT_TOP_BAR_COLOR)
    var disabledTextTopBarColor = ColorUtils.setAlphaComponent(textTopBarColor, (255*0.4).toInt())
    var textLinkColor = colorParser.fromHexString(DEFAULT_TEXT_LINK_COLOR, DEFAULT_TEXT_LINK_COLOR)
    var uiPrimaryColor = colorParser.fromHexString(DEFAULT_UI_PRIMARY_COLOR, DEFAULT_UI_PRIMARY_COLOR)
    val uiPrimaryColorDisabled = ColorUtils.setAlphaComponent(uiPrimaryColor, (255*0.3).toInt())
    var uiSecondaryColor = colorParser.fromHexString(DEFAULT_UI_SECONDARY_COLOR, DEFAULT_UI_SECONDARY_COLOR)
    var uiTertiaryColor = colorParser.fromHexString(DEFAULT_UI_TERTIARY_COLOR, DEFAULT_UI_TERTIARY_COLOR)
    var uiErrorColor = colorParser.fromHexString(DEFAULT_UI_ERROR_COLOR, DEFAULT_UI_ERROR_COLOR)
    var uiSuccessColor = colorParser.fromHexString(DEFAULT_UI_SUCCESS_COLOR, DEFAULT_UI_SUCCESS_COLOR)
    var uiNavigationPrimaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_PRIMARY_COLOR, DEFAULT_UI_NAVIGATION_PRIMARY_COLOR)
    var uiNavigationSecondaryColor = colorParser.fromHexString(DEFAULT_UI_NAVIGATION_SECONDARY_COLOR, DEFAULT_UI_NAVIGATION_SECONDARY_COLOR)
    var textMessageColor = colorParser.fromHexString(DEFAULT_TEXT_MESSAGE_COLOR, DEFAULT_TEXT_MESSAGE_COLOR)
    var textButtonColor = Color.WHITE
    var statsDifferenceIncreaseBackgroundColor = colorParser.fromHexString(DEFAULT_STATS_POSITIVE_COLOR, DEFAULT_STATS_POSITIVE_COLOR)
    var statsDifferenceDecreaseBackgroundColor = colorParser.fromHexString(DEFAULT_STATS_NEGATIVE_COLOR, DEFAULT_STATS_NEGATIVE_COLOR)
    var uiStatusBarStyle = UIStatusBarStyle.AUTO
    var uiTheme: UITheme = UITheme.THEME_2

    fun updateUIConfigFrom(projectBranding: ProjectBranding) {
        uiBackgroundPrimaryColor = projectBranding.uiBackgroundPrimaryColor
        uiBackgroundSecondaryColor = projectBranding.uiBackgroundSecondaryColor
        iconPrimaryColor = projectBranding.iconPrimaryColor
        iconSecondaryColor = projectBranding.iconSecondaryColor
        iconTertiaryColor = projectBranding.iconTertiaryColor
        textPrimaryColor = projectBranding.textPrimaryColor
        textSecondaryColor = projectBranding.textSecondaryColor
        textTertiaryColor = projectBranding.textTertiaryColor
        textTopBarColor = projectBranding.textTopBarColor
        disabledTextTopBarColor = ColorUtils.setAlphaComponent(textTopBarColor, (255*0.4).toInt())
        textLinkColor = projectBranding.textLinkColor
        uiPrimaryColor = projectBranding.uiPrimaryColor
        uiSecondaryColor = projectBranding.uiSecondaryColor
        uiTertiaryColor = projectBranding.uiTertiaryColor
        uiErrorColor = projectBranding.uiErrorColor
        uiSuccessColor = projectBranding.uiSuccessColor
        uiNavigationPrimaryColor = projectBranding.uiNavigationPrimaryColor
        uiNavigationSecondaryColor = projectBranding.uiNavigationSecondaryColor
        textMessageColor = projectBranding.textMessageColor
        uiStatusBarStyle = projectBranding.uiStatusBarStyle
        uiTheme = projectBranding.uiTheme
    }
}
