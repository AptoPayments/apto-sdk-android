package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a virtual credit card.
 * @author Adrian
 */
public class AddVirtualCardModel implements AddFinancialAccountModel {

    /**
     * Creates a new {@link AddVirtualCardModel} instance.
     */
    public AddVirtualCardModel() {

    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.ic_card_virtual;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return R.string.add_financial_account_virtual_card;
    }

    @Override
    public int getDescription() {
        return R.string.add_financial_account_virtual_card_description;
    }
}
