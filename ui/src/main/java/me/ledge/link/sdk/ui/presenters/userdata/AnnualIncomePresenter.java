package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.datapoints.Employment;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.EmploymentStatusVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.config.SalaryFrequencyVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AnnualIncomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class AnnualIncomePresenter
        extends UserDataPresenter<AnnualIncomeModel, AnnualIncomeView>
        implements AnnualIncomeView.ViewListener {

    private int mIncomeMultiplier;
    private int mMaxIncome;
    private AnnualIncomeDelegate mDelegate;

    private HintArrayAdapter<IdDescriptionPairDisplayVo> mEmploymentStatusesAdapter;
    private HintArrayAdapter<IdDescriptionPairDisplayVo> mSalaryFrequenciesAdapter;

    private boolean mIsEmploymentRequired;

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public AnnualIncomePresenter(AppCompatActivity activity, AnnualIncomeDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
        mIsEmploymentRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(8));
    }

    /**
     * @param list List of employment statuses.
     * @return Adapter used to display the list of employment statuses.
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

    /**
     * TODO: This is almost exactly the same as {@link #generateEmploymentStatusesAdapter}.
     * @param list List of salary frequencies.
     * @return Adapter used to display the list of salary frequencies.
     */
    private HintArrayAdapter<IdDescriptionPairDisplayVo> generateSalaryFrequenciesAdapter(SalaryFrequencyVo[] list) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint
                = new IdDescriptionPairDisplayVo(-1, mActivity.getString(R.string.annual_income_salary_frequency_hint));

        adapter.add(hint);

        if (list != null) {
            for (SalaryFrequencyVo frequency : list) {
                adapter.add(new IdDescriptionPairDisplayVo(frequency.salary_frequency_id, frequency.description));
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
                .setAnnualIncome(mActivity.getResources().getInteger(R.integer.default_income));

        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AnnualIncomeView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
        mView.setListener(this);
        mView.setMinMax(mModel.getMinIncome() / mIncomeMultiplier, mModel.getMaxIncome() / mIncomeMultiplier);
        mView.setIncome(mModel.getAnnualIncome() / mIncomeMultiplier);
        mView.showEmploymentFields(mIsEmploymentRequired);
        mView.updateEmploymentStatusError(false);
        mView.updateSalaryFrequencyError(false);

        if(mIsEmploymentRequired) {
            mView.showLoading(mEmploymentStatusesAdapter == null || mSalaryFrequenciesAdapter == null);

            if (mEmploymentStatusesAdapter == null) {
                mView.setEmploymentStatusAdapter(generateEmploymentStatusesAdapter(null));

                // Load employment statuses list.
                LedgeLinkUi.getEmploymentStatusesList();
            } else {
                mView.setEmploymentStatusAdapter(mEmploymentStatusesAdapter);

                if (mModel.hasValidEmploymentStatus()) {
                    mView.setEmploymentStatus(mModel.getEmploymentStatus().getKey());
                }
            }

            // TODO: Abstract this!
            if (mSalaryFrequenciesAdapter == null) {
                mView.setSalaryFrequencyAdapter(generateSalaryFrequenciesAdapter(null));

                // Load salary frequencies list.
                LedgeLinkUi.getSalaryFrequenciesList();
            } else {
                mView.setSalaryFrequencyAdapter(mSalaryFrequenciesAdapter);

                if (mModel.hasValidSalaryFrequency()) {
                    mView.setSalaryFrequency(mModel.getSalaryFrequency().getKey());
                }
            }
        }
        else {
            mView.showLoading(false);
        }
    }

    @Override
    public void onBack() {
        mDelegate.annualIncomeOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        mModel.setAnnualIncome(mView.getIncome() * mIncomeMultiplier);

        if(mIsEmploymentRequired) {
            mModel.setEmploymentStatus(mView.getEmploymentStatus());
            mModel.setSalaryFrequency(mView.getSalaryFrequency());

            mView.updateEmploymentStatusError(!mModel.hasValidEmploymentStatus());
            mView.updateSalaryFrequencyError(!mModel.hasValidSalaryFrequency());

            if(mModel.hasAllData()) {
                saveDataAndExit();
            }
        }
        else {
            if(mModel.hasValidIncome()) {
                saveDataAndExit();
            }
        }
    }

    private void saveDataAndExit() {
        saveData();
        mDelegate.annualIncomeStored();
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
            mView.showLoading(mEmploymentStatusesAdapter == null || mSalaryFrequenciesAdapter == null);
            mView.setEmploymentStatusAdapter(mEmploymentStatusesAdapter);

            if (mModel.hasValidEmploymentStatus()) {
                mView.setEmploymentStatus(mModel.getEmploymentStatus().getKey());
            }
        }
    }

    /**
     * TODO: Make handling theses lists and generating Adapters more generic.
     */
    public void setSalaryFrequenciesList(SalaryFrequencyVo[] list) {
        mSalaryFrequenciesAdapter = generateSalaryFrequenciesAdapter(list);

        if (mView != null) {
            mView.showLoading(mEmploymentStatusesAdapter == null || mSalaryFrequenciesAdapter == null);
            mView.setSalaryFrequencyAdapter(mSalaryFrequenciesAdapter);

            if (mModel.hasValidSalaryFrequency()) {
                mView.setSalaryFrequency(mModel.getSalaryFrequency().getKey());
            }
        }
    }

    /**
     * Called when the employment statuses / salary frequency lists API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleConfigResponse(ConfigResponseVo response) {
        if (isEmploymentStatusesPresent(response)) {
            setEmploymentStatusesList(response.employmentStatusOpts.data);
        }
        if (isSalaryFrequencyPresent(response)) {
            setSalaryFrequenciesList(response.salaryFrequencyOpts.data);
        }
    }

/*    *//**
     * Called when the salary frequencies list API response has been received.
     * @param response API response.
     *//*
    @Subscribe
    public void handleSalaryFrequenciesList(ConfigResponseVo response) {
        if (isSalaryFrequencyPresent(response)) {
            setSalaryFrequenciesList(response.salaryFrequencyOpts.data);
        }
    }*/

    private boolean isEmploymentStatusesPresent(ConfigResponseVo response) {
        return response!=null && response.employmentStatusOpts!=null;
    }

    private boolean isSalaryFrequencyPresent(ConfigResponseVo response) {
        return response!=null && response.salaryFrequencyOpts!=null;
    }
}
