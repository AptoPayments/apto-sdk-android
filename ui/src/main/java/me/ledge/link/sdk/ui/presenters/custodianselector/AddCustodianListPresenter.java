package me.ledge.link.sdk.ui.presenters.custodianselector;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.ui.models.custodianselector.AddCoinbaseModel;
import me.ledge.link.sdk.ui.models.custodianselector.AddCustodianListModel;
import me.ledge.link.sdk.ui.models.custodianselector.AddCustodianModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.custodianselector.AddCustodianListView;
import me.ledge.link.sdk.ui.workflow.ModuleManager;

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
    protected void init() {
        super.init();
    }

    /** {@inheritDoc} */
    @Override
    public AddCustodianListModel createModel() {
        CustodianSelectorModule accountSelectorModule = (CustodianSelectorModule) ModuleManager.getInstance().getCurrentModule();
        return new AddCustodianListModel(accountSelectorModule.getConfiguration());
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
        mResponseHandler.subscribe(this);
        AddCustodianModel[] viewData = createViewData(mModel.getCustodianTypes());
        mView.setData(viewData);
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

    private AddCustodianModel[] createViewData(ArrayList<AddCustodianListModel.CustodianType> custodianTypes) {
        if (custodianTypes == null || custodianTypes.size() <= 0) {
            return null;
        }

        AddCustodianModel[] data = new AddCustodianModel[custodianTypes.size()];
        int i = 0;
        for (AddCustodianListModel.CustodianType type: custodianTypes) {
            switch (type) {
                case Coinbase:
                    data[i] = new AddCoinbaseModel();
                    i++;
                    break;
                case Dwolla:
                    //TODO
                    i++;
                    break;
            }
        }

        return data;
    }

    @Override
    public void accountClickHandler(AddCustodianModel model) {
        if(model instanceof AddCoinbaseModel) {
            mDelegate.addCoinbase();
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
            mView.displayErrorMessage("API Error: " + error);
        }
    }
}
