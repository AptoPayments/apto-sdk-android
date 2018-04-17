package com.shift.link.sdk.ui.adapters.fundingsources;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.card.FundingSourceModel;
import com.shift.link.sdk.ui.views.card.FundingSourceView;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;

/**
 * A concrete {@link RecyclerView.Adapter} for the funding source list.
 * @author Adrian
 */
public class FundingSourcesListRecyclerAdapter extends PagedListRecyclerAdapter<FundingSourceModel, FundingSourceView> {

    private FundingSourceView.ViewListener mListener;

    /**
     * Creates a new {@link FundingSourcesListRecyclerAdapter} instance.
     */
    public FundingSourcesListRecyclerAdapter() {
        super(R.layout.cv_funding_source);
    }

    /** {@inheritDoc} */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FundingSourceView view = (FundingSourceView) LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
        return new FundingSourcesListViewHolder(view);
    }

    /** {@inheritDoc} */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder != null && hasData()) {
            ((FundingSourcesListViewHolder) holder).setListener(mListener);
        }
    }

    /**
     * Stores a new callback listener that the generated {@link FundingSourceView}s will invoke.
     * @param listener New callback listener.
     */
    public void setViewListener(FundingSourceView.ViewListener listener) {
        mListener = listener;
    }
}
