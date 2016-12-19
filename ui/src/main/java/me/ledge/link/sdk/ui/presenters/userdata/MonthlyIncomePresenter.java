package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.MonthlyIncomeModel;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.userdata.MonthlyIncomeView;
import me.ledge.link.sdk.ui.widgets.MultiplyTransformer;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * TODO: Class documentation.
 * TODO: Check the striking similarities with {@link AnnualIncomePresenter}.
 * @author Wijnand
 */
public class MonthlyIncomePresenter
        extends UserDataPresenter<MonthlyIncomeModel, MonthlyIncomeView>
        implements MonthlyIncomeView.ViewListener {

    private int mIncomeMultiplier;

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public MonthlyIncomePresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 5, true, true);
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
            DataPointVo.Income income = (DataPointVo.Income) data.getUniqueDataPoint(
                    DataPointVo.DataPointType.Income, new DataPointVo.Income());
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
        super.nextClickHandler();
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
