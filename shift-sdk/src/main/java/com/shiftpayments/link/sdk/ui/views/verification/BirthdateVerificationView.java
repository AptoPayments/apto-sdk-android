package com.shiftpayments.link.sdk.ui.views.verification;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.userdata.NextButtonListener;
import com.shiftpayments.link.sdk.ui.views.userdata.UserDataView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the birthdate screen.
 * @author Adrian
 */
public class BirthdateVerificationView
        extends UserDataView<BirthdateVerificationView.ViewListener>
        implements View.OnClickListener, ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void birthdayClickHandler();
    }

    private Button mBirthdateButton;
    private TextInputLayout mBirthdateWrapper;
    private AppCompatEditText mBirthdateField;
    private LoadingView mLoadingView;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public BirthdateVerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public BirthdateVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mBirthdateButton = (Button) findViewById(R.id.btn_birthday);
        mBirthdateWrapper = (TextInputLayout) findViewById(R.id.til_birthday);
        mBirthdateField = (AppCompatEditText) findViewById(R.id.et_birthday);
        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mBirthdateButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.btn_birthday) {
            mListener.birthdayClickHandler();
        }
        else {
            super.onClick(view);
        }
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * Shows the user's birthday.
     * @param birthday Formatted birthday.
     */
    public void setBirthdate(String birthday) {
        mBirthdateField.setText(birthday);
    }

    /**
     * Updates the birthday field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateBirthdateError(boolean show, int errorMessageId) {
        updateErrorDisplay(mBirthdateWrapper, show, errorMessageId);
    }

    public String getBirthdate() {
        return mBirthdateField.getText().toString();
    }
}
