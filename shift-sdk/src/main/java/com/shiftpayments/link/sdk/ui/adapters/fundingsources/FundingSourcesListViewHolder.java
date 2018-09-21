package com.shiftpayments.link.sdk.ui.adapters.fundingsources;

import com.shiftpayments.link.sdk.ui.models.card.BalanceModel;
import com.shiftpayments.link.sdk.ui.views.card.FundingSourceView;

import me.ledge.common.adapters.recyclerview.GenericViewHolder;

/**
 * Concrete {@link RecyclerView.ViewHolder} for the funding source list.
 * @author Adrian
 */
public class FundingSourcesListViewHolder extends GenericViewHolder<BalanceModel, FundingSourceView> {

    /**
     * @param itemView See {@link GenericViewHolder#GenericViewHolder}.
     * @see GenericViewHolder#GenericViewHolder
     */
    public FundingSourcesListViewHolder(FundingSourceView itemView) {
        super(itemView);
    }

    public void setListener(FundingSourceView.ViewListener listener) {
        if (mView != null) {
            mView.setListener(listener);
        }
    }
}
