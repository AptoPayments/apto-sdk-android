package com.aptopayments.core.features.managecard

class CardOptions(
        showStatsButton: Boolean = false,
        showNotificationPreferences: Boolean = false,
        showDetailedCardActivityOption: Boolean = false,
        hideFundingSourcesReconnectButton: Boolean = false,
        showAccountSettingsButton: Boolean = true,
        var openingMode: OpeningMode = OpeningMode.STANDALONE,
        var fontOptions: FontOptions = FontOptions()
) {
    private var features: MutableMap<OptionKeys, Boolean> = mutableMapOf()

    private enum class OptionKeys {
        SHOW_STATS_BUTTON,
        SHOW_NOTIFICATION_PREFERENCES,
        SHOW_DETAILED_CARD_ACTIVITY_OPTION,
        HIDE_FUNDING_SOURCES_RECONNECT_BUTTON,
        SHOW_ACCOUNT_SETTINGS_BUTTON
    }

    enum class OpeningMode { EMBEDDED, STANDALONE }

    init {
        features[OptionKeys.SHOW_STATS_BUTTON] = showStatsButton
        features[OptionKeys.SHOW_NOTIFICATION_PREFERENCES] = showNotificationPreferences
        features[OptionKeys.SHOW_DETAILED_CARD_ACTIVITY_OPTION] = showDetailedCardActivityOption
        features[OptionKeys.HIDE_FUNDING_SOURCES_RECONNECT_BUTTON] = hideFundingSourcesReconnectButton
        features[OptionKeys.SHOW_ACCOUNT_SETTINGS_BUTTON] = showAccountSettingsButton
    }

    fun showStatsButton() = features[OptionKeys.SHOW_STATS_BUTTON] == true

    fun showNotificationPreferences() = features[OptionKeys.SHOW_NOTIFICATION_PREFERENCES] == true

    fun showDetailedCardActivityOption() = features[OptionKeys.SHOW_DETAILED_CARD_ACTIVITY_OPTION] == true

    fun hideFundingSourcesReconnectButton() = features[OptionKeys.HIDE_FUNDING_SOURCES_RECONNECT_BUTTON] == true

    fun showAccountSettingsButton() = features[OptionKeys.SHOW_ACCOUNT_SETTINGS_BUTTON] == true
}
