package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;

/**
 * TODO: Class documentation.
 * TODO: A lot copied straight from {@link AnnualIncomeModel}.
 * @author Wijnand
 */
public class MonthlyIncomeModel extends AbstractUserDataModel implements UserDataModel {

    private DataPointVo.Income mIncome;
    private int mMinIncome;
    private int mMaxIncome;

    public MonthlyIncomeModel() {
        init();
    }

    private void init() {
        mIncome = new DataPointVo.Income();
    }

    /**
     * @param income Income to validate.
     * @return Whether the income is within the allowed range.
     */
    protected boolean isValid(double income) {
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
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        DataPointVo.Income baseIncome = (DataPointVo.Income) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, new DataPointVo.Income());
        baseIncome.monthlyNetIncome = getMonthlyIncome();

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        DataPointVo.Income baseIncome = (DataPointVo.Income) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Income,
                new DataPointVo.Income());
        setIncome(baseIncome);
    }

    public void setIncome(DataPointVo.Income mIncome) {
        setMonthlyIncome(mIncome.monthlyNetIncome);
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
    public double getMonthlyIncome() {
        return mIncome.monthlyNetIncome;
    }

    /**
     * Stores the income.
     * @param income The income.
     * @return Self reference for easy method chaining.
     */
    public MonthlyIncomeModel setMonthlyIncome(double income) {
        if (isValid(income)) {
            mIncome.monthlyNetIncome = income;
        }

        return this;
    }

    /**
     * @return Whether a valid income has been set.
     */
    public boolean hasValidIncome() {
        return isValid(mIncome.monthlyNetIncome);
    }
}
