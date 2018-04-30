package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

import org.w3c.dom.Text;


/**
 * Displays a single transaction
 * @author Adrian
 */
public class TransactionDetailsView extends CoordinatorLayout implements ViewWithToolbar {

    protected Toolbar mToolbar;
    private ImageView mLogoView;
    private TransactionView mTransactionView;
    private TextView mDetailAmount;
    private TextView mCurrency;
    private TextView mType;
    private TextView mLocation;
    private TextView mCategory;
    private TextView mTransactionDate;
    private TextView mSettlementDate;
    private TextView mTransactionId;

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
    }

    public void setTransactionId(String id) {
        mTransactionId.setText(id);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mLogoView = (ImageView) findViewById(R.id.iv_custodian_logo);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTransactionView = (TransactionView) findViewById(R.id.cv_transaction_view);
        mDetailAmount = (TextView) findViewById(R.id.tv_transaction_amount);
        mCurrency = (TextView) findViewById(R.id.tv_transaction_currency);
        mType = (TextView) findViewById(R.id.tv_transaction_type);
        mLocation = (TextView) findViewById(R.id.tv_transaction_location);
        mCategory = (TextView) findViewById(R.id.tv_transaction_category);
        mTransactionDate = (TextView) findViewById(R.id.tv_transaction_date);
        mSettlementDate = (TextView) findViewById(R.id.tv_transaction_settlement_date);
        mTransactionId = (TextView) findViewById(R.id.tv_transaction_id);
    }

    private void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(color));
        mToolbar.setTitleTextColor(contrastColor);
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        backArrow.setColorFilter(contrastColor, PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(backArrow);
    }
}
