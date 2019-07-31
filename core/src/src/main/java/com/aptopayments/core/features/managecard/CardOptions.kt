package com.aptopayments.core.features.managecard

class CardOptions(
        useBalanceVersionV2: Boolean = false,
        showStatsButton: Boolean = false,
        showNotificationPreferences: Boolean = false,
        showDetailedCardActivityOption: Boolean = false,
        var openingMode: OpeningMode = OpeningMode.STANDALONE,
        var fontOptions: FontOptions = FontOptions()
) {
    private var features: MutableMap<OptionKeys, Boolean> = HashMap()

    private enum class OptionKeys {
        USE_BALANCE_VERSION_V2,
        SHOW_STATS_BUTTON,
        SHOW_NOTIFICATION_PREFERENCES,
        SHOW_DETAILED_CARD_ACTIVITY_OPTION
    }

    enum class OpeningMode { EMBEDDED, STANDALONE }

    init {
        features[OptionKeys.USE_BALANCE_VERSION_V2] = useBalanceVersionV2
        features[OptionKeys.SHOW_STATS_BUTTON] = showStatsButton
        features[OptionKeys.SHOW_NOTIFICATION_PREFERENCES] = showNotificationPreferences
        features[OptionKeys.SHOW_DETAILED_CARD_ACTIVITY_OPTION] = showDetailedCardActivityOption
    }

    fun useBalancesV2() = features[OptionKeys.USE_BALANCE_VERSION_V2] == true

    fun showStatsButton() = features[OptionKeys.SHOW_STATS_BUTTON] == true

    fun showNotificationPreferences() = features[OptionKeys.SHOW_NOTIFICATION_PREFERENCES] == true

    fun showDetailedCardActivityOption() = features[OptionKeys.SHOW_DETAILED_CARD_ACTIVITY_OPTION] == true
}
