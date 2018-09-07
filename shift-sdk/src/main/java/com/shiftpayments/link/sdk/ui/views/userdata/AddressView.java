package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import java.util.ArrayList;

/**
 * Displays the address screen.
 * @author Wijnand
 */
public class AddressView
        extends UserDataView<AddressView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void onAddressLostFocus();
    }

    private LoadingView mLoadingView;

    private TextInputLayout mAddressWrapper;
    private EditText mAddressField;

    private EditText mApartmentField;

    private TextInputLayout mCityWrapper;
    private EditText mCityField;

    private Spinner mStateSpinner;
    private TextView mStateErrorField;

    private TextInputLayout mZipWrapper;
    private EditText mZipField;
    private ArrayAdapter<CharSequence> mAdapter;

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

        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mAddressWrapper = findViewById(R.id.til_address);
        mAddressField = findViewById(R.id.et_address);

        mApartmentField = findViewById(R.id.et_apartment_number);

        mCityWrapper = findViewById(R.id.til_city);
        mCityField = findViewById(R.id.et_city);

        mStateSpinner = findViewById(R.id.sp_state);
        mStateErrorField = findViewById(R.id.tv_state_spinner_error);

        mZipWrapper = findViewById(R.id.til_zip_code);
        mZipField = findViewById(R.id.et_zip_code);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (mAddressField != null) {
            mAddressField.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    mListener.onAddressLostFocus();
                }
            });
        }
        ArrayList<EditText> mFields = new ArrayList<>();
        mFields.add(mAddressField);
        mFields.add(mCityField);
        mFields.add(mZipField);
        super.setUiFieldsObservable(mFields);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAddressField.requestFocus();
    }

    /**
     * Stores a new {@link ArrayAdapter} to use with the state {@link Spinner}.
     * @param adapter The new Adapter.
     */
    public void setStateSpinnerAdapter(ArrayAdapter<CharSequence> adapter) {
        mAdapter = adapter;
        mStateSpinner.setAdapter(adapter);
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return mAddressField.getText().toString();
    }

    /**
     * Shows the address.
     * @param address New address.
     */
    public void setAddress(String address) {
        mAddressField.setText(address);
    }

    /**
     * @return Apartment or unit number.
     */
    public String getApartment() {
        return mApartmentField.getText().toString();
    }

    /**
     * Shows the apartment or unit number.
     * @param apartment New apartment or unit number.
     */
    public void setApartment(String apartment) {
        mApartmentField.setText(apartment);
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mCityField.getText().toString();
    }

    @Override
    protected void setColors() {
        super.setColors();
        Integer textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        Integer textTertiaryColor = UIStorage.getInstance().getTextTertiaryColor();
        mAddressField.setTextColor(textSecondaryColor);
        mAddressField.setHintTextColor(textTertiaryColor);
        mApartmentField.setTextColor(textSecondaryColor);
        mApartmentField.setHintTextColor(textTertiaryColor);
        mCityField.setTextColor(textSecondaryColor);
        mCityField.setHintTextColor(textTertiaryColor);
        mZipField.setTextColor(textSecondaryColor);
        mZipField.setHintTextColor(textTertiaryColor);
        UIStorage.getInstance().setCursorColor(mAddressField);
        UIStorage.getInstance().setCursorColor(mApartmentField);
        UIStorage.getInstance().setCursorColor(mCityField);
        UIStorage.getInstance().setCursorColor(mZipField);
    }

    /**
     * Shows the city.
     * @param city New city.
     */
    public void setCity(String city) {
        mCityField.setText(city);
    }

    /**
     * @return State.
     */
    public String getState() {
        String state = null;
        int position = mStateSpinner.getSelectedItemPosition();

        if (position != Spinner.INVALID_POSITION) {
            state = mAdapter.getItem(mStateSpinner.getSelectedItemPosition()).toString();
        }

        return state;
    }

    /**
     * Shows the new state.
     * @param state New State.
     */
    public void setState(String state) {
        int position = mAdapter.getPosition(state);
        mStateSpinner.setSelection(position);
    }

    /**
     * @return Zip or postal code.
     */
    public String getZipCode() {
        return mZipField.getText().toString();
    }

    /**
     * Shows the zip or postal code.
     * @param zip New zip or postal code.
     */
    public void setZipCode(String zip) {
        mZipField.setText(zip);
    }

    /**
     * Updates the address field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateAddressError(boolean show, int errorMessageId) {
        updateErrorDisplay(mAddressWrapper, show, errorMessageId);
    }

    /**
     * Updates the city field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateCityError(boolean show, int errorMessageId) {
        updateErrorDisplay(mCityWrapper, show, errorMessageId);
    }

    /**
     * Updates the state field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateStateError(boolean show, int errorMessageId) {
        if (show) {
            mStateErrorField.setText(errorMessageId);
            mStateErrorField.setVisibility(VISIBLE);
        } else {
            mStateErrorField.setVisibility(GONE);
        }
    }

    /**
     * Updates the ZIP field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateZipError(boolean show, int errorMessageId) {
        updateErrorDisplay(mZipWrapper, show, errorMessageId);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }
}
