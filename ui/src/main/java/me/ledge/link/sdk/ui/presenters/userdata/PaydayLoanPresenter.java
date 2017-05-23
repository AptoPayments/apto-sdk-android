package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PaydayLoanModel;
import me.ledge.link.sdk.ui.views.userdata.PaydayLoanView;

/**
 * Concrete {@link Presenter} for the payday loan screen.
 * @author Adrian
 */
public class PaydayLoanPresenter
        extends UserDataPresenter<PaydayLoanModel, PaydayLoanView>
        implements PaydayLoanView.ViewListener {

    private PaydayLoanDelegate mDelegate;

    /**
     * Creates a new {@link PaydayLoanPresenter} instance.
     * @param activity Activity.
     */
    public PaydayLoanPresenter(AppCompatActivity activity, PaydayLoanDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
    }

    /** {@inheritDoc} */
    @Override
    public PaydayLoanModel createModel() {
        return new PaydayLoanModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PaydayLoanView view) {
        super.attachView(view);

        mView.setListener(this);
        mView.setSelection(getPaydayLoanId());
    }

    @Override
    public void onBack() {
        mDelegate.paydayLoanOnBackPressed();
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
        mModel.setPaydayLoan(getPaydayLoan());
        mView.showError(!mModel.hasAllData());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.paydayLoanStored();
        }
    }

    /**
     * @return Whether the user has used a payday loan based on the checked Radio Button.
     */
    private Boolean getPaydayLoan() {
        if(mView.getSelectionId() == -1) {
            return null;
        }
        else {
            return mView.getSelectionId() == R.id.rb_yes;
        }
    }

    /**
     * @return Radio Button ID based on the boolean value.
     */
    private int getPaydayLoanId() {
        Boolean hasUsedPaydayLoan = mModel.hasUsedPaydayLoan();
        if (hasUsedPaydayLoan == null) {
            return -1;
        }

        return (mModel.hasUsedPaydayLoan()) ? R.id.rb_yes : R.id.rb_no;
    }
}
