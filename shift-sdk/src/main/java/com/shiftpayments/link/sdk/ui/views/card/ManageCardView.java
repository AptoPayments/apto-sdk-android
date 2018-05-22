package com.shiftpayments.link.sdk.ui.views.card;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.shiftpayments.link.sdk.ui.R;

/**
 * Displays the manage card screen.
 * @author Adrian
 */
public class ManageCardView
        extends CoordinatorLayout
        implements SwipeRefreshLayout.OnRefreshListener {

    private ViewListener mListener;
    private FrameLayout mPinView;
    private ProgressBar mSpinner;
    private RecyclerView mTransactionsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public ManageCardView(Context context) {
        this(context, null);
    }

    public ManageCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showPinFragment(boolean show) {
        if(show) {
            mPinView.setVisibility(VISIBLE);
        }
        else {
            mPinView.setVisibility(GONE);
        }
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void pullToRefreshHandler();
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
    }

    @Override
    public void onRefresh() {
        mListener.pullToRefreshHandler();
    }

    public void configureTransactionsView(LinearLayoutManager linearLayoutManager, EndlessRecyclerViewScrollListener scrollListener, TransactionsAdapter adapter) {
        if (mTransactionsRecyclerView != null) {
            mTransactionsRecyclerView.setLayoutManager(linearLayoutManager);
            mTransactionsRecyclerView.setAdapter(adapter);
            mTransactionsRecyclerView.addOnScrollListener(scrollListener);
        }
    }

    public void showLoading(Activity activity, boolean show) {
        if(show) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mSpinner.setVisibility(View.VISIBLE);
        }
        else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mSpinner.setVisibility(View.GONE);
        }
    }

    public void setRefreshing(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    private void findAllViews() {
        mPinView = findViewById(R.id.pin_fragment);
        mSpinner = findViewById(R.id.pb_spinner);
        mTransactionsRecyclerView = findViewById(R.id.transactions_recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_container);
    }

    private void setUpListeners() {
        if (mTransactionsRecyclerView != null) {
            mTransactionsRecyclerView.setAdapter(new TransactionsAdapter());
        }
        if(mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }
}
