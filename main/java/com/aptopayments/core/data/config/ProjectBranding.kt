package com.aptopayments.core.data.config

import java.io.Serializable

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
        val uiTheme: UITheme
) : Serializable
