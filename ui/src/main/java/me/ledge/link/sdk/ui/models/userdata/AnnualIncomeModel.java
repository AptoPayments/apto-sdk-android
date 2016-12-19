package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class AnnualIncomeModel extends AbstractUserDataModel implements UserDataModel {

    private DataPointVo.Income mIncome;
    private int mMinIncome;
    private int mMaxIncome;
    private IdDescriptionPairDisplayVo mEmploymentStatus;
    private IdDescriptionPairDisplayVo mSalaryFrequency;

    public AnnualIncomeModel() {
        init();
    }

    private void init() {
        mEmploymentStatus = null;
        mSalaryFrequency = null;
        mIncome = new DataPointVo.Income();
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
        return hasValidIncome() && hasValidEmploymentStatus() && hasValidSalaryFrequency();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        DataPointVo.Income baseIncome = (DataPointVo.Income) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, new DataPointVo.Income());
        baseIncome.annualGrossIncome = getAnnualIncome();

        DataPointVo.Employment baseEmployment = (DataPointVo.Employment) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Employment,
                new DataPointVo.Employment());
        baseEmployment.employmentStatus = getEmploymentStatus().getKey();
        baseEmployment.salaryFrequency = getSalaryFrequency().getKey();

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

        DataPointVo.Employment baseEmployment = (DataPointVo.Employment) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Employment,
                new DataPointVo.Employment());
        setEmploymentStatus(new IdDescriptionPairDisplayVo(baseEmployment.employmentStatus, ""));
        setSalaryFrequency(new IdDescriptionPairDisplayVo(baseEmployment.salaryFrequency, ""));
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

    public void setIncome(DataPointVo.Income income) {
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

    public IdDescriptionPairDisplayVo getEmploymentStatus() {
        return mEmploymentStatus;
    }

    public void setEmploymentStatus(IdDescriptionPairDisplayVo status) {
        mEmploymentStatus = status;
    }

    public boolean hasValidEmploymentStatus() {
        return hasValidKey(mEmploymentStatus);
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
