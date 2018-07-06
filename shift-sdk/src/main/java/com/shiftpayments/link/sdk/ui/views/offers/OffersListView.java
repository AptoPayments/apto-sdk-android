package com.shiftpayments.link.sdk.ui.views.offers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shiftpayments.link.sdk.ui.presenters.offers.OffersListPresenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;

/**
 * Displays the offers list.
 * @author Wijnand
 */
public class OffersListView extends OffersBaseView {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends LoanOfferErrorView.ViewListener {

        /**
         * Called when the empty case "Update loan request" button has been pressed.
         */
        void updateClickedHandler();
    }

    private ViewListener mListener;

    private Toolbar mToolbar;
    private RecyclerView mOffersListView;
    private LinearLayout mEmptyCaseHolder;
    private TextView mUpdateButton;

    private LoanOfferErrorView mErrorView;
    private LoadingView mLoadingView;

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

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();
        setColors();

        showEmptyCase(false);
        showError(false);
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener != null && view.getId() == R.id.tv_bttn_edit_info) {
            mListener.updateClickedHandler();
        }
    }

    @Override
    public void setListener(OffersListPresenter offersListPresenter) {
        this.setListener((ViewListener) offersListPresenter);
    }

    @Override
    public void setData(IntermediateLoanApplicationModel data) {
        mErrorView.setData(data);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<OfferSummaryModel, OfferListSummaryView> adapter) {
        mOffersListView.setAdapter(adapter);
    }

    /**
     * Updates the empty case visibility.
     * @param show Whether the empty case should be shown.
     */
    public void showEmptyCase(boolean show) {
        showView(show, mEmptyCaseHolder);
    }

    /**
     * Updates the error visibility.
     * @param show Whether the error should be shown.
     */
    public void showError(boolean show) {
        showView(show, mErrorView);
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener New {@link ViewListener}.
     */
    private void setListener(ViewListener listener) {
        mListener = listener;
        mErrorView.setListener(listener);
    }

    private void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        if (mUpdateButton != null) {
            mUpdateButton.setBackgroundColor(color);
            mUpdateButton.setTextColor(contrastColor);
        }
        mToolbar.setBackgroundDrawable(new ColorDrawable(color));
        mToolbar.setTitleTextColor(contrastColor);
        Drawable backArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(contrastColor, PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);

        Drawable menuIcon = mToolbar.getOverflowIcon();
        if (menuIcon != null) {
            menuIcon.setColorFilter(contrastColor, PorterDuff.Mode.SRC_ATOP);
            mToolbar.setOverflowIcon(menuIcon);
        }
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mOffersListView = findViewById(R.id.rv_offers_list);
        mEmptyCaseHolder = findViewById(R.id.ll_empty_case);
        mUpdateButton = findViewById(R.id.tv_bttn_edit_info);

        mErrorView = findViewById(R.id.ll_loan_error);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    /**
     * Sets up all child View listeners.
     */
    private void setUpListeners() {
        mUpdateButton.setOnClickListener(this);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mOffersListView.setHasFixedSize(true);
        mOffersListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Updates a {@link View}'s visibility.
     * @param show Whether the view should be shown.
     * @param view The view to update.
     */
    private void showView(boolean show, View view) {
        if (show) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }
}
