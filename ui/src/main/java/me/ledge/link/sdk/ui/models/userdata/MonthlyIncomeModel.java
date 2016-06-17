package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * TODO: Class documentation.
 * TODO: A lot copied straight from {@link AnnualIncomeModel}.
 * @author Wijnand
 */
public class MonthlyIncomeModel extends AbstractUserDataModel implements UserDataModel {

    private int mMinIncome;
    private int mMaxIncome;
    private int mIncome;

    /**
     * @param income Income to validate.
     * @return Whether the income is within the allowed range.
     */
    protected boolean isValid(int income) {
        return income >= mMinIncome && income <= mMaxIncome;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.monthly_income_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidIncome();
    }

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.monthlyNetIncome = getIncome();

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);
        setIncome(base.monthlyNetIncome);
    }

    /**
     * @return Minimum income.
     */
    public int getMinIncome() {
        return mMinIncome;
    }

    /**
     * Stores a new minimum income.
     * @param min Minimum income.
     * @return Self reference for easy method chaining.
     */
    public MonthlyIncomeModel setMinIncome(int min) {
        mMinIncome = min;
        return this;
    }

    /**
     * @return Maximum income.
     */
    public int getMaxIncome() {
        return mMaxIncome;
    }

    /**
     * Stores a new maximum income.
     * @param max Maximum income.
     * @return Self reference for easy method chaining.
     */
    public MonthlyIncomeModel setMaxIncome(int max) {
        mMaxIncome = max;
        return this;
    }

    /**
     * @return income.
     */
    public int getIncome() {
        return mIncome;
    }

    /**
     * Stores the income.
     * @param income The income.
     * @return Self reference for easy method chaining.
     */
    public MonthlyIncomeModel setIncome(int income) {
        if (isValid(income)) {
            mIncome = income;
        }

        return this;
    }

    /**
     * @return Whether a valid income has been set.
     */
    public boolean hasValidIncome() {
        return isValid(mIncome);
    }
}
