package me.ledge.link.sdk.ui.adapters.offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.views.offers.OfferSummaryView;

/**
 * A concrete {@link RecyclerView.Adapter} for the loan offers list.
 * @author Wijnand
 */
public class OffersListRecyclerAdapter extends PagedListRecyclerAdapter<OfferSummaryModel, OfferSummaryView> {

    private OfferSummaryView.ViewListener mListener;

    /**
     * Creates a new {@link OffersListRecyclerAdapter} instance.
     */
    public OffersListRecyclerAdapter() {
        super(R.layout.cv_loan_offer);
    }

    /** {@inheritDoc} */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferSummaryView view = (OfferSummaryView) LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
        return new OffersListViewHolder(view);
    }

    /** {@inheritDoc} */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder != null && hasData()) {
            ((OffersListViewHolder) holder).setListener(mListener);
        }
    }

    /**
     * Stores a new callback listener that the generated {@link OfferSummaryView}s will invoke.
     * @param listener New callback listener.
     */
    public void setViewListener(OfferSummaryView.ViewListener listener) {
        mListener = listener;
    }
}
