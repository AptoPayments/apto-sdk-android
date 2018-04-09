package me.ledge.link.sdk.ui.models.card;

import android.content.res.Resources;

import me.ledge.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import me.ledge.link.sdk.ui.models.Model;

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
     */
    public FundingSourceModel(FundingSourceVo fundingSource, Resources resources) {
        mFundingSource = fundingSource;
        mResources = resources;
        mIsSelected = false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof FundingSourceModel && ((FundingSourceModel) other).getFundingSourceId().equals(getFundingSourceId());
    }

    public String getFundingSourceName() {
        return mFundingSource.custodianWallet.custodian.name;
    }

    public String getFundingSourceBalance() {
        return mFundingSource.balance.amount + " " + mFundingSource.balance.currency;
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
