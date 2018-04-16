package com.shift.link.sdk.ui.adapters.offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shift.link.sdk.ui.views.offers.OfferListSummaryView;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shift.link.sdk.ui.views.offers.OfferListSummaryView;

/**
 * A concrete {@link RecyclerView.Adapter} for the loan offers list.
 * @author Wijnand
 */
public class OffersListRecyclerAdapter extends PagedListRecyclerAdapter<OfferSummaryModel, OfferListSummaryView> {

    private OfferListSummaryView.ViewListener mListener;

    /**
     * Creates a new {@link OffersListRecyclerAdapter} instance.
     */
    public OffersListRecyclerAdapter() {
        super(R.layout.cv_loan_offer);
    }

    /** {@inheritDoc} */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfferListSummaryView view = (OfferListSummaryView) LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
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
     * Stores a new callback listener that the generated {@link OfferListSummaryView}s will invoke.
     * @param listener New callback listener.
     */
    public void setViewListener(OfferListSummaryView.ViewListener listener) {
        mListener = listener;
    }
}
