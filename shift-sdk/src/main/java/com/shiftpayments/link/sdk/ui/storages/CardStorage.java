package com.shiftpayments.link.sdk.ui.storages;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;

/**
 * Stores card related data.
 * @author Adrian
 */
public class CardStorage {

    private Card mCard;
    private static CardStorage mInstance;
    private BalanceVo mSelectedBalance;
    private String mBalanceId;
    private ApplicationVo mApplication;
    public boolean showCardInfo;
    private String mSelectedCountry;

    /**
     * Creates a new {@link CardStorage} instance.
     */
    private CardStorage() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mCard = null;
    }

    /**
     * @return The single instance of this class.
     */
    public static synchronized CardStorage getInstance() {
        if (mInstance == null) {
            mInstance = new CardStorage();
        }

        return mInstance;
    }

    public Card getCard() {
        // TODO: testing
        mCard.physicalCardActivationRequired = true;
        return mCard;
    }

    public void setCard(Card card) {
        mCard = card;
    }

    public void setBalance(BalanceVo balance) {
        mSelectedBalance = balance;
    }

    public boolean hasBalance() {
        return mSelectedBalance != null;
    }

    public BalanceVo getBalance() {
        return mSelectedBalance;
    }

    public String getBalanceId() {
        return mSelectedBalance.id;
    }

    public boolean hasBalanceId() {
        return mSelectedBalance.id != null && !mSelectedBalance.id.isEmpty();
    }

    public ApplicationVo getApplication() {
        return mApplication;
    }

    public void setApplication(ApplicationVo application) {
        mApplication = application;
    }

    public String getSelectedCountry() {
        return mSelectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        mSelectedCountry = selectedCountry;
    }

    public boolean hasUserSelectedCountry() {
        return mSelectedCountry != null;
    }
}
