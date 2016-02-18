package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
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
}
