package us.ledge.line.sdk.sdk.views;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import us.ledge.line.sdk.sdk.R;

/**
 * Displays the user input screen.
 * @author Wijnand
 */
public class UserDetailsView extends RelativeLayout implements View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {

        /**
         * Called when the birthday input field has been pressed.
         */
        void birthdayClickHandler();

        /**
         * Called when the "next" button has been pressed.
         */
        void nextClickHandler();
    }

    private ViewListener mListener;
    private Toolbar mToolbar;

    private Button mBirthdayButton;
    private TextInputLayout mBirthdayWrapper;
    private AppCompatEditText mBirthdayField;

    private TextInputLayout mSocialSecurityWrapper;
    private AppCompatEditText mSocialSecurityField;

    private TextView mNextButton;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDetailsView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        mBirthdayButton = (Button) findViewById(R.id.btn_birthday);
        mBirthdayWrapper = (TextInputLayout) findViewById(R.id.til_birthday);
        mBirthdayField = (AppCompatEditText) findViewById(R.id.et_birthday);

        mSocialSecurityWrapper = (TextInputLayout) findViewById(R.id.til_social_security);
        mSocialSecurityField = (AppCompatEditText) findViewById(R.id.et_social_security);

        mNextButton = (TextView) findViewById(R.id.tv_next_bttn);
    }

    /**
     * Sets up all required listeners.
     */
    private void setupListeners() {
        mBirthdayButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }

    /**
     * Updates an error display.
     * @param wrapper The {@link TextInputLayout} to update.
     * @param show Whether the error should be shown.
     * @param errorMessage Error message.
     */
    private void updateErrorDisplay(TextInputLayout wrapper, boolean show, String errorMessage) {
        if (show) {
            wrapper.setError(errorMessage);
        } else {
            wrapper.setError(null);
            wrapper.setErrorEnabled(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
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
        } else if (id == R.id.tv_next_bttn) {
            mListener.nextClickHandler();
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener New {@link ViewListener}.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * @return Toolbar.
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * @return Social security number.
     */
    public String getSocialSecurityNumber() {
        return mSocialSecurityField.getText().toString();
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
     * @param errorMessage Error message.
     */
    public void updateBirthdayError(boolean show, String errorMessage) {
        updateErrorDisplay(mBirthdayWrapper, show, errorMessage);
    }

    /**
     * Updates the SSN field error display.
     * @param show Whether the error should be shown.
     * @param errorMessage Error message.
     */
    public void updateSocialSecurityError(boolean show, String errorMessage) {
        updateErrorDisplay(mSocialSecurityWrapper, show, errorMessage);
    }
}
