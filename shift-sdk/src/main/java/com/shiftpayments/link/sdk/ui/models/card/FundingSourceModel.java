package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

/**
 * Concrete {@link Model} for the funding source.
 * @author Adrian
 */
public class FundingSourceModel implements Model {

    private final FundingSourceVo mFundingSource;
    private boolean mIsSelected;

    /**
     * Creates a new {@link FundingSourceModel} instance.
     * @param fundingSource Raw funding source data.
     * @param isSelected if it's the current funding source
     */
    public FundingSourceModel(FundingSourceVo fundingSource, boolean isSelected) {
        mFundingSource = fundingSource;
        mIsSelected = isSelected;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof FundingSourceModel && ((FundingSourceModel) other).getFundingSourceId().equals(getFundingSourceId());
    }

    public String getFundingSourceName() {
        String capitalizedName = mFundingSource.custodianWallet.custodian.name.substring(0, 1).toUpperCase() + mFundingSource.custodianWallet.custodian.name.substring(1);
        String amount = new AmountVo(mFundingSource.balance.amount, mFundingSource.balance.currency).toString();
        return capitalizedName + " " + amount;
    }

    public String getFundingSourceBalance() {
        String currency = mFundingSource.custodianWallet.balance.currency == null ? "" : mFundingSource.custodianWallet.balance.currency;
        return mFundingSource.custodianWallet.balance.amount + " " + currency;
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

    public String getSpendableAmount() {
        return new AmountVo(mFundingSource.amountSpendable.amount, mFundingSource.amountSpendable.currency).toString();
    }
}
