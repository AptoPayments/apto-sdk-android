package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.SsnEditText;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the user details screen.
 * @author Wijnand
 */
public class IdentityVerificationView
        extends UserDataView<IdentityVerificationView.ViewListener>
        implements View.OnClickListener, ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {

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

    private TextView mDisclaimer;
    private TextView mDisclaimersHeader;
    private TextView mDisclaimersField;

    private LoadingView mLoadingView;
    private ProgressBar mProgressBar;

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

        mDisclaimer = (TextView) findViewById(R.id.tv_security);
        mDisclaimersHeader = (TextView) findViewById(R.id.tv_disclaimers_header);
        mDisclaimersField = (TextView) findViewById(R.id.tv_disclaimers_body);
        mDisclaimersField.setMovementMethod(LinkMovementMethod.getInstance());

        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mBirthdayButton.setOnClickListener(this);
        mSocialSecurityField.setOnClickListener(this);
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
     * Sets a new color resource for the progress bar to use.
     * @param colorResourceId Color resource ID.
     */
    public void setProgressColor(int colorResourceId) {
        if (mProgressBar == null || mProgressBar.getIndeterminateDrawable() == null) {
            return;
        }

        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(colorResourceId), android.graphics.PorterDuff.Mode.SRC_IN);
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
     * Shows the user's social security number with hyphens.
     * @param ssn social security number.
     */
    public void setSSN(String ssn) {
        ssn = new StringBuilder(ssn).insert(3, "-").toString();
        ssn = new StringBuilder(ssn).insert(6, "-").toString();
        mSocialSecurityField.setText(ssn);
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

    public void setDisclaimers(String disclaimers) {
        mDisclaimersField.setText(Html.fromHtml(disclaimers));
    }

    /** {@inheritDoc} */
    @Override
    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
    }

    public void setButtonText(String buttonText) {
        super.mNextButton.setText(buttonText);
    }

    public void showDisclaimers(boolean show) {
        if(show) {
            mDisclaimer.setVisibility(VISIBLE);
            mDisclaimersHeader.setVisibility(VISIBLE);
            mDisclaimersField.setVisibility(VISIBLE);
        }
        else {
            mDisclaimer.setVisibility(GONE);
            mDisclaimersHeader.setVisibility(GONE);
            mDisclaimersField.setVisibility(GONE);
        }
    }
}
