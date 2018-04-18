package com.shift.link.sdk.ui.models.financialaccountselector;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.ui.R;

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
        switch (mCard.cardNetwork) {
            case VISA:
                return R.drawable.icon_visa;
            case MASTERCARD:
                return R.drawable.icon_mastercard;
            case AMEX:
                return R.drawable.icon_amex;
            default:
                return R.drawable.icon_add_card;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return mCard.cardNetwork.name() + " (...." + mCard.lastFourDigits + ")";
    }

    @Override
    public FinancialAccountVo getFinancialAccount() {
        return mCard;
    }
}
