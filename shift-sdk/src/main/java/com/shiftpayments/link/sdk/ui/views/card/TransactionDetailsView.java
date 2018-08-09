package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.ImageView;
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
    private ImageView mHeader;
    private RecyclerView mRecyclerView;
    private NestedScrollView mNestedScrollView;
    private TransactionView mTransactionView;
    private TextView mDetailAmount;
    private TextView mType;
    private TextView mCategory;
    private TextView mTransactionDate;
    private RelativeLayout mSettlementDateHolder;
    private TextView mSettlementDate;
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
    private TextView mTransfersHeader;

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
        setHeader();
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

    public void setSettlementDate(String date) {
        mSettlementDate.setText(date);
        mSettlementDateHolder.setVisibility(VISIBLE);
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

    public void showTransfersHeader(boolean show) {
        if(show) {
            mTransfersHeader.setVisibility(VISIBLE);
        }
        else {
            mTransfersHeader.setVisibility(GONE);
        }
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
    }

    public void setHeader() {
        // TODO: check if location coordinates are present and show map
        disableExpandingToolbar();
        //mHeader.setImageResource(R.drawable.google_map);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbar = findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = findViewById(R.id.settings_toolbar);
        mHeader = findViewById(R.id.iv_header);
        mRecyclerView = findViewById(R.id.recycler);
        mNestedScrollView = findViewById(R.id.nested_scroll_view);
        mTransactionView = findViewById(R.id.cv_transaction_view);
        mDetailAmount = findViewById(R.id.tv_transaction_amount);
        mType = findViewById(R.id.tv_transaction_type);
        mCategory = findViewById(R.id.tv_transaction_category);
        mTransactionDate = findViewById(R.id.tv_transaction_date);
        mSettlementDate = findViewById(R.id.tv_transaction_settlement_date);
        mSettlementDateHolder = findViewById(R.id.rl_settlement_date);
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
        mTransfersHeader = findViewById(R.id.tv_transfers_header);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationIcon(backArrow);
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                //Collapsed
                mToolbar.setBackgroundColor(primaryColor);
            } else {
                //Expanded
                mToolbar.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        mCollapsingToolbar.setCollapsedTitleTextColor(contrastColor);
        mCollapsingToolbar.setExpandedTitleColor(contrastColor);
        mCollapsingToolbar.setBackgroundColor(primaryColor);
        mAddressImageView.setColorFilter(primaryColor);
    }

    private void disableExpandingToolbar() {
        mAppBarLayout.setExpanded(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mNestedScrollView.setNestedScrollingEnabled(false);
    }
}
