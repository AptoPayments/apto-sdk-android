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
        val textTopBarColor: Int,
        val textLinkColor: Int,
        val uiPrimaryColor: Int,
        val uiSecondaryColor: Int,
        val uiTertiaryColor: Int,
        val uiErrorColor: Int,
        val uiSuccessColor: Int,
        val uiNavigationPrimaryColor: Int,
        val uiNavigationSecondaryColor: Int,
        val textMessageColor: Int,
        val uiStatusBarStyle: UIStatusBarStyle,
        val logoUrl: String?,
        val uiTheme: UITheme
) : Serializable
