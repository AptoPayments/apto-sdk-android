package me.ledge.link.sdk.ui.views.verification;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dpizarro.pinview.library.PinView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.views.userdata.UserDataView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationView
        extends UserDataView<PhoneVerificationView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

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
        configurePinView();
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
        mPinView.setOnCompleteListener(new PinView.OnCompleteListener() {
            @Override
            public void onComplete(boolean completed, final String pinResults) {
                if (completed) {
                    hideKeyboard(PhoneVerificationView.super.getContext());
                    mListener.nextClickHandler();
                }
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

    private void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = ((Activity) context).getCurrentFocus();
        if (imm != null && currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }
}
