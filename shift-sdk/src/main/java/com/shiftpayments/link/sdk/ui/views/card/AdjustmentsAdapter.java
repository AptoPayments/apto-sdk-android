package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.AdjustmentVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.util.List;

public class AdjustmentsAdapter extends
        RecyclerView.Adapter<AdjustmentsAdapter.ViewHolder> {

    private List<AdjustmentVo> mAdjustments;
    private Context mContext;

    public AdjustmentsAdapter(List<AdjustmentVo> adjustments, Context context) {
        mAdjustments = adjustments;
        mContext = context;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdjustmentView adjustmentView;

        public ViewHolder(View itemView, int viewType) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            adjustmentView = itemView.findViewById(R.id.cv_adjustment_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View transactionView = inflater.inflate(R.layout.cv_adjustment, parent, false);
        return new ViewHolder(transactionView, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AdjustmentVo adjustment = mAdjustments.get(position);
        AdjustmentView adjustmentView = viewHolder.adjustmentView;
        if(adjustment.fundingSourceName != null && !adjustment.fundingSourceName.isEmpty()) {
            if(adjustment.type.equals(AdjustmentVo.AdjustmentType.CAPTURE)) {
                adjustmentView.setDescription(mContext.getResources().getString(R.string.transaction_details_adjustment_from, adjustment.fundingSourceName));
                adjustmentView.setAmountColor(mContext.getResources().getColor(R.color.positive_adjustment));
            }
            else if(adjustment.type.equals(AdjustmentVo.AdjustmentType.REFUND)) {
                adjustmentView.setDescription(mContext.getResources().getString(R.string.transaction_details_adjustment_to, adjustment.fundingSourceName));
                adjustmentView.setAmountColor(mContext.getResources().getColor(R.color.negative_adjustment));
            }
            else {
                adjustmentView.setDescription(adjustment.fundingSourceName);
            }
        }
        if(adjustment.externalId != null) {
            adjustmentView.setId("ID: " + adjustment.externalId);
        }
        if(adjustment.nativeAmount != null && adjustment.exchangeRate != null) {
            AmountVo nativeAmount = new AmountVo(1, adjustment.nativeAmount.currency);
            AmountVo exchangeRateAmount = new AmountVo(Double.parseDouble(adjustment.exchangeRate), adjustment.localAmount.currency);
            String exchangeRate = String.format("%s = %s", nativeAmount.toString(), exchangeRateAmount.toString());
            adjustmentView.setExchangeRate(exchangeRate);
        }
        if(adjustment.localAmount != null) {
            AmountVo amount = new AmountVo(adjustment.localAmount.amount, adjustment.localAmount.currency);
            adjustmentView.setAmount(amount.toString());
        }
    }

    @Override
    public int getItemCount() {
        if(mAdjustments == null) {
            return 0;
        }
        return mAdjustments.size();
    }
}