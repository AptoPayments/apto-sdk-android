package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.sdk.ui.models.financialaccountselector.EnableAutoPayModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.EnableAutoPayView;

/**
 * Concrete {@link Presenter} for the enable auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayPresenter
        extends ActivityPresenter<EnableAutoPayModel, EnableAutoPayView>
        implements Presenter<EnableAutoPayModel, EnableAutoPayView>, EnableAutoPayView.ViewListener {

    private EnableAutoPayDelegate mDelegate;

    /**
     * Creates a new {@link EnableAutoPayPresenter} instance.
     * @param activity Activity.
     */
    public EnableAutoPayPresenter(AppCompatActivity activity, EnableAutoPayDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        mModel.setFinancialAccount(mDelegate.getFinancialAccount());
    }

    /** {@inheritDoc} */
    @Override
    public EnableAutoPayModel createModel() {
        return new EnableAutoPayModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(EnableAutoPayView view) {
        super.attachView(view);
        mView.setListener(this);
        mView.displayFinancialAccountInfo(mModel.getFinancialAccountInfo(mActivity.getResources()));
    }

    @Override
    public void onBack() {
        mDelegate.autoPayOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    @Override
    public void enableAutoPayClickHandler() {
        // TODO
        mDelegate.autoPayEnabled();
    }
}
