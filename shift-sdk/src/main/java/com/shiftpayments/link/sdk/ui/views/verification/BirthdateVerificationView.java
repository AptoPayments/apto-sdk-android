package com.shiftpayments.link.sdk.ui.views.verification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.userdata.NextButtonListener;
import com.shiftpayments.link.sdk.ui.views.userdata.UserDataView;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import java.util.ArrayList;

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
        void monthClickHandler();
    }

    private EditText mBirthdayMonth;
    private EditText mBirthdayDay;
    private EditText mBirthdayYear;
    private TextView mBirthdateErrorView;
    private LoadingView mLoadingView;
    ArrayList<EditText> mFields;

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
        mFields = new ArrayList<>();
        mFields.add(mBirthdayMonth);
        mFields.add(mBirthdayDay);
        mFields.add(mBirthdayYear);
        super.setUiFieldsObservable(mFields);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mBirthdayMonth = findViewById(R.id.et_birthday_month);
        mBirthdayDay = findViewById(R.id.et_birthday_day);
        mBirthdayYear = findViewById(R.id.et_birthday_year);
        mBirthdateErrorView = findViewById(R.id.tv_birthdate_error);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mBirthdayMonth.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.et_birthday_month) {
            mListener.monthClickHandler();
        }
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    @Override
    protected void setColors() {
        super.setColors();

        Integer textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        Integer textTertiaryColor = UIStorage.getInstance().getTextTertiaryColor();
        mBirthdayMonth.setTextColor(textSecondaryColor);
        mBirthdayMonth.setHintTextColor(textTertiaryColor);
        mBirthdayDay.setTextColor(textSecondaryColor);
        mBirthdayDay.setHintTextColor(textTertiaryColor);
        mBirthdayYear.setTextColor(textSecondaryColor);
        mBirthdayYear.setHintTextColor(textTertiaryColor);
        UIStorage.getInstance().setCursorColor(mBirthdayDay);
        UIStorage.getInstance().setCursorColor(mBirthdayYear);
    }

    /**
     * @return The selected month.
     */
    public String getBirthdayMonth() {
        return mBirthdayMonth.getText().toString();
    }

    /**
     * Sets a new month.
     * @param month Month.
     */
    public void setBirthdayMonth(String month) {
        mBirthdayMonth.setText(month);
    }

    public String getBirthdayDay() {
        return mBirthdayDay.getText().toString();
    }

    public String getBirthdayYear() {
        return mBirthdayYear.getText().toString();
    }

    /**
     * Updates the birthday field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateBirthdateError(boolean show, int errorMessageId) {
        if(show) {
            String error = getResources().getString(errorMessageId);
            mBirthdateErrorView.setText(error);
            mBirthdateErrorView.setVisibility(VISIBLE);
        }
        else {
            mBirthdateErrorView.setVisibility(GONE);
        }
    }

}
