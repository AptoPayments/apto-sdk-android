package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.api.vos.responses.config.ContentVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.link.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.link.LoanAmountView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.MultiplyTransformer;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

import static me.ledge.link.sdk.api.vos.responses.config.ContentVo.formatValues.plain_text;

/**
 * Concrete {@link Presenter} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountPresenter
        extends LoanDataPresenter<LoanAmountModel, LoanAmountView>
        implements LoanAmountView.ViewListener {

    private int mAmountIncrement;
    private HintArrayAdapter<IdDescriptionPairDisplayVo> mPurposeAdapter;
    private LoanDataDelegate mDelegate;
    private boolean isMaxLoanAmountReady;
    private boolean isMinLoanAmountReady;
    private boolean isDefaultLoanAmountReady;
    private boolean isLoanPurposesReady;
    private boolean isLoanIncrementsReady;
    private boolean isLoanAmountRequired;
    private boolean isLoanPurposeRequired;
    private String mDisclaimersText;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link LoanAmountPresenter} instance.
     * @param activity Activity.
     */
    public LoanAmountPresenter(AppCompatActivity activity, LoanDataDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
        mPurposeAdapter = null;
        isMaxLoanAmountReady = false;
        isMinLoanAmountReady = false;
        isDefaultLoanAmountReady = false;
        isLoanPurposesReady = false;
        isLoanIncrementsReady = false;
        isLoanAmountRequired = !ConfigStorage.getInstance().getSkipLoanAmount();
        isLoanPurposeRequired = !ConfigStorage.getInstance().getSkipLoanPurpose();
        retrieveValuesFromConfig();
    }

    /**
     * @param loanPurposesList List of loan purposes.
     * @return A new {@link HintArrayAdapter} to use for the loan purpose drop-down.
     */
    private HintArrayAdapter<IdDescriptionPairDisplayVo> getPurposeAdapter(LoanPurposeVo[] loanPurposesList) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint
                = new IdDescriptionPairDisplayVo(-1, mActivity.getString(R.string.loan_amount_purpose_hint));

        adapter.add(hint);

        if (loanPurposesList != null) {
            for (LoanPurposeVo purpose : loanPurposesList) {
                adapter.add(new IdDescriptionPairDisplayVo(purpose.loan_purpose_id, purpose.description));
            }
        }

        return adapter;
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        boolean enableNextButton = !((LoanInfoModule) ModuleManager.getInstance().getCurrentModule()).userHasAllRequiredData;
        return new StepperConfiguration(TOTAL_STEPS, 0, true, enableNextButton);
    }

    /** {@inheritDoc} */
    @Override
    public LoanAmountModel createModel() {
        return new LoanAmountModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mView.setListener(this);
        mLoadingSpinnerManager.showLoading(true);
        if(((LoanInfoModule) ModuleManager.getInstance().getCurrentModule()).userHasAllRequiredData) {
            mView.showGetOffersButtonAndDisclaimers(true);
            if (mDisclaimersText == null) {
                mLoadingSpinnerManager.showLoading(true);
                CompletableFuture
                        .supplyAsync(()-> ConfigStorage.getInstance().getLoanProducts())
                        .exceptionally(ex -> {
                            errorReceived(ex.getMessage());
                            return null;
                        })
                        .thenAccept(this::partnerDisclaimersListRetrieved);
            } else {
                setDisclaimers(mDisclaimersText);
            }
        }
        else {
            mView.showGetOffersButtonAndDisclaimers(false);
        }

        mView.showLoanAmount(isLoanAmountRequired);
        mView.showLoanPurpose(isLoanPurposeRequired);

        if(isLoanPurposeRequired) {
            if (mPurposeAdapter == null) {
                mView.setPurposeAdapter(getPurposeAdapter(null));

                // Load loan purpose list.
                CompletableFuture
                        .supplyAsync(()-> ConfigStorage.getInstance().getLoanPurposes())
                        .exceptionally(ex -> {
                            errorReceived(ex.getMessage());
                            return null;
                        })
                        .thenAccept(this::loanPurposesListRetrieved);
            } else {
                mView.setPurposeAdapter(mPurposeAdapter);

                if (mModel.hasValidLoanPurpose()) {
                    mView.setPurpose(mPurposeAdapter.getPosition(mModel.getLoanPurpose()));
                }
            }
        }
    }

    @Override
    public void onBack() {
        mDelegate.loanDataOnBackPressed();
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
        if(isLoanAmountRequired) {
            mModel.setAmount(mView.getAmount() * mAmountIncrement);
        }
        if(isLoanPurposeRequired) {
            mModel.setLoanPurpose(mView.getPurpose());
            mView.updatePurposeError(!mModel.hasValidLoanPurpose());
        }

        if (isLoanAmountRequired && isLoanPurposeRequired && mModel.hasAllData()) {
            saveDataAndExit();
        }
        else if (!isLoanPurposeRequired && isLoanAmountRequired && mModel.hasValidAmount()) {
            saveDataAndExit();
        }
        else if (!isLoanAmountRequired && isLoanPurposeRequired && mModel.hasValidLoanPurpose()) {
            saveDataAndExit();
        }
    }

    private void saveDataAndExit() {
        saveData();
        mDelegate.loanDataPresented();
    }

    /** {@inheritDoc} */
    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        mView.updateAmountText(mActivity.getString(R.string.loan_amount_format, value * mAmountIncrement));
    }

    /** {@inheritDoc} */
    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) { /* Do nothing. */ }

    private void retrieveValuesFromConfig() {
        if(isLoanAmountRequired) {
            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getMaxLoanAmount())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::maxLoanAmountRetrieved);

            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getMinLoanAmount())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::minLoanAmountRetrieved);

            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getLoanAmountDefault())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::defaultLoanAmountRetrieved);

            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getLoanAmountIncrements())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::loanAmountIncrementsRetrieved);
        }
    }

    /**
     * Stores a new list of loan purposes and updates the View.
     * @param loanPurposesList New list.
     */
    private void setLoanPurposeList(LoanPurposeVo[] loanPurposesList) {
        mPurposeAdapter = getPurposeAdapter(loanPurposesList);

        isLoanPurposesReady = true;
        mView.setPurposeAdapter(mPurposeAdapter);

        if (mModel.hasValidLoanPurpose()) {
            mView.setPurpose(mPurposeAdapter.getPosition(mModel.getLoanPurpose()));
        }

        updateViewIfReady();
    }

    private void loanPurposesListRetrieved(LoanPurposesResponseVo purposeList) {
        setLoanPurposeList(purposeList.data);
    }

    private void maxLoanAmountRetrieved(double maxLoanAmount) {
        isMaxLoanAmountReady = true;
        mModel.setMaxAmount((int) maxLoanAmount);
        updateViewIfReady();
    }

    private void minLoanAmountRetrieved(double minLoanAmount) {
        isMinLoanAmountReady = true;
        mModel.setMinAmount((int) minLoanAmount);
        updateViewIfReady();
    }

    private void defaultLoanAmountRetrieved(double defaultLoanAmount) {
        isDefaultLoanAmountReady = true;
        mModel.setAmount((int) defaultLoanAmount);
        updateViewIfReady();
    }

    private void loanAmountIncrementsRetrieved(double amountIncrement) {
        isLoanIncrementsReady = true;
        mAmountIncrement = (int) amountIncrement;
        updateViewIfReady();
    }

    private void partnerDisclaimersListRetrieved(LoanProductListVo response) {
        setDisclaimers(parseDisclaimersResponse(response));
    }

    private void errorReceived(String error) {
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
        }

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error);
        mView.displayErrorMessage(message);
    }

    private boolean isAllDataReadyForView() {
        boolean isViewReady = true;
        if(isLoanAmountRequired) {
            isViewReady = isMinLoanAmountReady && isMaxLoanAmountReady && isDefaultLoanAmountReady
                    && isLoanIncrementsReady;
        }
        if(isLoanPurposeRequired) {
            isViewReady = isViewReady && isLoanPurposesReady;
        }
        return isViewReady;
    }

    private void updateViewIfReady() {
        if(isAllDataReadyForView()) {
            super.populateModelFromStorage();
            if(isLoanAmountRequired) {
                mView.setSeekBarTransformer(new MultiplyTransformer(mAmountIncrement));
                mView.setMinMax((mModel.getMinAmount() / mAmountIncrement)+1, mModel.getMaxAmount() / mAmountIncrement);
                mView.setAmount(mModel.getAmount() / mAmountIncrement);
            }
            mLoadingSpinnerManager.showLoading(false);
        }
    }

    private String parseDisclaimersResponse(LoanProductListVo productDisclaimerList) {
        if (productDisclaimerList == null) {
            return "";
        }

        String lineBreak = "<br />";
        String partnerDivider = "<br /><br />";
        StringBuilder result = new StringBuilder();

        for(LoanProductVo loanProduct : productDisclaimerList.data) {
            if (hasValidDisclaimer(loanProduct)) {
                result.append(loanProduct.preQualificationDisclaimer.value.replaceAll("\\r?\\n", lineBreak));
                result.append(partnerDivider);
            }
        }

        return result.substring(0, result.length() - partnerDivider.length());
    }

    private boolean hasValidDisclaimer(LoanProductVo loanProduct) {
        return !TextUtils.isEmpty(loanProduct.preQualificationDisclaimer.value) &&
                ContentVo.formatValues.valueOf(loanProduct.preQualificationDisclaimer.format).equals(plain_text);
    }

    private void setDisclaimers(String disclaimers) {
        mDisclaimersText = disclaimers;
        mActivity.runOnUiThread(() -> {
            if(!disclaimers.isEmpty()) {
                mView.setDisclaimers(disclaimers);
            }
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int id = item.getItemId();
        if (id == R.id.menu_update_profile) {
            mDelegate.onUpdateUserProfile();
        } else {
            handled = false;
        }

        return handled;
    }
}
