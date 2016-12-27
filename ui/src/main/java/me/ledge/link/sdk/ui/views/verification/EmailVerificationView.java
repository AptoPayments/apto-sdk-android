package me.ledge.link.sdk.ui.views.verification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.views.userdata.UserDataView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the email verification screen.
 * @author Adrian
 */
public class EmailVerificationView
        extends UserDataView<EmailVerificationView.ViewListener>
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

    private TextView mSubmitButton;
    private TextView mResendButton;
    private TextView mEmailInfo;


    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public EmailVerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public EmailVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mSubmitButton = (TextView) findViewById(R.id.tv_submit_bttn);
        mResendButton = (TextView) findViewById(R.id.tv_resend_bttn);
        mEmailInfo = (TextView) findViewById(R.id.tv_verification_code_header);
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

    public void displayEmailAddress(String email) {
        mEmailInfo.append(email);
    }
}
