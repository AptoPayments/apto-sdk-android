package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.models.custodianselector.AddCustodianListModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.custodianselector.AddCustodianListView;

/**
 * Concrete {@link Presenter} for the add custodian screen.
 * @author Adrian
 */
public class AddCustodianListPresenter
        extends ActivityPresenter<AddCustodianListModel, AddCustodianListView>
        implements Presenter<AddCustodianListModel, AddCustodianListView>, AddCustodianListView.ViewListener {

    private AddCustodianListDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link AddCustodianListPresenter} instance.
     * @param activity Activity.
     */
    public AddCustodianListPresenter(AppCompatActivity activity, AddCustodianListDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public AddCustodianListModel createModel() {
        return new AddCustodianListModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddCustodianListView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
        mView.setViewListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.addCustodianListOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    @Override
    public void submitClickHandler() {
        mLoadingSpinnerManager.showLoading(true);
        mDelegate.addCoinbase();
        mActivity.finish();
    }
}
