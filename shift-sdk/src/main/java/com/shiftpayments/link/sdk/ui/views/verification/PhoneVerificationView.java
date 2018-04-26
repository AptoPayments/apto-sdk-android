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
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationView
        extends UserDataView<PhoneVerificationView.ViewListener>
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
    private TextView mSubmitButton;
    private TextView mResendButton;
    private LoadingView mLoadingView;
    final int CODE_LENGTH = 6;


    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public PhoneVerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public PhoneVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mPinView = (PinView) findViewById(R.id.pinView);
        mSubmitButton = (TextView) findViewById(R.id.tv_submit_bttn);
        mResendButton = (TextView) findViewById(R.id.tv_resend_bttn);
        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
        configurePinView();
        setColors();
    }

    @Override
    public void setColors() {
        super.setColors();

        int color = UIStorage.getInstance().getPrimaryColor();
        mSubmitButton.setBackgroundColor(color);
        mResendButton.setTextColor(color);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mPinView.setOnCompleteListener((completed, pinResults) -> {
            if (completed) {
                KeyboardUtil.hideKeyboard(PhoneVerificationView.super.getContext());
                mListener.nextClickHandler();
            }
        });

        if (mSubmitButton != null) {
            mSubmitButton.setOnClickListener(this);
        }
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
        if (id == R.id.tv_submit_bttn) {
            mListener.nextClickHandler();
        }
        else if (id == R.id.tv_resend_bttn) {
            mListener.resendClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void displayErrorMessage(String message) {
        showToast(message);
    }

    public void displaySentMessage(String message) {
        showToast(message);
    }

    private void showToast(String message) {
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

    public void configurePinView() {
        mPinView.setPin(CODE_LENGTH);
        mPinView.setKeyboardMandatory(false);
        mPinView.setMaskPassword(false);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }
}
