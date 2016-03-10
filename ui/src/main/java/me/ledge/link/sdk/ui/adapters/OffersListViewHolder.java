package me.ledge.link.sdk.ui.adapters;

import me.ledge.common.adapters.recyclerview.GenericViewHolder;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.views.offers.OfferSummaryView;

/**
 * Concrete {@link RecyclerView.ViewHolder} for the loan offers list.
 * @author Wijnand
 */
public class OffersListViewHolder extends GenericViewHolder<OfferSummaryModel, OfferSummaryView> {

    /**
     * @param itemView See {@link GenericViewHolder#GenericViewHolder}.
     * @see GenericViewHolder#GenericViewHolder
     */
    public OffersListViewHolder(OfferSummaryView itemView) {
        super(itemView);
    }

    public void setListener(OfferSummaryView.ViewListener listener) {
        if (mView != null) {
            mView.setListener(listener);
        }
    }
}
