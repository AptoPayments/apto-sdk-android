package com.shiftpayments.link.sdk.ui.models.custodianselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;

/**
 * Concrete {@link AddCoinbaseModel} for linking coinbase account.
 * @author Adrian
 */
public class AddCoinbaseModel implements AddCustodianModel, ActivityModel {

    /**
     * Creates a new {@link AddCoinbaseModel} instance.
     */
    public AddCoinbaseModel() {

    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_coinbase;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return R.string.add_custodian_coinbase_account;
    }

    @Override
    public int getDescription() {
        return R.string.add_custodian_coinbase_account_description;
    }

    @Override
    public int getActivityTitleResource() {
        return R.string.add_custodian_list_title;
    }

    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }
}
