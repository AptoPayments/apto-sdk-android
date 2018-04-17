package com.shift.link.sdk.ui.models.card;

import android.content.res.Resources;

import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the funding source.
 * @author Adrian
 */
public class FundingSourceModel implements Model {

    protected final FundingSourceVo mFundingSource;
    private boolean mIsSelected;
    protected final Resources mResources;

    /**
     * Creates a new {@link FundingSourceModel} instance.
     * @param fundingSource Raw funding source data.
     * @param resources {@link Resources} used to fetch Strings.
     * @param isSelected if it's the current funding source
     */
    public FundingSourceModel(FundingSourceVo fundingSource, Resources resources, boolean isSelected) {
        mFundingSource = fundingSource;
        mResources = resources;
        mIsSelected = isSelected;
    }


    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof FundingSourceModel && ((FundingSourceModel) other).getFundingSourceId().equals(getFundingSourceId());
    }

    public String getFundingSourceName() {
        String capitalizedName = mFundingSource.custodianWallet.custodian.name.substring(0, 1).toUpperCase() + mFundingSource.custodianWallet.custodian.name.substring(1);
        String amount = mResources.getString(R.string.funding_source_dollar_amount, mFundingSource.balance.amount);
        return capitalizedName + " " + amount;
    }

    public String getFundingSourceBalance() {
        return mFundingSource.custodianWallet.balance.amount + " " + mFundingSource.custodianWallet.balance.currency;
    }

    /**
     * @return Funding source ID OR null if not found.
     */
    public String getFundingSourceId() {
        String id = null;

        if (mFundingSource != null) {
            id = mFundingSource.id;
        }

        return id;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }
}
