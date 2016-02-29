package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.vos.LoanPurposeDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.MultiplyTransformer;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;

/**
 * Concrete {@link Presenter} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountPresenter
        extends UserDataPresenter<LoanAmountModel, LoanAmountView>
        implements LoanAmountView.ViewListener {

    private int mAmountIncrement;

    /**
     * Creates a new {@link LoanAmountPresenter} instance.
     * @param activity Activity.
     */
    public LoanAmountPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /**
     * @param loanPurposesList List of loan purposes.
     * @return A new {@link HintArrayAdapter} to use for the loan purpose drop-down.
     */
    private HintArrayAdapter<LoanPurposeDisplayVo> getPurposeAdapter(LoanPurposeVo[] loanPurposesList) {
        HintArrayAdapter<LoanPurposeDisplayVo> adapter
                = new HintArrayAdapter<LoanPurposeDisplayVo>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        LoanPurposeDisplayVo hint = new LoanPurposeDisplayVo();
        hint.description = mActivity.getString(R.string.loan_amount_purpose_hint);
        hint.loan_purpose_id = -1;

        adapter.add(hint);

        if (loanPurposesList != null) {
            for (LoanPurposeVo purpose : loanPurposesList) {
                adapter.add(new LoanPurposeDisplayVo(purpose));
            }
        }

        return adapter;
    }

    /** {@inheritDoc} */
    @Override
    public LoanAmountModel createModel() {
        return new LoanAmountModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromParcel() {
        mAmountIncrement = mActivity.getResources().getInteger(R.integer.loan_amount_increment);

        mModel.setMinAmount(mActivity.getResources().getInteger(R.integer.min_loan_amount))
                .setMaxAmount(mActivity.getResources().getInteger(R.integer.max_loan_amount))
                .setAmount(mActivity.getResources().getInteger(R.integer.default_loan_amount));

        super.populateModelFromParcel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanAmountView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setSeekBarTransformer(new MultiplyTransformer(mAmountIncrement));
        mView.setMinMax(mModel.getMinAmount() / mAmountIncrement, mModel.getMaxAmount() / mAmountIncrement);
        mView.setAmount(mModel.getAmount() / mAmountIncrement);
        mView.setPurposeAdapter(getPurposeAdapter(null));
        mView.showLoading(true);

        // Load loan purpose list.
        LedgeLinkUi.getLoanPurposesList();
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
     * Stores a new list of loan purposes and updates the View.
     * @param loanPurposesList New list.
     */
    public void setLoanPurposeList(LoanPurposeVo[] loanPurposesList) {
        mView.showLoading(false);
        mView.setPurposeAdapter(getPurposeAdapter(loanPurposesList));
    }

    /**
     * Deals with an API error.
     * @param error API error.
     */
    public void setApiError(ApiErrorVo error) {
        mView.showLoading(false);

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}
