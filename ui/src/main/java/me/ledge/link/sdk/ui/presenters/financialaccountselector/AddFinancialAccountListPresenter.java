package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

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
                default:
                    break;
            }
        }

        return data;
    }

    @Override
    public void accountClickHandler(AddFinancialAccountModel model) {
        if(model instanceof AddBankAccountModel) {
            // Plaid screen
        }
        else if (model instanceof  AddVirtualCardModel) {
            // issue virtual card call & return
        }
        else if (model instanceof AddCardModel) {
            // add card input screen
            mDelegate.addCard();
        }

    }
}
