package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import java.util.ArrayList;

/**
 * Displays the personal information confirmation screen.
 * @author Adrian
 */
public class PersonalInformationConfirmationView
        extends UserDataView<PersonalInformationConfirmationView.ViewListener>
        implements ViewWithToolbar {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
    }

    private TextView mFirstNameField;
    private TextView mFirstNameLabel;
    private TextView mLastNameField;
    private TextView mLastNameLabel;
    private TextView mEmailField;
    private TextView mEmailLabel;
    private TextView mAddressField;
    private TextView mAddressLabel;
    private TextView mPhoneField;
    private TextView mPhoneLabel;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public PersonalInformationConfirmationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public PersonalInformationConfirmationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mFirstNameField = findViewById(R.id.tv_first_name);
        mFirstNameLabel = findViewById(R.id.tv_first_name_label);
        mLastNameField = findViewById(R.id.tv_last_name);
        mLastNameLabel = findViewById(R.id.tv_last_name_label);
        mEmailField = findViewById(R.id.tv_email);
        mEmailLabel = findViewById(R.id.tv_email_label);
        mAddressField = findViewById(R.id.tv_address);
        mAddressLabel = findViewById(R.id.tv_address_label);
        mPhoneField = findViewById(R.id.tv_phone);
        mPhoneLabel = findViewById(R.id.tv_phone_label);
    }

    @Override
    protected void setColors() {
        super.setColors();
        Integer textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        mFirstNameField.setTextColor(textSecondaryColor);
        mLastNameField.setTextColor(textSecondaryColor);
        mEmailField.setTextColor(textSecondaryColor);
        mAddressField.setTextColor(textSecondaryColor);
        mPhoneField.setTextColor(textSecondaryColor);

    }

    public void setFirstName(String firstName) {
        mFirstNameField.setText(firstName);
        showName(true);
    }

    public void setLastName(String lastName) {
        mLastNameField.setText(lastName);
        showName(true);
    }

    public void setEmail(String email) {
        mEmailField.setText(email);
        showEmail(true);
    }

    public void setAddress(String address) {
        mAddressField.setText(address);
        showAddress(true);
    }

    public void setPhone(String phone) {
        mPhoneField.setText(phone);
        showPhone(true);
    }

    public void showName(boolean show) {
        if(show) {
            mFirstNameField.setVisibility(VISIBLE);
            mFirstNameLabel.setVisibility(VISIBLE);
            mLastNameField.setVisibility(VISIBLE);
            mLastNameLabel.setVisibility(VISIBLE);
        }
        else {
            mFirstNameField.setVisibility(GONE);
            mFirstNameLabel.setVisibility(GONE);
            mLastNameField.setVisibility(GONE);
            mLastNameLabel.setVisibility(GONE);
        }
    }

    public void showEmail(boolean show) {
        if(show) {
            mEmailField.setVisibility(VISIBLE);
            mEmailLabel.setVisibility(VISIBLE);
        }
        else {
            mEmailField.setVisibility(GONE);
            mEmailLabel.setVisibility(GONE);
        }
    }

    public void showAddress(boolean show) {
        if(show) {
            mAddressField.setVisibility(VISIBLE);
            mAddressLabel.setVisibility(VISIBLE);
        }
        else {
            mAddressField.setVisibility(GONE);
            mAddressLabel.setVisibility(VISIBLE);
        }
    }

    public void showPhone(boolean show) {
        if(show) {
            mPhoneField.setVisibility(VISIBLE);
            mPhoneLabel.setVisibility(VISIBLE);
        }
        else {
            mPhoneField.setVisibility(GONE);
            mPhoneLabel.setVisibility(GONE);
        }
    }
}
