package com.shiftpayments.link.sdk.ui.adapters.offers;

import com.shiftpayments.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shiftpayments.link.sdk.ui.views.offers.OfferListSummaryView;

import me.ledge.common.adapters.recyclerview.GenericViewHolder;

/**
 * Concrete {@link RecyclerView.ViewHolder} for the loan offers list.
 * @author Wijnand
 */
public class OffersListViewHolder extends GenericViewHolder<OfferSummaryModel, OfferListSummaryView> {

    /**
     * @param itemView See {@link GenericViewHolder#GenericViewHolder}.
     * @see GenericViewHolder#GenericViewHolder
     */
    public OffersListViewHolder(OfferListSummaryView itemView) {
        super(itemView);
    }

    public void setListener(OfferListSummaryView.ViewListener listener) {
        if (mView != null) {
            mView.setListener(listener);
        }
    }
}
