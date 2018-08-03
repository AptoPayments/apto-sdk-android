package com.shiftpayments.link.sdk.ui.vos;

import java.util.HashMap;
import java.util.Map;

public class ShiftSdkOptions {
    public enum OptionKeys {
        showTransactionList,
        showAccountManagement,
        showChangePIN,
        showLockCard,
        showActivateCardButton,
        showAddFundingSourceButton
    }

    public Map<OptionKeys, Boolean> features;

    public ShiftSdkOptions() {
        features = new HashMap<>();
        features.put(OptionKeys.showTransactionList, true);
        features.put(OptionKeys.showAccountManagement, true);
        features.put(OptionKeys.showChangePIN, true);
        features.put(OptionKeys.showLockCard, true);
        features.put(OptionKeys.showActivateCardButton, true);
        features.put(OptionKeys.showAddFundingSourceButton, true);
    }

    public ShiftSdkOptions(Map<OptionKeys, Boolean> features) {
        this.features = features;
    }
}
