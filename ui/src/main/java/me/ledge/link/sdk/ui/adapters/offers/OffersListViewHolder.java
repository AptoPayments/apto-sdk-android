package me.ledge.link.sdk.ui.adapters.offers;

import me.ledge.common.adapters.recyclerview.GenericViewHolder;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.views.offers.OfferListSummaryView;

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
