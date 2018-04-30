package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.TransactionDetailsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;

/**
 * Concrete {@link Presenter} for the transaction details screen.
 * @author Adrian
 */
public class TransactionDetailsPresenter
        extends BasePresenter<TransactionDetailsModel, TransactionDetailsView>
        implements Presenter<TransactionDetailsModel, TransactionDetailsView> {

    private TransactionDetailsActivity mActivity;

    public TransactionDetailsPresenter(TransactionDetailsActivity activity, TransactionVo transaction) {
        mActivity = activity;
        mModel.setTransaction(transaction);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TransactionDetailsView view) {
        super.attachView(view);
        mActivity.setSupportActionBar(mView.getToolbar());
        Integer icon = UIStorage.getInstance().getIcon(mModel.getTransaction().merchantCategoryIcon);
        mView.setTransactionIcon(mActivity.getDrawable(icon));
        mView.setTransactionAmount(mModel.getLocalAmount());
        mView.setTransactionDescription(mModel.getDescription());
        mView.setDetailAmount(mModel.getUsdAmount());
        mView.setCurrency(mModel.getCurrency());
        // TODO: remove hardcoded type
        mView.setType("Purchase");
        mView.setLocation(mModel.getLocation());
        mView.setCategory(mModel.getCategory());
        mView.setTransactionDate(mModel.getTransactionDate());
        mView.setSettlementDate(mModel.getSettlementDate());
        mView.setTransactionId(mModel.getTransactionId());
    }

    @Override
    public TransactionDetailsModel createModel() {
        return new TransactionDetailsModel();
    }
}
