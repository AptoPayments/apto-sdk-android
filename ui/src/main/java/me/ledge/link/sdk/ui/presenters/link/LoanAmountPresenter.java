package me.ledge.link.sdk.ui.presenters.link;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.link.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
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

    /**
     * Creates a new {@link LoanAmountPresenter} instance.
     * @param activity Activity.
     */
    public LoanAmountPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
        mPurposeAdapter = null;
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
        return new StepperConfiguration(TOTAL_STEPS, 1, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public LoanAmountModel createModel() {
        return new LoanAmountModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        mAmountIncrement = mActivity.getResources().getInteger(R.integer.loan_amount_increment);

        mModel.setMinAmount(mActivity.getResources().getInteger(R.integer.min_loan_amount))
                .setMaxAmount(mActivity.getResources().getInteger(R.integer.max_loan_amount))
                .setAmount(mActivity.getResources().getInteger(R.integer.default_loan_amount));

        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);

        mView.setListener(this);
        mView.setSeekBarTransformer(new MultiplyTransformer(mAmountIncrement));
        mView.setMinMax(mModel.getMinAmount() / mAmountIncrement, mModel.getMaxAmount() / mAmountIncrement);
        mView.setAmount(mModel.getAmount() / mAmountIncrement);
        mView.showLoading(true);

        if (mPurposeAdapter == null) {
            mView.setPurposeAdapter(getPurposeAdapter(null));

            // Load loan purpose list.
            LedgeLinkUi.getLoanPurposesList();
        } else {
            mView.setPurposeAdapter(mPurposeAdapter);

            if (mModel.hasValidLoanPurpose()) {
                mView.setPurpose(mPurposeAdapter.getPosition(mModel.getLoanPurpose()));
            }
        }
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
        mModel.setAmount(mView.getAmount() * mAmountIncrement);
        mModel.setLoanPurpose(mView.getPurpose());

        mView.updatePurposeError(!mModel.hasValidLoanPurpose());
        super.nextClickHandler();
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
     * Called when the list of loan purposes has been received from the API.
     * @param response API response.
     */
    @Subscribe
    public void handlePurposeList(LoanPurposesResponseVo response) {
        setLoanPurposeList(response.data);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if (mView != null) {
            mView.showLoading(false);
        }

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Stores a new list of loan purposes and updates the View.
     * @param loanPurposesList New list.
     */
    private void setLoanPurposeList(LoanPurposeVo[] loanPurposesList) {
        mPurposeAdapter = getPurposeAdapter(loanPurposesList);

        mView.showLoading(false);
        mView.setPurposeAdapter(mPurposeAdapter);

        if (mModel.hasValidLoanPurpose()) {
            mView.setPurpose(mPurposeAdapter.getPosition(mModel.getLoanPurpose()));
        }
    }
}
