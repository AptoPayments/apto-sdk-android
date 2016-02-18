package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class IncomeModel extends AbstractUserDataModel implements UserDataModel {

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
        return R.string.income_label;
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
        base.income = mIncome;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);
        setIncome(base.income);
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
    public IncomeModel setMinIncome(int min) {
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
    public IncomeModel setMaxIncome(int max) {
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
    public IncomeModel setIncome(int income) {
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
