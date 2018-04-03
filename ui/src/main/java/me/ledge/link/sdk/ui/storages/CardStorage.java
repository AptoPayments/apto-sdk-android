package me.ledge.link.sdk.ui.storages;

import me.ledge.link.sdk.api.vos.datapoints.VirtualCard;

/**
 * Stores card related data.
 * @author Adrian
 */
public class CardStorage {

    private VirtualCard mCard;
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


    public VirtualCard getCard() {
        return mCard;
    }

    public void setCard(VirtualCard card) {
        mCard = card;
    }

    public boolean hasCard() {
        return mCard != null;
    }
}
