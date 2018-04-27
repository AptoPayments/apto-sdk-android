package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;


/**
 * Displays a single transaction
 * @author Adrian
 */
public class TransactionDetailsView extends CoordinatorLayout implements ViewWithToolbar {

    private ImageView mLogoView;
    protected Toolbar mToolbar;

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
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setLogo() {
        mLogoView.setImageResource(R.drawable.coinbase_logo);
    }

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mLogoView = (ImageView) findViewById(R.id.iv_custodian_logo);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
