package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
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
        void birthdayClickHandler();
        void ssnCheckBoxClickHandler();
    }

    private Button mBirthdayButton;
    private TextInputLayout mBirthdayWrapper;
    private AppCompatEditText mBirthdayField;

    private TextInputLayout mSocialSecurityWrapper;
    private SsnEditText mSocialSecurityField;
    private CheckBox mSocialSecurityAvailableCheck;
    private TextView mSocialSecurityAvailableField;

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
        ((AppCompatCheckBox) mSocialSecurityAvailableCheck).setSupportButtonTintList(
                ColorStateList.valueOf(UIStorage.getInstance().getPrimaryColor()));
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
        mSocialSecurityAvailableCheck = (CheckBox) findViewById(R.id.cb_ssn_itin_not_available);
        mSocialSecurityAvailableField = (TextView) findViewById(R.id.tv_ssn_itin_not_available);

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
        mBirthdayField.setOnClickListener(this);
        mSocialSecurityField.setOnClickListener(this);
        mSocialSecurityAvailableCheck.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.btn_birthday || id == R.id.et_birthday) {
            mListener.birthdayClickHandler();
        }
        else if (id == R.id.cb_ssn_itin_not_available) {
            mListener.ssnCheckBoxClickHandler();
        }
        else {
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

    public String getBirthday() {
        return mBirthdayField.getText().toString();
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

    private String getMaskedSSN() {
        final char DOT = '\u2022';
        char[] mask = new char[getResources().getInteger(R.integer.ssn_length)];
        Arrays.fill(mask, DOT);
        return new String(mask);
    }

    public void setMaskedSSN() {
        setSSN(getMaskedSSN());
    }

    public boolean isSSNMasked() {
        return getMaskedSSN().equals(getSocialSecurityNumber());
    }

    public void showSSN(boolean show) {
        if(show) {
            mSocialSecurityField.setVisibility(VISIBLE);
        }
        else {
            mSocialSecurityField.setVisibility(GONE);
        }
    }

    public void showSSNNotAvailableCheckbox(boolean show) {
        if(show) {
            mSocialSecurityAvailableCheck.setVisibility(VISIBLE);
            mSocialSecurityAvailableField.setVisibility(VISIBLE);
        }
        else {
            mSocialSecurityAvailableCheck.setVisibility(GONE);
            mSocialSecurityAvailableField.setVisibility(GONE);
        }
    }

    public boolean isSSNCheckboxChecked() {
        return mSocialSecurityAvailableCheck.isChecked();
    }

    public void enableSSNField(boolean enabled) {
        mSocialSecurityField.setEnabled(enabled);
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
        mDisclaimersHeader.setVisibility(VISIBLE);
        mDisclaimersField.setText(Html.fromHtml(disclaimers));
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
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

    public void showBirthday(boolean show) {
        if(show) {
            mBirthdayButton.setVisibility(VISIBLE);
            mBirthdayField.setVisibility(VISIBLE);
        }
        else {
            mBirthdayButton.setVisibility(GONE);
            mBirthdayField.setVisibility(GONE);
        }
    }
}
