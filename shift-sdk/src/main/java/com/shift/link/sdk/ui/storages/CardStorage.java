package com.shift.link.sdk.ui.storages;

import com.shift.link.sdk.api.vos.Card;

/**
 * Stores card related data.
 * @author Adrian
 */
public class CardStorage {

    private Card mCard;
    private static CardStorage mInstance;

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
}
