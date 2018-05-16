package com.shiftpayments.link.sdk.ui.presenters.card;

import android.support.v7.widget.LinearLayoutManager;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.TransactionDetailsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.card.AdjustmentsAdapter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;

import java.util.Arrays;

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
        Integer icon = UIStorage.getInstance().getIcon(mModel.getTransaction().merchant.mcc.merchantCategoryIcon);
        mView.setTransactionIcon(mActivity.getDrawable(icon));
        switch (mModel.getTransactionType()) {
            case DECLINE:
                mView.setTransactionAmount("Declined");
                mView.setAmountLabel(mActivity.getResources().getString(R.string.transaction_details_declined_amount));
                mView.setDeclineReason(mModel.getDeclinedReason());
                break;
            case PENDING:
                mView.setAmountLabel(mActivity.getResources().getString(R.string.transaction_details_amount));
                mView.setTransactionAmount("Pending");
                break;
            default:
                mView.setAmountLabel(mActivity.getResources().getString(R.string.transaction_details_amount));
                mView.setTransactionAmount(mModel.getNativeBalance());
        }
        mView.setDetailAmount(mModel.getLocalAmount());
        mView.setCurrency(mModel.getCurrency());
        mView.setType(mModel.getTransactionType().toString());
        mView.setLocation(mModel.getLocation());
        mView.setCategory(mModel.getCategory());
        mView.setTransactionDate(mModel.getTransactionDate());
        mView.setSettlementDate(mModel.getSettlementDate());
        if(mModel.hasHoldAmount()) {
            mView.setHoldAmount(mModel.getHoldAmount());
        }
        if(mModel.hasFeeAmount()) {
            mView.setFeeAmount(mModel.getFeeAmount());
        }
        if(mModel.hasCashbackAmount()) {
            mView.setCashbackAmount(mModel.getCashbackAmount());
        }
        mView.setTransactionDescription(mModel.getMerchantName());
        mView.setTransactionId(mModel.getTransactionId());
        mView.setShiftId(mModel.getShiftId());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        AdjustmentsAdapter adapter = new AdjustmentsAdapter(Arrays.asList(mModel.getTransferList()), mActivity);
        mView.configureAdjustmentsAdapter(linearLayoutManager, adapter);
    }

    @Override
    public TransactionDetailsModel createModel() {
        return new TransactionDetailsModel();
    }
}
