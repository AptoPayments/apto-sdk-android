package com.shift.link.sdk.ui.presenters.financialaccountselector;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.BankAccount;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectBankAccountModel;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectCardModel;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import com.shift.link.sdk.ui.presenters.ActivityPresenter;
import com.shift.link.sdk.ui.presenters.Presenter;
import com.shift.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;

import java.util.List;

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
                    data[i] = new SelectBankAccountModel((BankAccount) financialAccount);
                    break;
                case Card:
                    data[i] = new SelectCardModel((Card) financialAccount);
                    break;
            }
        }

        return data;
    }

    @Override
    public void accountClickHandler(SelectFinancialAccountModel model) {
        mDelegate.accountSelected(model);
    }

    @Override
    public void addAccountClickHandler() {
        mDelegate.addAccountPressed();
    }
}
