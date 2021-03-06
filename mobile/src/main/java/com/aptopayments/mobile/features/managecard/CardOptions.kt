package com.aptopayments.mobile.features.managecard

class CardOptions(
    private val showStatsButton: Boolean = false,
    private val showNotificationPreferences: Boolean = false,
    private val showDetailedCardActivityOption: Boolean = false,
    private val hideFundingSourcesReconnectButton: Boolean = false,
    private val showAccountSettingsButton: Boolean = true,
    private val showMonthlyStatementsOption: Boolean = true,
    private val authenticateOnStartup: Boolean = false,
    private val authenticateOnPCI: PCIAuthType = PCIAuthType.NONE,
    private val darkThemeEnabled: Boolean = false,
    var openingMode: OpeningMode = OpeningMode.STANDALONE,
    var fontOptions: FontOptions = FontOptions()
) {

    enum class OpeningMode { EMBEDDED, STANDALONE }

    enum class PCIAuthType { PIN_OR_BIOMETRICS, BIOMETRICS, NONE }

    fun showStatsButton() = showStatsButton

    fun showNotificationPreferences() = showNotificationPreferences

    fun showDetailedCardActivityOption() = showDetailedCardActivityOption

    fun hideFundingSourcesReconnectButton() = hideFundingSourcesReconnectButton

    fun showAccountSettingsButton() = showAccountSettingsButton

    fun showMonthlyStatementOption() = showMonthlyStatementsOption

    fun authenticateOnStartup() = authenticateOnStartup

    fun authenticatePCI() = authenticateOnPCI

    fun darkThemeEnabled() = darkThemeEnabled
}
