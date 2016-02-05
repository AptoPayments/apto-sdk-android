package me.ledge.link.sdk.sdk.models.userdata;

import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.sdk.models.Model;
import me.ledge.link.sdk.sdk.vos.UserDataVo;

/**
 * Concrete {@link Model} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountModel extends AbstractUserDataModel implements UserDataModel {

    private int mMinAmount;
    private int mMaxAmount;
    private int mAmount;

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
    public Class getPreviousActivity() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return PersonalInformationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAmount();
    }

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        mBase.loanAmount = getAmount();
        return super.getBaseData();
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);
        setAmount(base.loanAmount);
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
     * @return Whether a valid loan amount has been set.
     */
    public boolean hasValidAmount() {
        return isValid(mAmount);
    }
}
