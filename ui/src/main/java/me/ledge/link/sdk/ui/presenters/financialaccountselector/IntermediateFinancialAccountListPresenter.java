package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.models.financialaccountselector.IntermediateFinancialAccountListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.IntermediateFinancialAccountListView;

/**
 * Concrete {@link Presenter} for the intermediate screen during financial accounts loading
 * @author Adrian
 */
public class IntermediateFinancialAccountListPresenter
        extends ActivityPresenter<IntermediateFinancialAccountListModel, IntermediateFinancialAccountListView>
        implements Presenter<IntermediateFinancialAccountListModel, IntermediateFinancialAccountListView>  {


    private IntermediateFinancialAccountListDelegate mDelegate;
    /**
     * Creates a new {@link IntermediateFinancialAccountListPresenter} instance.
     * @param activity Activity.
     */
    public IntermediateFinancialAccountListPresenter(AppCompatActivity activity, IntermediateFinancialAccountListDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        LedgeLinkUi.getCurrentUser();
    }

    @Override
    public IntermediateFinancialAccountListModel createModel() {
        return new IntermediateFinancialAccountListModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IntermediateFinancialAccountListView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
        mView.showLoading(true);
    }

    @Override
    public void onBack() {
        mDelegate.onIntermediateFinancialAccountListBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /**
     * Called when the get current user info response has been received.
     * @param userInfo API response.
     */
    @Subscribe
    public void handleResponse(DataPointList userInfo) {
        if (mView != null) {
            mView.showLoading(false);
        }
        if(userInfo.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount) == null) {
            mDelegate.noFinancialAccountsReceived();
        }
        else {
            mDelegate.financialAccountsReceived(userInfo);
        }
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
        mDelegate.noFinancialAccountsReceived();
    }
}