package com.aptopayments.mobile.features.managecard

class CardOptions(
    private val showStatsButton: Boolean = false,
    private val showNotificationPreferences: Boolean = false,
    private val showDetailedCardActivityOption: Boolean = false,
    private val hideFundingSourcesReconnectButton: Boolean = false,
    private val showAccountSettingsButton: Boolean = true,
    private val showMonthlyStatementsOption: Boolean = true,
    private val authenticateOnStartup: Boolean = false,
    private val authenticateWithPINOnPCI: Boolean = false,
    private val darkThemeEnabled: Boolean = false,
    private val inAppProvisioningEnabled: Boolean = false,
    var openingMode: OpeningMode = OpeningMode.STANDALONE,
    var fontOptions: FontOptions = FontOptions()
) {

    enum class OpeningMode { EMBEDDED, STANDALONE }

    fun showStatsButton() = showStatsButton

    fun showNotificationPreferences() = showNotificationPreferences

    fun showDetailedCardActivityOption() = showDetailedCardActivityOption

    fun hideFundingSourcesReconnectButton() = hideFundingSourcesReconnectButton

    fun showAccountSettingsButton() = showAccountSettingsButton

    fun showMonthlyStatementOption() = showMonthlyStatementsOption

    fun authenticateOnStartup() = authenticateOnStartup

    fun authenticateWithPINOnPCI() = authenticateWithPINOnPCI

    fun darkThemeEnabled() = darkThemeEnabled

    fun inAppProvisioningEnabled() = inAppProvisioningEnabled
}
