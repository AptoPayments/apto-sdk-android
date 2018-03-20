package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.sdk.api.vos.responses.config.IncomeTypeVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.api.vos.responses.config.SalaryFrequencyVo;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AnnualIncomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;

/**
 * Concrete {@link Presenter} for the income screen.
 * @author Wijnand
 */
public class AnnualIncomePresenter
        extends UserDataPresenter<AnnualIncomeModel, AnnualIncomeView>
        implements AnnualIncomeView.ViewListener {

    protected int mIncomeMultiplier;
    private boolean mIncomeValuesReady;
    private AnnualIncomeDelegate mDelegate;

    private HintArrayAdapter<IdDescriptionPairDisplayVo> mIncomeTypesAdapter;
    private HintArrayAdapter<IdDescriptionPairDisplayVo> mSalaryFrequenciesAdapter;

    private boolean mIsEmploymentRequired;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link AnnualIncomePresenter} instance.
     * @param activity Activity.
     */
    public AnnualIncomePresenter(AppCompatActivity activity, AnnualIncomeDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
        mIsEmploymentRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.IncomeSource));
        mIncomeValuesReady = false;
    }

    private void retrieveValuesFromConfig() {
        CompletableFuture
                .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                .exceptionally(ex -> {
                    mView.displayErrorMessage(ex.getMessage());
                    return null;
                })
                .thenAccept(this::contextConfigRetrieved);
    }

    private void contextConfigRetrieved(ConfigResponseVo config) {
        mActivity.runOnUiThread(() -> {
            mIncomeValuesReady = true;
            mModel.setMinIncome((int) config.grossIncomeMin)
                    .setMaxIncome((int) config.grossIncomeMax)
                    .setAnnualIncome((int) config.grossIncomeDefault);
            mIncomeMultiplier = (int) config.grossIncomeIncrements;
            super.populateModelFromStorage();

            mView.setMinMax(mModel.getMinIncome() / mIncomeMultiplier, mModel.getMaxIncome() / mIncomeMultiplier);
            mView.setIncome(mModel.getAnnualIncome() / mIncomeMultiplier);

            if(mIsEmploymentRequired) {
                mLoadingSpinnerManager.showLoading(isViewLoading());
                if (mIncomeTypesAdapter == null) {
                    mView.setIncomeTypesAdapter(generateIncomeTypesAdapter(null));
                    handleConfigResponse(config);
                } else {
                    mView.setIncomeTypesAdapter(mIncomeTypesAdapter);

                    if (mModel.hasValidIncomeType()) {
                        mView.setIncomeType(mModel.getIncomeType().getKey());
                    }
                }

                if (mSalaryFrequenciesAdapter == null) {
                    mView.setSalaryFrequencyAdapter(generateSalaryFrequenciesAdapter(null));
                    handleConfigResponse(config);
                } else {
                    mView.setSalaryFrequencyAdapter(mSalaryFrequenciesAdapter);

                    if (mModel.hasValidSalaryFrequency()) {
                        mView.setSalaryFrequency(mModel.getSalaryFrequency().getKey());
                    }
                }
            }
            else {
                mLoadingSpinnerManager.showLoading(!mIncomeValuesReady);
            }
        });
    }
    
    private boolean isViewLoading() {
        return mIncomeTypesAdapter == null || mSalaryFrequenciesAdapter == null ||
                !mIncomeValuesReady;
    }

    /**
     * @param list List of employment statuses.
     * @return Adapter used to display the list of employment statuses.
     */
    private HintArrayAdapter<IdDescriptionPairDisplayVo> generateIncomeTypesAdapter(IncomeTypeVo[] list) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint
                = new IdDescriptionPairDisplayVo(-1, mActivity.getString(R.string.annual_income_income_type_hint));

        adapter.add(hint);

        if (list != null) {
            for (IncomeTypeVo type : list) {
                adapter.add(new IdDescriptionPairDisplayVo(type.income_type_id, type.description));
            }
        }

        return adapter;
    }

    /**
     * TODO: This is almost exactly the same as {@link #generateIncomeTypesAdapter(IncomeTypeVo[])}.
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
    public AnnualIncomeModel createModel() {
        return new AnnualIncomeModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AnnualIncomeView view) {
        super.attachView(view);
        mView.setListener(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);

        mView.showEmploymentFields(mIsEmploymentRequired);
        mView.updateIncomeTypeError(false);
        mView.updateSalaryFrequencyError(false);
        retrieveValuesFromConfig();
    }

    @Override
    public void onBack() {
        mDelegate.annualIncomeOnBackPressed();
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
        mModel.setAnnualIncome(mView.getIncome() * mIncomeMultiplier);

        if(mIsEmploymentRequired) {
            mModel.setIncomeType(mView.getIncomeType());
            mModel.setSalaryFrequency(mView.getSalaryFrequency());

            mView.updateIncomeTypeError(!mModel.hasValidIncomeType());
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
        mView.updateIncomeText(mActivity.getString(R.string.annual_income_format, value * mIncomeMultiplier));
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
    private void setIncomeTypesList(IncomeTypeVo[] list) {
        mIncomeTypesAdapter = generateIncomeTypesAdapter(list);

        if (mView != null) {
            mActivity.runOnUiThread(() -> {
                mLoadingSpinnerManager.showLoading(isViewLoading());
                mView.setIncomeTypesAdapter(mIncomeTypesAdapter);

                if (mModel.hasValidIncomeType()) {
                    mView.setIncomeType(mModel.getIncomeType().getKey());
                }
            });
        }
    }

    /**
     * TODO: Make handling theses lists and generating Adapters more generic.
     */
    private void setSalaryFrequenciesList(SalaryFrequencyVo[] list) {
        mSalaryFrequenciesAdapter = generateSalaryFrequenciesAdapter(list);

        if (mView != null) {
            mActivity.runOnUiThread(() -> {
                mLoadingSpinnerManager.showLoading(isViewLoading());
                mView.setSalaryFrequencyAdapter(mSalaryFrequenciesAdapter);

                if (mModel.hasValidSalaryFrequency()) {
                    mView.setSalaryFrequency(mModel.getSalaryFrequency().getKey());
                }
            });
        }
    }

    /**
     * Called when the employment statuses / salary frequency lists API response has been received.
     * @param response API response.
     */
    private void handleConfigResponse(ConfigResponseVo response) {
        if (isIncomeTypesPresent(response)) {
            setIncomeTypesList(response.incomeTypeOpts.data);
        }
        if (isSalaryFrequencyPresent(response)) {
            setSalaryFrequenciesList(response.salaryFrequencyOpts.data);
        }
    }

    private boolean isIncomeTypesPresent(ConfigResponseVo response) {
        return response!=null && response.incomeTypeOpts!=null;
    }

    private boolean isSalaryFrequencyPresent(ConfigResponseVo response) {
        return response!=null && response.salaryFrequencyOpts!=null;
    }
}
