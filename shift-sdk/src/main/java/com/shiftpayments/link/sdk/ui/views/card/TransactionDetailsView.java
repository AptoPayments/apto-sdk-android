package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;


/**
 * Displays a single transaction
 * @author Adrian
 */
public class TransactionDetailsView extends CoordinatorLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedScrollView;
    private TransactionView mTransactionView;
    private TextView mDetailAmount;
    private TextView mType;
    private TextView mCategory;
    private TextView mTransactionDate;
    private TextView mTransactionId;
    private RelativeLayout mTransactionIdHolder;
    private TextView mShiftId;
    private RecyclerView mAdjustmentsRecyclerView;
    private TextView mDeclineReason;
    private RelativeLayout mDeclineReasonHolder;
    private TextView mFee;
    private RelativeLayout mFeeHolder;
    private TextView mCashbackAmount;
    private RelativeLayout mCashbackAmountHolder;
    private TextView mHoldAmount;
    private RelativeLayout mHoldAmountHolder;
    private ImageView mAddressImageView;
    private View mTitleBackground;
    private LinearLayout mTitleHolder;
    private TextView mTitle;
    private TextView mSubtitle;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public TransactionDetailsView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public TransactionDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTransactionAddress(String address) {
        mTransactionView.setAddress(address);
    }

    public void setDetailAmount(String amount) {
        mDetailAmount.setText(amount);
    }

    public void setType(String type) {
        mType.setText(type);
    }

    public void setCategory(String category) {
        mCategory.setText(category);
    }

    public void setTransactionDate(String date) {
        mTransactionDate.setText(date);
    }

    public void setTransactionId(String id) {
        mTransactionId.setText(id);
        mTransactionIdHolder.setVisibility(VISIBLE);
    }

    public void setShiftId(String id) {
        mShiftId.setText(id);
    }

    public void configureAdjustmentsAdapter(LinearLayoutManager manager, AdjustmentsAdapter adapter) {
        mAdjustmentsRecyclerView.setLayoutManager(manager);
        mAdjustmentsRecyclerView.setAdapter(adapter);
    }

    public void setDeclineReason(String reason) {
        mDeclineReason.setText(reason);
        mDeclineReasonHolder.setVisibility(VISIBLE);
    }

    public void setFeeAmount(String fee) {
        mFee.setText(fee);
        mFeeHolder.setVisibility(VISIBLE);
    }

    public void setHoldAmount(String amount) {
        mHoldAmount.setText(amount);
        mHoldAmountHolder.setVisibility(VISIBLE);
    }

    public void setCashbackAmount(String cashback) {
        mCashbackAmount.setText(cashback);
        mCashbackAmountHolder.setVisibility(VISIBLE);
    }

    public void setTitle(String title) {
        mCollapsingToolbar.setTitle(title);
        mTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        mSubtitle.setText(subtitle);
    }

    public void disableExpandingToolbar() {
        mAppBarLayout.setExpanded(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mNestedScrollView.setNestedScrollingEnabled(false);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = findViewById(R.id.settings_toolbar);
        mRecyclerView = findViewById(R.id.recycler);
        mNestedScrollView = findViewById(R.id.nested_scroll_view);
        mTransactionView = findViewById(R.id.cv_transaction_view);
        mDetailAmount = findViewById(R.id.tv_transaction_amount);
        mType = findViewById(R.id.tv_transaction_type);
        mCategory = findViewById(R.id.tv_transaction_category);
        mTransactionDate = findViewById(R.id.tv_transaction_date);
        mTransactionId = findViewById(R.id.tv_transaction_id);
        mTransactionIdHolder = findViewById(R.id.rl_transaction_id);
        mShiftId = findViewById(R.id.tv_shift_transaction_id);
        mAdjustmentsRecyclerView = findViewById(R.id.adjustments_recycler_view);
        mDeclineReason = findViewById(R.id.tv_transaction_decline_reason);
        mDeclineReasonHolder = findViewById(R.id.rl_decline_reason);
        mFee = findViewById(R.id.tv_fee_amount);
        mFeeHolder = findViewById(R.id.rl_fee_amount);
        mCashbackAmount = findViewById(R.id.tv_cashback_amount);
        mCashbackAmountHolder = findViewById(R.id.rl_cashback_amount);
        mHoldAmount = findViewById(R.id.tv_hold_amount);
        mHoldAmountHolder = findViewById(R.id.rl_hold_amount);
        mAddressImageView = findViewById(R.id.iv_address_icon);
        mTitleBackground = findViewById(R.id.collapsing_toolbar_title_background_view);
        mTitleHolder = findViewById(R.id.header_text_layout);
        mTitle = findViewById(R.id.tv_title);
        mSubtitle = findViewById(R.id.tv_subtitle);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                //Collapsed
                mToolbar.setBackgroundColor(primaryColor);
                mCollapsingToolbar.setStatusBarScrimColor(primaryColor);
                mTitleBackground.setVisibility(GONE);
                mTitleHolder.setVisibility(GONE);

            } else {
                //Expanded
                mToolbar.setBackgroundColor(Color.TRANSPARENT);
                mCollapsingToolbar.setStatusBarScrimColor(Color.TRANSPARENT);
                mTitleBackground.setVisibility(VISIBLE);
                mTitleBackground.setBackgroundColor(primaryColor);
                mTitleHolder.setVisibility(VISIBLE);
            }
        });
        mCollapsingToolbar.setCollapsedTitleTextColor(contrastColor);
        mCollapsingToolbar.setBackgroundColor(primaryColor);
        mAddressImageView.setColorFilter(primaryColor);
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        mToolbar.bringToFront();
        mTitle.setTextColor(contrastColor);
        mSubtitle.setTextColor(contrastColor);
        mTitleHolder.bringToFront();
    }
}
