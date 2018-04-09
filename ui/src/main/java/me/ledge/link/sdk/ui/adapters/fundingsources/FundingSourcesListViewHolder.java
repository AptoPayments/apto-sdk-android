package me.ledge.link.sdk.ui.adapters.fundingsources;

import me.ledge.common.adapters.recyclerview.GenericViewHolder;
import me.ledge.link.sdk.ui.models.card.FundingSourceModel;
import me.ledge.link.sdk.ui.views.card.FundingSourceView;

/**
 * Concrete {@link RecyclerView.ViewHolder} for the funding source list.
 * @author Adrian
 */
public class FundingSourcesListViewHolder extends GenericViewHolder<FundingSourceModel, FundingSourceView> {

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
