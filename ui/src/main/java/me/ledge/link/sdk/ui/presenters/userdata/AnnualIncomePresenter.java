package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.responses.config.EmploymentStatusVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AnnualIncomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class AnnualIncomePresenter
        extends UserDataPresenter<AnnualIncomeModel, AnnualIncomeView>
        implements AnnualIncomeView.ViewListener {

    private int mIncomeMultiplier;
    private int mMaxIncome;

    private HintArrayAdapter<IdDescriptionPairDisplayVo> mEmploymentStatusesAdapter;

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public AnnualIncomePresenter(AppCompatActivity activity) {
        super(activity);
    }

    /**
     * @param typesList List of housing types.
     * @return Adapter used to display the list of housing types.
     */
    private HintArrayAdapter<IdDescriptionPairDisplayVo> generateEmploymentStatusesAdapter(EmploymentStatusVo[] list) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint
                = new IdDescriptionPairDisplayVo(-1, mActivity.getString(R.string.annual_income_employment_status_hint));

        adapter.add(hint);

        if (list != null) {
            for (EmploymentStatusVo type : list) {
                adapter.add(new IdDescriptionPairDisplayVo(type.employment_status_id, type.description));
            }
        }

        return adapter;
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 4, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public AnnualIncomeModel createModel() {
        return new AnnualIncomeModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        mIncomeMultiplier = mActivity.getResources().getInteger(R.integer.income_increment);
        mMaxIncome = mActivity.getResources().getInteger(R.integer.max_income);

        mModel.setMinIncome(mActivity.getResources().getInteger(R.integer.min_income))
                .setMaxIncome(mMaxIncome)
                .setIncome(mActivity.getResources().getInteger(R.integer.default_income));

        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AnnualIncomeView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setMinMax(mModel.getMinIncome() / mIncomeMultiplier, mModel.getMaxIncome() / mIncomeMultiplier);
        mView.setIncome(mModel.getIncome() / mIncomeMultiplier);
        mView.updateEmploymentStatusError(false);

        mView.showLoading(true);

        if (mEmploymentStatusesAdapter == null) {
            mView.setEmploymentStatusAdapter(generateEmploymentStatusesAdapter(null));

            // Load employment statuses list.
            LedgeLinkUi.getEmploymentStatusesList();
        } else {
            mView.setEmploymentStatusAdapter(mEmploymentStatusesAdapter);

            if (mModel.hasValidEmploymentStatus()) {
                mView.setEmploymentStatus(mEmploymentStatusesAdapter.getPosition(mModel.getEmploymentStatus()));
            }
        }
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
        mModel.setIncome(mView.getIncome() * mIncomeMultiplier);
        mModel.setEmploymentStatus(mView.getEmploymentStatus());

        mView.updateEmploymentStatusError(!mModel.hasValidEmploymentStatus());
        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        String formatted = mActivity.getString(R.string.income_format, value);
        if (value * mIncomeMultiplier >= mMaxIncome) {
            formatted = mActivity.getString(R.string.income_format_and_more, value);
        }

        mView.updateIncomeText(formatted);
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /**
     * TODO: Make handling theses lists and generating Adapters more generic.
     */
    public void setEmploymentStatusesList(EmploymentStatusVo[] list) {
        mEmploymentStatusesAdapter = generateEmploymentStatusesAdapter(list);

        if (mView != null) {
            mView.showLoading(false);
            mView.setEmploymentStatusAdapter(mEmploymentStatusesAdapter);

            if (mModel.hasValidEmploymentStatus()) {
                mView.setEmploymentStatus(mEmploymentStatusesAdapter.getPosition(mModel.getEmploymentStatus()));
            }
        }
    }
}
