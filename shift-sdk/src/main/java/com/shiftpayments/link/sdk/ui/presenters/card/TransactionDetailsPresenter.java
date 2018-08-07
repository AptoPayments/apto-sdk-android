package com.shiftpayments.link.sdk.ui.presenters.card;

import android.support.v7.widget.LinearLayoutManager;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.AdjustmentVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.activities.card.TransactionDetailsActivity;
import com.shiftpayments.link.sdk.ui.models.card.TransactionDetailsModel;
import com.shiftpayments.link.sdk.ui.presenters.BasePresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.card.AdjustmentsAdapter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;

import java.util.List;

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
        /*switch (mModel.getTransactionType()) {
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
                mView.setTransactionAmount(mModel.getLocalAmount());
        }*/

        mView.setTitle(mModel.getMerchantName() + " " + mModel.getLocalAmount());
        mView.setTransactionDate(mModel.getTransactionDate());
        mView.setShiftId(mModel.getShiftId());
        mView.setType(mModel.getTransactionType().toString());
        /*mView.setDetailAmount(mModel.getLocalAmount());*/
        mView.setTransactionAddress(mModel.getLocation());
        mView.setCategory(mModel.getCategory());
        if(mModel.hasSettlementDate()) {
            mView.setSettlementDate(mModel.getSettlementDate());
        }
        if(mModel.hasHoldAmount()) {
            mView.setHoldAmount(mModel.getHoldAmount());
        }
        /*if(mModel.hasFeeAmount()) {
            mView.setFeeAmount(mModel.getFeeAmount());
        }
        if(mModel.hasCashbackAmount()) {
            mView.setCashbackAmount(mModel.getCashbackAmount());
        }*/
        if(mModel.hasTransactionId()) {
            mView.setTransactionId(mModel.getTransactionId());
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        List<AdjustmentVo> transferList = mModel.getTransferList();
        mView.showTransfersHeader(!transferList.isEmpty());
        AdjustmentsAdapter adapter = new AdjustmentsAdapter(transferList, mActivity);
        mView.configureAdjustmentsAdapter(linearLayoutManager, adapter);
    }

    @Override
    public TransactionDetailsModel createModel() {
        return new TransactionDetailsModel();
    }
}
