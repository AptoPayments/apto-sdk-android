package com.shiftpayments.link.sdk.ui.storages;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;

/**
 * Stores card related data.
 * @author Adrian
 */
public class CardStorage {

    private Card mCard;
    private static CardStorage mInstance;
    private String mBalanceId;
    private ApplicationVo mApplication;
    public boolean showCardInfo;

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
        return mCard;
    }

    public void setCard(Card card) {
        mCard = card;
    }

    public void setBalanceId(String balanceId) {
        mBalanceId = balanceId;
    }

    public String getBalanceId() {
        return mBalanceId;
    }

    public boolean hasBalanceId() {
        return mBalanceId != null && !mBalanceId.isEmpty();
    }

    public ApplicationVo getApplication() {
        return mApplication;
    }

    public void setApplication(ApplicationVo application) {
        mApplication = application;
    }
}
