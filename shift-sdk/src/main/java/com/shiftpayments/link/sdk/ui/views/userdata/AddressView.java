package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the address screen.
 * @author Wijnand
 */
public class AddressView
        extends UserDataView<AddressView.ViewListener>
        implements ViewWithToolbar {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
    }

    private TextView mAddressHeader;
    private EditText mApartmentField;
    private TextInputLayout mApartmentWrapper;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mAddressHeader = findViewById(R.id.tv_address_header);
        mApartmentField = findViewById(R.id.et_apartment_number);
        mApartmentWrapper = findViewById(R.id.til_apartment_number);
        // remove hint from `TextInputLayout`
        mApartmentWrapper.setHint(null);
        // set the hint back on the `EditText`
        mApartmentField.setHint(R.string.address_apartment_number_hint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mApartmentField.requestFocus();
    }

    /**
     * Shows the address.
     * @param address New address.
     */
    public void setAddress(String address) {
        mAddressHeader.setText(address);
    }

    /**
     * @return Apartment or unit number.
     */
    public String getApartment() {
        return mApartmentField.getText().toString();
    }

    @Override
    protected void setColors() {
        super.setColors();
        Integer textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        Integer textTertiaryColor = UIStorage.getInstance().getTextTertiaryColor();
        mApartmentField.setTextColor(textSecondaryColor);
        mApartmentField.setHintTextColor(textTertiaryColor);
        UIStorage.getInstance().setCursorColor(mApartmentField);
    }
}
