package com.shiftpayments.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the loan agreement.
 * @author Wijnand
 */
public class LoanAgreementView
        extends RelativeLayout
        implements ViewWithToolbar, ViewWithIndeterminateLoading, View.OnClickListener {

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener extends ObservableScrollViewCallbacks {

        /**
         * Called when the "confirm" button has been pressed.
         */
        void confirmClickHandler();
    }

    private Toolbar mToolbar;
    private ObservableScrollView mScrollView;
    private LoadingView mLoadingView;

    private ImageView mLenderLogo;
    private TextView mLenderNameField;
    private TextView mLoanInterestField;
    private TextView mLoanAmountField;
    private TextView mLoanDurationField;
    private TextView mPaymentField;
    private TextView mConfirmButton;
    private TextView mDisclaimer;
    private TextView mConsentDisclaimer;
    private CheckBox mAcceptTermsCheck;

    private ViewListener mViewListener;
    private int mMaxScroll;
    private int mBackgroundColor;
    private int mForegroundColor;
    private String mScrollMoreCopy;
    private String mAgreeCopy;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanAgreementView(Context context) {
        this(context, null);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public LoanAgreementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mMaxScroll = -1;

        mForegroundColor = UIStorage.getInstance().getPrimaryContrastColor();
        mBackgroundColor = UIStorage.getInstance().getPrimaryColor();

        mScrollMoreCopy = getResources().getString(R.string.loan_agreement_button_scroll_down);
        mAgreeCopy = getResources().getString(R.string.loan_agreement_button_agree);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mScrollView = findViewById(R.id.osv_scroll);
        mLoadingView = findViewById(R.id.rl_loading_overlay);

        mLenderLogo = findViewById(R.id.iv_lender_logo);
        mLenderNameField = findViewById(R.id.tv_lender_name);
        mLoanInterestField = findViewById(R.id.tv_loan_interest_rate);
        mLoanAmountField = findViewById(R.id.tv_loan_amount);
        mLoanDurationField = findViewById(R.id.tv_loan_duration);
        mPaymentField = findViewById(R.id.tv_payment);
        mConfirmButton = findViewById(R.id.tv_confirm_btn);
        mAcceptTermsCheck = findViewById(R.id.cb_accept_terms);
        mDisclaimer = findViewById(R.id.tv_esign_disclaimer);
        mConsentDisclaimer = findViewById(R.id.tv_esign_consent_disclaimer);
    }

    /**
     * Sets up all required callback listeners.
     */
    private void setUpListeners() {
        mConfirmButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    private void setColors() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(mBackgroundColor));
        mToolbar.setTitleTextColor(mForegroundColor);
        ((TextView) findViewById(R.id.tv_terms_header)).setTextColor(Color.BLACK);
        ((TextView) findViewById(R.id.tv_borrower_agreement_header)).setTextColor(Color.BLACK);
        mLenderNameField.setTextColor(Color.BLACK);
        mLoanInterestField.setTextColor(Color.BLACK);
        mLoanDurationField.setTextColor(Color.BLACK);
        mLoanAmountField.setTextColor(Color.BLACK);
        mPaymentField.setTextColor(Color.BLACK);
        ((AppCompatCheckBox) mAcceptTermsCheck).setSupportButtonTintList(ColorStateList.valueOf(mBackgroundColor));
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mViewListener != null) {
            mViewListener.confirmClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new reference to a {@link ViewListener} that will be invoked by this View.
     * @param listener {@link ViewListener} - The new {@link ViewListener} to store.
     */
    public void setViewListener(ViewListener listener) {
        mViewListener = listener;
        mScrollView.setScrollViewCallbacks(listener);
    }

    /**
     * Updates this View with the latest data.
     * @param data New data.
     */
    public void setData(LoanAgreementModel data) {
        if (data == null) {
            return;
        }

        mLenderLogo.setVisibility(GONE);
        mLenderNameField.setVisibility(GONE);

        if (data.hasImageLoader() && data.getLenderImage() != null) {
            data.getImageLoader().load(data.getLenderImage(), mLenderLogo);
            mLenderLogo.setVisibility(VISIBLE);
        } else {
            mLenderNameField.setText(data.getLenderName());
            mLenderNameField.setVisibility(VISIBLE);
        }

        mLoanInterestField.setText(data.getInterestRate(getResources()));
        mLoanAmountField.setText(data.getTotalAmount());
        mLoanDurationField.setText(data.getTerm(getResources()));
        mPaymentField.setText(data.getPaymentAmount());
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * @return boolean - Whether the terms of service and the privacy policy have been agreed to.
     */
    public boolean hasAcceptedTerms() {
        return mAcceptTermsCheck.isChecked();
    }

    /**
     * @return int - The maximum amount the {@code mScrollView} can scroll.
     */
    public int getMaxScroll() {
        if (mMaxScroll <= 0) {
            mMaxScroll = mScrollView.getChildAt(0).getHeight() - mScrollView.getHeight();
        }

        return mMaxScroll;
    }

    /**
     * @return int - The current vertical scroll position of the {@code mScrollView}.
     */
    public int getCurrentScroll() {
        int scroll = -1;
        if (mScrollView != null) {
            scroll = mScrollView.getCurrentScrollY();
        }

        return scroll;
    }

    /**
     * Scrolls the TCs text to the bottom.
     */
    public void scrollToBottom() {
        mScrollView.smoothScrollTo(0, getMaxScroll());
    }

    /**
     * Updates the looks of the bottom button.
     * @param fullyScrolled boolean - Whether the View is fully scrolled.
     */
    public void updateBottomButton(boolean fullyScrolled) {
        if (mConfirmButton == null) {
            return;
        }

        if (fullyScrolled) {
            mConfirmButton.setBackgroundColor(mBackgroundColor);
            mConfirmButton.setTextColor(mForegroundColor);
            mConfirmButton.setText(mAgreeCopy);
        } else {
            mConfirmButton.setBackgroundColor(mForegroundColor);
            mConfirmButton.setTextColor(mBackgroundColor);
            mConfirmButton.setText(mScrollMoreCopy);
        }
    }

    public void setDisclaimer(String disclaimer) {
        mDisclaimer.setText(disclaimer);
    }

    public void setConsentDisclaimer(String disclaimer) {
        mConsentDisclaimer.setText(disclaimer);
    }
}
