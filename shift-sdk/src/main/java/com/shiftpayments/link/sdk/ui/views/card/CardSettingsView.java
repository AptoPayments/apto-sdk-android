package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.card.FundingSourceModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;


/**
 * Displays the card settings
 * @author Adrian
 */
public class CardSettingsView extends CoordinatorLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private RecyclerView mFundingSourcesListView;
    private TextView mFundingSourceLabel;
    private LinearLayout mAddFundingSourceHolder;
    private ImageButton mAddFundingSourceButton;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public CardSettingsView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public CardSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupRecyclerView();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<FundingSourceModel, FundingSourceView> adapter) {
        mFundingSourcesListView.setAdapter(adapter);
    }

    public void showFundingSourceLabel(boolean show) {
        if (show) {
            mFundingSourceLabel.setVisibility(VISIBLE);
        } else {
            mFundingSourceLabel.setVisibility(GONE);
        }
    }

    public void showAddFundingSourceButton(boolean show) {
        if(show) {
            mAddFundingSourceHolder.setVisibility(VISIBLE);
        }
        else {
            mAddFundingSourceHolder.setVisibility(GONE);
        }
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.toolbar);
        mFundingSourcesListView = findViewById(R.id.rv_funding_sources_list);
        mFundingSourceLabel = findViewById(R.id.tv_funding_sources_header);
        mAddFundingSourceButton = findViewById(R.id.ib_add_funding_source);
        mAddFundingSourceHolder = findViewById(R.id.ll_add_funding_source);

    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        /*Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationIcon(backArrow);*/
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
