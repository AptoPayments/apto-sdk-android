package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.ledge.link.api.vos.Card;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.FinancialAccountVo;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectCardModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;

/**
 * Concrete {@link Presenter} for the select financial account screen.
 * @author Adrian
 */
public class SelectFinancialAccountListPresenter
        extends ActivityPresenter<SelectFinancialAccountListModel, SelectFinancialAccountListView>
        implements Presenter<SelectFinancialAccountListModel, SelectFinancialAccountListView>, SelectFinancialAccountListView.ViewListener {

    private SelectFinancialAccountListDelegate mDelegate;

    /**
     * Creates a new {@link SelectFinancialAccountListPresenter} instance.
     * @param activity Activity.
     */
    public SelectFinancialAccountListPresenter(AppCompatActivity activity, SelectFinancialAccountListDelegate delegate) {
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
    public SelectFinancialAccountListModel createModel() {
        return new SelectFinancialAccountListModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void setupToolbar() {
        initToolbar();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(SelectFinancialAccountListView view) {
        super.attachView(view);

        SelectFinancialAccountModel[] viewData = createViewData(mModel.getFinancialAccounts());
        if(viewData != null) {
            mView.setData(viewData);
        }
        mView.setViewListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.selectFinancialAccountListOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        super.detachView();
    }

    private SelectFinancialAccountModel[] createViewData(List<DataPointVo> financialAccounts) {
        if (financialAccounts == null || financialAccounts.isEmpty()) {
            return null;
        }

        SelectFinancialAccountModel[] data = new SelectFinancialAccountModel[financialAccounts.size()];
        FinancialAccountVo.FinancialAccountType type;

        for (int i = 0; i< financialAccounts.size(); i++) {
            FinancialAccountVo financialAccount = (FinancialAccountVo) financialAccounts.get(i);
            type = financialAccount.mAccountType;
            switch (type) {
                case Bank:
                    //data[i] = new SelectBankAccountModel();
                    break;
                case Card:
                    data[i] = new SelectCardModel((Card) financialAccount);
                    break;
                default:
                    break;
            }
        }

        return data;
    }

    @Override
    public void accountClickHandler(SelectFinancialAccountModel model) {
/*        if(model instanceof SelectBankAccountModel) {
            mDelegate.addBankAccount();
        }
        else if (model instanceof  SelectVirtualCardModel) {
            // issue virtual card call & return
        }
        else if (model instanceof SelectCardModel) {
            mDelegate.addCard();
        }*/

    }

    @Override
    public void addAccountClickHandler() {
        mDelegate.addAccountPressed();
    }
}
