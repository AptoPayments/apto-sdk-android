package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.TransactionDetailsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;

/**
 * Concrete {@link Presenter} for the transaction details screen.
 * @author Adrian
 */
public class TransactionDetailsPresenter
        extends BasePresenter<TransactionDetailsModel, TransactionDetailsView>
        implements Presenter<TransactionDetailsModel, TransactionDetailsView> {

    private TransactionDetailsActivity mActivity;

    public TransactionDetailsPresenter(TransactionDetailsActivity activity, TransactionVo transactionVo) {
        mActivity = activity;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(TransactionDetailsView view) {
        super.attachView(view);
        mActivity.setSupportActionBar(mView.getToolbar());

    }

    @Override
    public TransactionDetailsModel createModel() {
        return new TransactionDetailsModel();
    }
}
