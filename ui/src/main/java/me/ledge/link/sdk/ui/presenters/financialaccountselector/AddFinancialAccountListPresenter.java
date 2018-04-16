package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.ledge.link.sdk.api.vos.Card;
import me.ledge.link.sdk.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.ui.ShiftUi;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddCardModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddFinancialAccountListModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddFinancialAccountModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddVirtualCardModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.financialaccountselector.AddFinancialAccountListView;
import me.ledge.link.sdk.ui.workflow.ModuleManager;

/**
 * Concrete {@link Presenter} for the add financial account screen.
 * @author Adrian
 */
public class AddFinancialAccountListPresenter
        extends ActivityPresenter<AddFinancialAccountListModel, AddFinancialAccountListView>
        implements Presenter<AddFinancialAccountListModel, AddFinancialAccountListView>, AddFinancialAccountListView.ViewListener {

    private AddFinancialAccountListDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link AddFinancialAccountListPresenter} instance.
     * @param activity Activity.
     */
    public AddFinancialAccountListPresenter(AppCompatActivity activity, AddFinancialAccountListDelegate delegate) {
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
    public AddFinancialAccountListModel createModel() {
        FinancialAccountSelectorModule accountSelectorModule = (FinancialAccountSelectorModule) ModuleManager.getInstance().getCurrentModule();
        return new AddFinancialAccountListModel(accountSelectorModule.getConfiguration());
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddFinancialAccountListView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
        mResponseHandler.subscribe(this);
        AddFinancialAccountModel[] viewData = createViewData(mModel.getFinancialAccountTypes());
        mView.setData(viewData);
        mView.setViewListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.addFinancialAccountListOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    private AddFinancialAccountModel[] createViewData(ArrayList<FinancialAccountVo.FinancialAccountType> accountTypes) {
        if (accountTypes == null || accountTypes.size() <= 0) {
            return null;
        }

        AddFinancialAccountModel[] data = new AddFinancialAccountModel[accountTypes.size()];
        int i = 0;
        for (FinancialAccountVo.FinancialAccountType type: accountTypes) {
            switch (type) {
                case Bank:
                    data[i] = new AddBankAccountModel();
                    i++;
                    break;
                case Card:
                    data[i] = new AddCardModel();
                    i++;
                    break;
                case VirtualCard:
                    data[i] = new AddVirtualCardModel();
                    i++;
                    break;
            }
        }

        return data;
    }

    @Override
    public void accountClickHandler(AddFinancialAccountModel model) {
        if(model instanceof AddBankAccountModel) {
            mDelegate.addBankAccount();
        }
        else if (model instanceof  AddVirtualCardModel) {
            mLoadingSpinnerManager.showLoading(true);
            AddVirtualCardModel mModel = (AddVirtualCardModel) model;
            ShiftUi.issueVirtualCard(mModel.getRequest());
        }
        else if (model instanceof AddCardModel) {
            mDelegate.addCard();
            mResponseHandler.unsubscribe(this);
        }
    }

    /**
     * Called when the issue virtual card response has been received.
     * @param virtualCard API response.
     */
    @Subscribe
    public void handleResponse(Card virtualCard) {
        mResponseHandler.unsubscribe(this);
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
        }
        mDelegate.virtualCardIssued(virtualCard);
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
