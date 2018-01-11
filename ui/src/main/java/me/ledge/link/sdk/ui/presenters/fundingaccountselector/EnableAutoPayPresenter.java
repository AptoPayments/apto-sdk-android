package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.models.fundingaccountselector.EnableAutoPayModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.fundingaccountselector.EnableAutoPayView;

/**
 * Concrete {@link Presenter} for the enable auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayPresenter
        extends ActivityPresenter<EnableAutoPayModel, EnableAutoPayView>
        implements Presenter<EnableAutoPayModel, EnableAutoPayView>, EnableAutoPayView.ViewListener {

    private EnableAutoPayDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link EnableAutoPayPresenter} instance.
     * @param activity Activity.
     */
    public EnableAutoPayPresenter(AppCompatActivity activity, EnableAutoPayDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getFinancialAccount(mDelegate.getFinancialAccountId());
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
        mView.setImage(UIStorage.getInstance().getContextConfig().logoURL);

        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
    }

    @Override
    public void onBack() {
        mDelegate.autoPayOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    @Override
    public void primaryButtonClickHandler() {
        mDelegate.primaryButtonPressed();
    }

    @Override
    public void secondaryButtonClickHandler() {
        mDelegate.secondaryButtonPressed();
    }

    /**
     * Called when the get financial account response has been received.
     * @param account API response.
     */
    @Subscribe
    public void handleResponse(FinancialAccountVo account) {
        mModel.setFinancialAccount(account);
        mActivity.runOnUiThread(() -> {
            AutoPayViewModel enableAutoPayViewModel = mModel.getEnableAutoPayViewModel(mActivity.getResources());
            if(enableAutoPayViewModel.showDescription) {
                mView.displayFinancialAccountInfo(enableAutoPayViewModel.description);
            }
            if(enableAutoPayViewModel.showPrimaryButton) {
                mView.setPrimaryButtonText(enableAutoPayViewModel.primaryButtonText);
            }
            if(enableAutoPayViewModel.showSecondaryButton) {
                mView.setSecondaryButtonText(enableAutoPayViewModel.secondaryButtonText);
            }
            mLoadingSpinnerManager.showLoading(false);
        });
    }
}
