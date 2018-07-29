package com.shiftpayments.link.sdk.ui.utils;

import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;

public class ShiftSdkOptionsBuilder {

    boolean _showTransactionList = true;
    boolean _showAccountManagement = true;
    boolean _showChangePIN = true;
    boolean _showLockCard = true;
    boolean _showActivateCardButton = true;
    boolean _showAddFundingSourceButton = true;

    public ShiftSdkOptionsBuilder() { }

    public ShiftSdkOptions buildOptions()
    {
        return new ShiftSdkOptions(_showTransactionList, _showAccountManagement, _showChangePIN,
                            _showLockCard, _showActivateCardButton, _showAddFundingSourceButton);
    }

    public ShiftSdkOptionsBuilder enableTransactionList(boolean showTransactionList)
    {
        this._showTransactionList = showTransactionList;
        return this;
    }

    public ShiftSdkOptionsBuilder enableAccountManagement(boolean showAccountManagement)
    {
        this._showAccountManagement = showAccountManagement;
        return this;
    }

    public ShiftSdkOptionsBuilder enablePinChange(boolean showChangePin)
    {
        this._showChangePIN = showChangePin;
        return this;
    }

    public ShiftSdkOptionsBuilder enableCardLocking(boolean showLockCard)
    {
        this._showLockCard = showLockCard;
        return this;
    }

    public ShiftSdkOptionsBuilder enableCardActivation(boolean showActivateCardButton)
    {
        this._showActivateCardButton = showActivateCardButton;
        return this;
    }

    public ShiftSdkOptionsBuilder enableAddingFundingSources(boolean showAddFundingSourceButton)
    {
        this._showAddFundingSourceButton = showAddFundingSourceButton;
        return this;
    }
}
