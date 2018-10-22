package com.shiftpayments.link.sdk.ui.views.verification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.pinview.library.PinView;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.KeyboardUtil;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.userdata.NextButtonListener;
import com.shiftpayments.link.sdk.ui.views.userdata.UserDataView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the verification screen.
 * @author Adrian
 */
public class VerificationView
        extends UserDataView<VerificationView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener, ViewWithIndeterminateLoading {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        /**
         * Called when the re-send code button has been pressed.
         */
        void resendClickHandler();
    }

    private PinView mPinView;
    private TextView mDescription;
    private TextView mDataPointLabel;
    private TextView mResendButton;
    private LoadingView mLoadingView;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public VerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public VerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mPinView = findViewById(R.id.pinView);
        mDescription = findViewById(R.id.tv_verification_code_header);
        mDataPointLabel = findViewById(R.id.tv_verification_datapoint_label);
        mResendButton = findViewById(R.id.tv_resend_bttn);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    @Override
    protected void setColors() {
        super.setColors();

        int textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        mDataPointLabel.setTextColor(textSecondaryColor);
        mResendButton.setTextColor(textSecondaryColor);
        mPinView.setColorTextPinBoxes(textSecondaryColor);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setColors();
        mPinView.requestFocus();
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mPinView.setOnCompleteListener((completed, pinResults) -> {
            if (completed) {
                KeyboardUtil.hideKeyboard(VerificationView.super.getContext());
                mListener.nextClickHandler();
            }
        });

        if (mResendButton != null) {
            mResendButton.setOnClickListener(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_resend_bttn) {
            mListener.resendClickHandler();
        }
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void displaySentMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @return Verification code as entered by the user.
     */
    public String getVerificationCode() {
        return mPinView.getPinResults();
    }

    public void clearPinView() {
        mPinView.clear();
    }

    public void setDescription(String description) {
        mDescription.setText(description);
    }

    public void setDataPoint(String dataPoint) {
        showDataPoint(true);
        mDataPointLabel.setText(dataPoint);
    }

    public void showDataPoint(boolean show) {
        if(show) {
            mDataPointLabel.setVisibility(VISIBLE);
        }
        else {
            mDataPointLabel.setVisibility(INVISIBLE);
        }
    }

    public void showResendButton(boolean show) {
        if(show) {
            mResendButton.setVisibility(VISIBLE);
        }
        else {
            mResendButton.setVisibility(INVISIBLE);
        }
    }
}
