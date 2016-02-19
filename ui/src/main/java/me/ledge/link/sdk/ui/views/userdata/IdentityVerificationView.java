package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.SsnEditText;

/**
 * Displays the user details screen.
 * @author Wijnand
 */
public class IdentityVerificationView
        extends UserDataView<IdentityVerificationView.ViewListener>
        implements View.OnClickListener, ViewWithToolbar {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends NextButtonListener {

        /**
         * Called when the birthday input field has been pressed.
         */
        void birthdayClickHandler();
    }

    private Button mBirthdayButton;
    private TextInputLayout mBirthdayWrapper;
    private AppCompatEditText mBirthdayField;

    private TextInputLayout mSocialSecurityWrapper;
    private SsnEditText mSocialSecurityField;

    private RelativeLayout mLoadingOverlay;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public IdentityVerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public IdentityVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showLoading(false);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mBirthdayButton = (Button) findViewById(R.id.btn_birthday);
        mBirthdayWrapper = (TextInputLayout) findViewById(R.id.til_birthday);
        mBirthdayField = (AppCompatEditText) findViewById(R.id.et_birthday);

        mSocialSecurityWrapper = (TextInputLayout) findViewById(R.id.til_social_security);
        mSocialSecurityField = (SsnEditText) findViewById(R.id.et_social_security);

        mLoadingOverlay = (RelativeLayout) findViewById(R.id.rl_loading_overlay);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mBirthdayButton.setOnClickListener(this);
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
        } else {
            super.onClick(view);
        }
    }

    /**
     * @return Social security number.
     */
    public String getSocialSecurityNumber() {
        return mSocialSecurityField.getSsn();
    }

    /**
     * Shows the user's birthday.
     * @param birthday Formatted birthday.
     */
    public void setBirthday(String birthday) {
        mBirthdayField.setText(birthday);
    }

    /**
     * Updates the birthday field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateBirthdayError(boolean show, int errorMessageId) {
        updateErrorDisplay(mBirthdayWrapper, show, errorMessageId);
    }

    /**
     * Updates the SSN field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateSocialSecurityError(boolean show, int errorMessageId) {
        updateErrorDisplay(mSocialSecurityWrapper, show, errorMessageId);
    }

    /**
     * Changes the loading overlay visibility.
     * @param show Whether the loading overlay should be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingOverlay.setVisibility(VISIBLE);
        } else {
            mLoadingOverlay.setVisibility(GONE);
        }
    }
}