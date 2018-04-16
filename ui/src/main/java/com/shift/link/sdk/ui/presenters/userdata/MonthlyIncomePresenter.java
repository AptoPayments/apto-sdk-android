package com.shift.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.Income;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.MonthlyIncomeModel;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.views.userdata.MonthlyIncomeView;
import com.shift.link.sdk.ui.widgets.MultiplyTransformer;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * TODO: Class documentation.
 * TODO: Check the striking similarities with {@link AnnualIncomePresenter}.
 * @author Wijnand
 */
public class MonthlyIncomePresenter
        extends UserDataPresenter<MonthlyIncomeModel, MonthlyIncomeView>
        implements MonthlyIncomeView.ViewListener {

    protected int mIncomeMultiplier;
    private MonthlyIncomeDelegate mDelegate;

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public MonthlyIncomePresenter(AppCompatActivity activity, MonthlyIncomeDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public MonthlyIncomeModel createModel() {
        return new MonthlyIncomeModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        int annualIncome = mActivity.getResources().getInteger(R.integer.max_income);
        DataPointList data = UserStorage.getInstance().getUserData();

        if (data != null) {
            Income income = (Income) data.getUniqueDataPoint(
                    DataPointVo.DataPointType.Income, new Income());
            annualIncome = (int) income.annualGrossIncome;
        }

        mIncomeMultiplier = mActivity.getResources().getInteger(R.integer.monthly_income_increment);
        int maxIncome = (int) Math.ceil(annualIncome / (12.0 * mIncomeMultiplier)) * mIncomeMultiplier;

        mModel.setMinIncome(mActivity.getResources().getInteger(R.integer.min_income))
                .setMaxIncome(maxIncome)
                .setMonthlyIncome(maxIncome / 2);

        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(MonthlyIncomeView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setSeekBarTransformer(new MultiplyTransformer(mIncomeMultiplier));
        mView.setMinMax(mModel.getMinIncome() / mIncomeMultiplier, mModel.getMaxIncome() / mIncomeMultiplier);
        mView.setIncome(mModel.getMonthlyIncome() / mIncomeMultiplier);
    }

    @Override
    public void onBack() {
        mDelegate.monthlyIncomeOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        mModel.setMonthlyIncome(mView.getIncome() * mIncomeMultiplier);
        if (mModel.hasAllData()) {
            saveData();
            mDelegate.monthlyIncomeStored();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mView.updateIncomeText(mActivity.getString(R.string.monthly_income_format, value * mIncomeMultiplier));
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

}
