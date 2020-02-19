package com.aptopayments.core.data.config

import androidx.core.graphics.ColorUtils

private const val UI_COLOR_DISABLED_ALPHA = 0.3
private const val TOP_BAR_DISABLED_ALPHA = 0.4

object UIConfig {

    var darkTheme: Boolean = false
    private var branding: Branding = Branding.createDefault()
    private val currentBranding: ProjectBranding
        get() = branding.getBranding(darkTheme)

    val uiBackgroundPrimaryColor: Int
        get() = currentBranding.uiBackgroundPrimaryColor
    val uiBackgroundSecondaryColor: Int
        get() = currentBranding.uiBackgroundSecondaryColor
    val iconPrimaryColor: Int
        get() = currentBranding.iconPrimaryColor
    val iconSecondaryColor: Int
        get() = currentBranding.iconSecondaryColor
    val iconTertiaryColor: Int
        get() = currentBranding.iconTertiaryColor
    val textPrimaryColor: Int
        get() = currentBranding.textPrimaryColor
    val textSecondaryColor: Int
        get() = currentBranding.textSecondaryColor
    val textTertiaryColor: Int
        get() = currentBranding.textTertiaryColor
    val textTopBarPrimaryColor: Int
        get() = currentBranding.textTopBarPrimaryColor
    val disabledTextTopBarPrimaryColor: Int
        get() = alpha(textTopBarPrimaryColor, TOP_BAR_DISABLED_ALPHA)
    val textTopBarSecondaryColor: Int
        get() = currentBranding.textTopBarSecondaryColor
    val disabledTextTopBarSecondaryColor: Int
        get() = alpha(textTopBarSecondaryColor, TOP_BAR_DISABLED_ALPHA)
    val textLinkColor: Int
        get() = currentBranding.textLinkColor
    val uiPrimaryColor: Int
        get() = currentBranding.uiPrimaryColor
    val uiPrimaryColorDisabled: Int
        get() = alpha(uiPrimaryColor, UI_COLOR_DISABLED_ALPHA)
    val uiSecondaryColor: Int
        get() = currentBranding.uiSecondaryColor
    val uiTertiaryColor: Int
        get() = currentBranding.uiTertiaryColor
    val uiErrorColor: Int
        get() = currentBranding.uiErrorColor
    val uiSuccessColor: Int
        get() = currentBranding.uiSuccessColor
    val uiNavigationPrimaryColor: Int
        get() = currentBranding.uiNavigationPrimaryColor
    val uiNavigationSecondaryColor: Int
        get() = currentBranding.uiNavigationSecondaryColor
    val textMessageColor: Int
        get() = currentBranding.textButtonColor
    val textButtonColor: Int
        get() = currentBranding.textButtonColor
    val statsDifferenceIncreaseBackgroundColor: Int
        get() = currentBranding.badgeBackgroundPositiveColor
    val statsDifferenceDecreaseBackgroundColor: Int
        get() = currentBranding.badgeBackgroundNegativeColor
    val uiStatusBarStyle: UIStatusBarStyle
        get() = currentBranding.uiStatusBarStyle
    val uiTheme: UITheme
        get() = currentBranding.uiTheme
    val showToastTitle: Boolean
        get() = currentBranding.showToastTitle
    val transactionDetailsShowDetailsSectionTitle: Boolean
        get() = currentBranding.transactionDetailsCollapsable
    val disclaimerBackgroundColor: Int
        get() = currentBranding.disclaimerBackgroundColor
    val buttonCornerRadius: Float
        get() = currentBranding.buttonCornerRadius
    val underlineLinks: Boolean
        get() = currentBranding.textLinkUnderlined
    val logoImage: String
        get() = currentBranding.logoUrl ?: ""

    fun updateUIConfigFrom(branding: Branding) {
        this.branding = branding
    }

    private fun alpha(color: Int, amount: Double) = ColorUtils.setAlphaComponent(color, (255 * amount).toInt())
}
