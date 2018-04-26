package com.shiftpayments.link.sdk.ui.models.link;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;

/**
 * Concrete {@link Model} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountModel extends AbstractLoanDataModel implements LoanDataModel {

    private int mMinAmount;
    private int mMaxAmount;
    private int mAmount;
    private IdDescriptionPairDisplayVo mLoanPurpose;

    /**
     * @param amount Amount to validate.
     * @return Whether the amount is within the allowed range.
     */
    protected boolean isValid(int amount) {
        return amount >= mMinAmount && amount <= mMaxAmount;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_amount_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAmount() && hasValidLoanPurpose();
    }

    /** {@inheritDoc} */
    @Override
    public LoanDataVo getBaseData() {
        LoanDataVo base = super.getBaseData();
        base.loanAmount = getAmount();
        base.loanPurpose = getLoanPurpose();

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(LoanDataVo base) {
        super.setBaseData(base);

        setAmount(base.loanAmount);
        setLoanPurpose(base.loanPurpose);
    }

    /**
     * @return Minimum loan amount.
     */
    public int getMinAmount() {
        return mMinAmount;
    }

    /**
     * Stores a new minimum loan amount.
     * @param min Minimum loan amount.
     * @return Self reference for easy method chaining.
     */
    public LoanAmountModel setMinAmount(int min) {
        mMinAmount = min;
        return this;
    }

    /**
     * @return Maximum loan amount.
     */
    public int getMaxAmount() {
        return mMaxAmount;
    }

    /**
     * Stores a new maximum loan amount.
     * @param max Maximum loan amount.
     * @return Self reference for easy method chaining.
     */
    public LoanAmountModel setMaxAmount(int max) {
        mMaxAmount = max;
        return this;
    }

    /**
     * @return Loan amount.
     */
    public int getAmount() {
        return mAmount;
    }

    /**
     * Stores the loan amount.
     * @param amount The loan amount.
     * @return Self reference for easy method chaining.
     */
    public LoanAmountModel setAmount(int amount) {
        if (isValid(amount)) {
            mAmount = amount;
        }

        return this;
    }

    /**
     * @return The selected loan purpose.
     */
    public IdDescriptionPairDisplayVo getLoanPurpose() {
        return mLoanPurpose;
    }

    /**
     * Stores a new loan purpose.
     * @param loanPurpose New loan purpose.
     */
    public void setLoanPurpose(IdDescriptionPairDisplayVo loanPurpose) {
        mLoanPurpose = loanPurpose;
    }

    /**
     * @return Whether a valid loan amount has been set.
     */
    public boolean hasValidAmount() {
        return isValid(mAmount);
    }

    /**
     * @return Whether a valid loan purpose has been selected.
     */
    public boolean hasValidLoanPurpose() {
        return mLoanPurpose != null && mLoanPurpose.getKey() >= 0;
    }
}
