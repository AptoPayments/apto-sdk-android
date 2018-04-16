package com.shift.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.shift.link.sdk.ui.views.ViewWithToolbar;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.card.FundingSourceModel;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the manage account screen.
 * @author Adrian
 */
public class ManageAccountView
        extends CoordinatorLayout implements View.OnClickListener, ViewWithToolbar {

    private ViewListener mListener;
    private RecyclerView mFundingSourcesListView;
    private LinearLayout mSignOutHolder;
    private ImageButton mAddFundingSourceButton;
    protected Toolbar mToolbar;

    public ManageAccountView(Context context) {
        this(context, null);
    }

    public ManageAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.ll_sign_out) {
            mListener.signOut();
        }
        else if(id == R.id.ib_add_funding_source) {
            mListener.addFundingSource();
        }
        else if(id == R.id.toolbar) {
            mListener.onBack();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void signOut();
        void addFundingSource();
        void onBack();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();
        setColors();
    }

    protected void findAllViews() {
        mFundingSourcesListView = (RecyclerView) findViewById(R.id.rv_funding_sources_list);
        mSignOutHolder = (LinearLayout) findViewById(R.id.ll_sign_out);
        mAddFundingSourceButton = (ImageButton) findViewById(R.id.ib_add_funding_source);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpListeners() {
        mSignOutHolder.setOnClickListener(this);
        mAddFundingSourceButton.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(this);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mAddFundingSourceButton.setColorFilter(color);
        mToolbar.setBackgroundDrawable(new ColorDrawable(color));
        mToolbar.setTitleTextColor(contrastColor);
        Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(contrastColor, PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<FundingSourceModel, FundingSourceView> adapter) {
        mFundingSourcesListView.setAdapter(adapter);
    }
}
