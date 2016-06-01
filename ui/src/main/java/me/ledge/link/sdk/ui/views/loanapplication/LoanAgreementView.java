package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import me.ledge.link.sdk.ui.utils.ResourceUtil;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the loan agreement.
 * @author Wijnand
 */
public class LoanAgreementView extends RelativeLayout implements ViewWithToolbar, View.OnClickListener {

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
    private RelativeLayout mLoadingOverlay;

    private ImageView mLenderLogo;
    private TextView mLenderNameField;
    private TextView mLoanInterestField;
    private TextView mLoanAmountField;
    private TextView mLoanDurationField;
    private TextView mPaymentField;
    private TextView mConfirmButton;
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

        ResourceUtil util = new ResourceUtil();
        mBackgroundColor = getResources().getColor(
                util.getResourceIdForAttribute(getContext(), R.attr.llsdk_loanAgreement_buttonColorOne));
        mForegroundColor = getResources().getColor(
                util.getResourceIdForAttribute(getContext(), R.attr.llsdk_loanAgreement_buttonColorTwo));

        mScrollMoreCopy = getResources().getString(R.string.loan_agreement_button_scroll_down);
        mAgreeCopy = getResources().getString(R.string.loan_agreement_button_agree);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mScrollView = (ObservableScrollView) findViewById(R.id.osv_scroll);
        mLoadingOverlay = (RelativeLayout) findViewById(R.id.rl_loading_overlay);

        mLenderLogo = (ImageView) findViewById(R.id.iv_lender_logo);
        mLenderNameField = (TextView) findViewById(R.id.tv_lender_name);
        mLoanInterestField = (TextView) findViewById(R.id.tv_loan_interest_rate);
        mLoanAmountField = (TextView) findViewById(R.id.tv_loan_amount);
        mLoanDurationField = (TextView) findViewById(R.id.tv_loan_duration);
        mPaymentField = (TextView) findViewById(R.id.tv_payment);
        mConfirmButton = (TextView) findViewById(R.id.tv_confirm_btn);
        mAcceptTermsCheck = (CheckBox) findViewById(R.id.cb_accept_terms);
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
        mLoanAmountField.setText(data.getTotalAmount(getResources()));
        mLoanDurationField.setText(data.getTerm(getResources()));
        mPaymentField.setText(data.getPaymentAmount(getResources()));
    }

    /**
     * Toggles the loading overlay visibility.
     * @param show boolean - Whether the overlay should be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingOverlay.setVisibility(VISIBLE);
        } else {
            mLoadingOverlay.setVisibility(GONE);
        }
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
     * @param fullyScrolled boolen - Whether the View is fully scrolled.
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
}
