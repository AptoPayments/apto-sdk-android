package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
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

    protected Toolbar mToolbar;
    private ImageView mLogoView;
    private TransactionView mTransactionView;
    private TextView mAmountLabel;
    private TextView mDetailAmount;
    private TextView mCurrency;
    private TextView mType;
    private TextView mLocation;
    private TextView mCategory;
    private TextView mTransactionDate;
    private RelativeLayout mSettlementDateHolder;
    private TextView mSettlementDate;
    private TextView mTransactionId;
    private RelativeLayout mTransactionIdHolder;
    private TextView mShiftId;
    private RecyclerView mAdjustmentsRecyclerView;
    private ImageView mShiftLogo;
    private TextView mDeclineReason;
    private RelativeLayout mDeclineReasonHolder;
    private TextView mFee;
    private RelativeLayout mFeeHolder;
    private TextView mCashbackAmount;
    private RelativeLayout mCashbackAmountHolder;
    private TextView mHoldAmount;
    private RelativeLayout mHoldAmountHolder;

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
        setLogo();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setLogo() {
        mLogoView.setImageResource(R.drawable.coinbase_logo);
    }

    public void setTransactionIcon(Drawable icon) {
        mTransactionView.setTransactionIcon(icon);
    }

    public void setTransactionAmount(String amount) {
        mTransactionView.setTitle(amount);
    }

    public void setTransactionDescription(String description) {
        mTransactionView.setDescription(description);
    }

    public void setDetailAmount(String amount) {
        mDetailAmount.setText(amount);
    }

    public void setCurrency(String currency) {
        mCurrency.setText(currency);
    }

    public void setType(String type) {
        mType.setText(type);
    }

    public void setLocation(String location) {
        mLocation.setText(location);
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
        findViewById(R.id.settlement_date_separator).setVisibility(VISIBLE);
    }

    public void setTransactionId(String id) {
        mTransactionId.setText(id);
        mTransactionIdHolder.setVisibility(VISIBLE);
        findViewById(R.id.settlement_date_separator).setVisibility(VISIBLE);
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
        findViewById(R.id.decline_reason_separator).setVisibility(VISIBLE);
    }

    public void setFeeAmount(String fee) {
        mFee.setText(fee);
        mFeeHolder.setVisibility(VISIBLE);
        findViewById(R.id.fee_amount_separator).setVisibility(VISIBLE);
    }

    public void setHoldAmount(String amount) {
        mHoldAmount.setText(amount);
        mHoldAmountHolder.setVisibility(VISIBLE);
        findViewById(R.id.hold_amount_separator).setVisibility(VISIBLE);
    }

    public void setCashbackAmount(String cashback) {
        mCashbackAmount.setText(cashback);
        mCashbackAmountHolder.setVisibility(VISIBLE);
        findViewById(R.id.cashback_amount_separator).setVisibility(VISIBLE);
    }

    public void setAmountLabel(String label) {
        mAmountLabel.setText(label);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mLogoView = (ImageView) findViewById(R.id.iv_custodian_logo);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTransactionView = (TransactionView) findViewById(R.id.cv_transaction_view);
        mAmountLabel = (TextView) findViewById(R.id.tv_amount_label);
        mDetailAmount = (TextView) findViewById(R.id.tv_transaction_amount);
        mCurrency = (TextView) findViewById(R.id.tv_transaction_currency);
        mType = (TextView) findViewById(R.id.tv_transaction_type);
        mLocation = (TextView) findViewById(R.id.tv_transaction_location);
        mCategory = (TextView) findViewById(R.id.tv_transaction_category);
        mTransactionDate = (TextView) findViewById(R.id.tv_transaction_date);
        mSettlementDate = (TextView) findViewById(R.id.tv_transaction_settlement_date);
        mSettlementDateHolder = (RelativeLayout) findViewById(R.id.rl_settlement_date);
        mTransactionId = (TextView) findViewById(R.id.tv_transaction_id);
        mTransactionIdHolder = (RelativeLayout) findViewById(R.id.rl_transaction_id);
        mShiftId = (TextView) findViewById(R.id.tv_shift_transaction_id);
        mAdjustmentsRecyclerView = (RecyclerView) findViewById(R.id.adjustments_recycler_view);
        mShiftLogo = (ImageView) findViewById(R.id.iv_shift_logo);
        mDeclineReason = (TextView) findViewById(R.id.tv_transaction_decline_reason);
        mDeclineReasonHolder = (RelativeLayout) findViewById(R.id.rl_decline_reason);
        mFee = (TextView) findViewById(R.id.tv_fee_amount);
        mFeeHolder = (RelativeLayout) findViewById(R.id.rl_fee_amount);
        mCashbackAmount = (TextView) findViewById(R.id.tv_cashback_amount);
        mCashbackAmountHolder = (RelativeLayout) findViewById(R.id.rl_cashback_amount);
        mHoldAmount = (TextView) findViewById(R.id.tv_hold_amount);
        mHoldAmountHolder = (RelativeLayout) findViewById(R.id.rl_hold_amount);
    }

    private void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(color));
        mToolbar.setTitleTextColor(contrastColor);
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(contrastColor, PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        mShiftLogo.setColorFilter(new ColorMatrixColorFilter(matrix));
    }
}
