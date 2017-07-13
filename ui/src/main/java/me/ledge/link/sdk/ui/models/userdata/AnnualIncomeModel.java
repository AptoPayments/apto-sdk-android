package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.IncomeSource;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class AnnualIncomeModel extends AbstractUserDataModel implements UserDataModel {

    private Income mIncome;
    private int mMinIncome;
    private int mMaxIncome;
    private IdDescriptionPairDisplayVo mIncomeType;
    private IdDescriptionPairDisplayVo mSalaryFrequency;

    public AnnualIncomeModel() {
        init();
    }

    private void init() {
        mIncomeType = null;
        mSalaryFrequency = null;
        mIncome = new Income();
    }

    private boolean hasValidKey(IdDescriptionPairDisplayVo pair) {
        return pair != null && pair.getKey() >= 0;
    }

    /**
     * @param income Income to validate.
     * @return Whether the income is within the allowed range.
     */
    protected boolean isValid(long income) {
        return income >= mMinIncome && income <= mMaxIncome;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.annual_income_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidIncome() && hasValidIncomeType() && hasValidSalaryFrequency();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasValidIncome()) {
            Income baseIncome = (Income) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Income, new Income());
            baseIncome.annualGrossIncome = getAnnualIncome();
        }

        if(hasValidIncomeType()) {
            IncomeSource baseEmployment = (IncomeSource) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.IncomeSource, new IncomeSource());
            baseEmployment.incomeType = getIncomeType();
        }
        if(hasValidSalaryFrequency()) {
            IncomeSource baseEmployment = (IncomeSource) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.IncomeSource, new IncomeSource());
            baseEmployment.salaryFrequency = getSalaryFrequency();
        }

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        Income baseIncome = (Income) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, null);
        if(baseIncome!=null) {
            setIncome(baseIncome);
        }

        IncomeSource baseEmployment = (IncomeSource) base.getUniqueDataPoint(
                DataPointVo.DataPointType.IncomeSource, null);
        if(baseEmployment!=null) {
            setIncomeType(baseEmployment.incomeType);
            setSalaryFrequency(baseEmployment.salaryFrequency);
        }
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
    public AnnualIncomeModel setMinIncome(int min) {
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
    public AnnualIncomeModel setMaxIncome(int max) {
        mMaxIncome = max;
        return this;
    }

    public void setIncome(Income income) {
        setAnnualIncome(income.annualGrossIncome);
    }

    /**
     * @return annual income.
     */
    public long getAnnualIncome() {
        return mIncome.annualGrossIncome;
    }

    /**
     * Stores the income.
     * @param income The income.
     * @return Self reference for easy method chaining.
     */
    public AnnualIncomeModel setAnnualIncome(long income) {
        if (isValid(income)) {
            mIncome.annualGrossIncome = income;
        }

        return this;
    }

    /**
     * @return Whether a valid income has been set.
     */
    public boolean hasValidIncome() {
        return isValid(mIncome.annualGrossIncome);
    }

    public IdDescriptionPairDisplayVo getIncomeType() {
        return mIncomeType;
    }

    public void setIncomeType(IdDescriptionPairDisplayVo status) {
        mIncomeType = status;
    }

    public boolean hasValidIncomeType() {
        return hasValidKey(mIncomeType);
    }

    public IdDescriptionPairDisplayVo getSalaryFrequency() {
        return mSalaryFrequency;
    }

    public void setSalaryFrequency(IdDescriptionPairDisplayVo frequency) {
        mSalaryFrequency = frequency;
    }

    public boolean hasValidSalaryFrequency() {
        return hasValidKey(mSalaryFrequency);
    }
}
