package com.shiftpayments.link.sdk.ui.views.fundingaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the enable auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayView
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener, ViewWithIndeterminateLoading {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {

        /**
         * Called when the primary button has been pressed.
         */
        void primaryButtonClickHandler();
        /**
         * Called when the secondary button has been pressed.
         */
        void secondaryButtonClickHandler();
    }
    private TextView mPrimaryButton;

    private TextView mSecondaryButton;
    private TextView mFinancialAccountInfo;
    private ImageView mImageView;
    private EnableAutoPayView.ViewListener mListener;
    private Toolbar mToolbar;
    private LoadingView mLoadingView;
    public EnableAutoPayView(Context context) {
        super(context);
    }

    public EnableAutoPayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_enable_auto_pay_primary_bttn) {
            mListener.primaryButtonClickHandler();
        }
        else if (id == R.id.tv_enable_auto_pay_secondary_bttn) {
            mListener.secondaryButtonClickHandler();
        }
    }

    /**
     * Stores a new {@link EnableAutoPayView.ViewListener}.
     * @param listener New {@link EnableAutoPayView.ViewListener}.
     */
    public void setListener(EnableAutoPayView.ViewListener listener) {
        mListener = listener;
    }

    public void displayFinancialAccountInfo(String info) {
        mFinancialAccountInfo.setVisibility(VISIBLE);
        mFinancialAccountInfo.setText(info);
    }

    public void setPrimaryButtonText(String buttonText) {
        mPrimaryButton.setVisibility(VISIBLE);
        mPrimaryButton.setText(buttonText);
    }

    public void setSecondaryButtonText(String buttonText) {
        mSecondaryButton.setVisibility(VISIBLE);
        mSecondaryButton.setText(buttonText);
    }

    public void setImage(String imageUrl) {
        ShiftPlatform.getImageLoader().load(imageUrl, mImageView);
    }

    private void findAllViews() {
        mPrimaryButton = findViewById(R.id.tv_enable_auto_pay_primary_bttn);
        mSecondaryButton = findViewById(R.id.tv_enable_auto_pay_secondary_bttn);
        mFinancialAccountInfo = findViewById(R.id.tv_financial_account_label);
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mImageView = findViewById(R.id.iv_auto_pay_logo);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mPrimaryButton.setTextColor(contrastColor);
        mPrimaryButton.setBackgroundColor(primaryColor);
        mSecondaryButton.setTextColor(primaryColor);
    }

    private void setupListeners() {
        if (mPrimaryButton != null) {
            mPrimaryButton.setOnClickListener(this);
        }
    }
}
