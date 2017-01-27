package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.api.vos.Card;
import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link SelectFinancialAccountModel} for adding a credit card.
 * @author Adrian
 */
public class SelectCardModel implements SelectFinancialAccountModel {

    private Card mCard;

    /**
     * Creates a new {@link SelectCardModel} instance.
     * @param card
     */
    public SelectCardModel(Card card) {
        mCard = card;
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        switch (mCard.cardType) {
            default:
                return R.drawable.icon_add_doc_generic;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return mCard.cardType.name() + " (...." + mCard.lastFourDigits + ")";
    }
}
