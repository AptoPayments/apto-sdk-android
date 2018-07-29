package com.shiftpayments.link.sdk.ui.vos;

public class ShiftSdkOptions {
    public boolean showTransactionList;
    public boolean showAccountManagement;
    public boolean showChangePIN;
    public boolean showLockCard;
    public boolean showActivateCardButton;
    public boolean showAddFundingSourceButton;

    public ShiftSdkOptions(boolean showTransactionList, boolean showAccountManagement,
                           boolean showChangePIN, boolean showLockCard,
                           boolean showActivateCardButton, boolean showAddFundingSourceButton) {
        this.showTransactionList = showTransactionList;
        this.showAccountManagement = showAccountManagement;
        this.showChangePIN = showChangePIN;
        this.showLockCard = showLockCard;
        this.showActivateCardButton = showActivateCardButton;
        this.showAddFundingSourceButton = showAddFundingSourceButton;
    }
}
