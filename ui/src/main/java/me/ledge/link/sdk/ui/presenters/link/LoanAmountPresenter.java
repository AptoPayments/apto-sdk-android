package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.api.vos.responses.config.LoanProductVo;
import me.ledge.link.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.link.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.MultiplyTransformer;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

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
    private boolean isLoanPurposesReady;
    private boolean isLoanIncrementsReady;
    private String mDisclaimersText;

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
        isLoanPurposesReady = false;
        isLoanIncrementsReady = false;
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
        boolean enableNextButton = UserStorage.getInstance().getBearerToken() == null;
        return new StepperConfiguration(TOTAL_STEPS, 0, true, enableNextButton);
    }

    /** {@inheritDoc} */
    @Override
    public LoanAmountModel createModel() {
        return new LoanAmountModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getMaxLoanAmount())
                .exceptionally(ex -> {
                    errorReceived(ex.getMessage());
                    return null;
                })
                .thenAccept(this::maxLoanAmountRetrieved);

        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getLoanAmountIncrements())
                .exceptionally(ex -> {
                    errorReceived(ex.getMessage());
                    return null;
                })
                .thenAccept(this::loanAmountIncrementsRetrieved);

        mModel.setMinAmount(mActivity.getResources().getInteger(R.integer.min_loan_amount))
                .setAmount(mActivity.getResources().getInteger(R.integer.default_loan_amount));

        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.showLoading(true);

        if(UserStorage.getInstance().getBearerToken() != null) {
            mView.showGetOffersButtonAndDisclaimers(true);
            if (mDisclaimersText == null) {
                mView.showLoading(true);
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
        mModel.setAmount(mView.getAmount() * mAmountIncrement);
        mModel.setLoanPurpose(mView.getPurpose());

        mView.updatePurposeError(!mModel.hasValidLoanPurpose());
        if (mModel.hasAllData()) {
            saveData();
            mDelegate.loanDataPresented();
        }
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
        mModel.setMaxAmount((int) maxLoanAmount)
                .setMinAmount(Math.min(mModel.getMinAmount(),(int) maxLoanAmount))
                .setAmount(Math.min(mModel.getAmount(),(int) maxLoanAmount));
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
            mView.showLoading(false);
        }

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error);
        mView.displayErrorMessage(message);
    }

    private boolean isAllDataReadyForView() {
        return isMaxLoanAmountReady && isLoanPurposesReady && isLoanIncrementsReady;
    }

    private void updateViewIfReady() {
        if(isAllDataReadyForView()) {
            mView.setSeekBarTransformer(new MultiplyTransformer(mAmountIncrement));
            mView.setMinMax(mModel.getMinAmount() / mAmountIncrement, mModel.getMaxAmount() / mAmountIncrement);
            mView.setAmount(mModel.getAmount() / mAmountIncrement);
            mView.showLoading(false);
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
            if (!TextUtils.isEmpty(loanProduct.preQualificationDisclaimer)) {
                result.append(loanProduct.preQualificationDisclaimer.replaceAll("\\r?\\n", lineBreak));
            }
            result.append(partnerDivider);
        }

        return result.substring(0, result.length() - partnerDivider.length());
    }

    private void setDisclaimers(String disclaimers) {
        mDisclaimersText = disclaimers;
        mActivity.runOnUiThread(() -> {
            mView.setDisclaimers(disclaimers);
            mView.showLoading(false);
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
