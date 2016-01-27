package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.userdata.LoanAmountModel;
import us.ledge.line.sdk.sdk.presenters.ActivityPresenter;
import us.ledge.line.sdk.sdk.presenters.Presenter;
import us.ledge.line.sdk.sdk.views.userdata.LoanAmountView;
import us.ledge.line.sdk.sdk.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountPresenter
        extends ActivityPresenter<LoanAmountModel, LoanAmountView>
        implements NextButtonListener {

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
    public void attachView(LoanAmountView view) {
        super.attachView(view);
        mView.setListener(this);
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
        mModel.setAmount(mView.getAmount());
        mView.updateAmountError(!mModel.hasValidAmount(), R.string.loan_amount_input_error);
    }
}
