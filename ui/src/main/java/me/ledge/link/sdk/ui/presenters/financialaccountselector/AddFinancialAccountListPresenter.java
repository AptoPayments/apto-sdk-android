package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddCardModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddFinancialAccountListModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddFinancialAccountModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddVirtualCardModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.AddFinancialAccountListView;

/**
 * Concrete {@link Presenter} for the add financial account screen.
 * @author Adrian
 */
public class AddFinancialAccountListPresenter
        extends ActivityPresenter<AddFinancialAccountListModel, AddFinancialAccountListView>
        implements Presenter<AddFinancialAccountListModel, AddFinancialAccountListView>, AddFinancialAccountListView.ViewListener {

    private AddFinancialAccountListDelegate mDelegate;

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
        return new AddFinancialAccountListModel();
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
        mView.showLoading(false);
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

    private AddFinancialAccountModel[] createViewData(String[] accountTypes) {
        if (accountTypes == null || accountTypes.length <= 0) {
            return null;
        }

        AddFinancialAccountModel[] data = new AddFinancialAccountModel[accountTypes.length];
        String type;

        for (int i = 0; i < accountTypes.length; i++) {
            type = accountTypes[i];

            switch (type) {
                case "Bank":
                    data[i] = new AddBankAccountModel();
                    break;
                case "Card":
                    data[i] = new AddCardModel();
                    break;
                case "VirtualCard":
                    data[i] = new AddVirtualCardModel();
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
            mView.showLoading(true);
            AddVirtualCardModel mModel = (AddVirtualCardModel) model;
            LedgeLinkUi.issueVirtualCard(mModel.getRequest());
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
        if (mView != null) {
            mView.showLoading(false);
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
            mView.showLoading(false);
            mView.displayErrorMessage("API Error: " + error);
        }
    }
}
