package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
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
public class CardSettingsView extends CoordinatorLayout implements ViewWithToolbar, View.OnClickListener {

    private ViewListener mListener;
    private Toolbar mToolbar;
    private RecyclerView mFundingSourcesListView;
    private TextView mFundingSourceLabel;
    private LinearLayout mAddFundingSourceHolder;
    private ImageButton mAddFundingSourceButton;
    private TextView mAddFundingSourceLabel;

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void addFundingSource();
        void onClose();
    }


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
        setupListeners();
        setupRecyclerView();
        setColors();
    }


    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.ib_add_funding_source || id == R.id.tv_add_funding_source_label) {
            mListener.addFundingSource();
        } else if (id == R.id.toolbar) {
            mListener.onClose();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
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

    private void setupListeners() {
        mAddFundingSourceButton.setOnClickListener(this);
        mAddFundingSourceLabel.setOnClickListener(this);
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
        mAddFundingSourceLabel = findViewById(R.id.tv_add_funding_source_label);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        Drawable closeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_close);
        int actionBarColor = getResources().getColor(R.color.llsdk_actionbar_background);
        mToolbar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        mToolbar.setNavigationIcon(closeIcon);
        mAddFundingSourceButton.setColorFilter(primaryColor);
        mAddFundingSourceLabel.setTextColor(primaryColor);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
