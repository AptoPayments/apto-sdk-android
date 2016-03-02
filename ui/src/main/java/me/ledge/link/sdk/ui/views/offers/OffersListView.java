package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the offers list.
 * @author Wijnand
 */
public class OffersListView extends RelativeLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private RecyclerView mOffersListView;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mOffersListView = (RecyclerView) findViewById(R.id.rv_offers_list);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mOffersListView.setHasFixedSize(true);
        mOffersListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupRecyclerView();
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<OfferSummaryModel, OfferSummaryView> adapter) {
        mOffersListView.setAdapter(adapter);
    }
}
