package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.geocoding.handlers.GooglePlacesArrayAdapter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.KeyboardUtil;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the address validation screen.
 * @author Adrian
 */
public class AddressView
        extends UserDataView<AddressView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading, AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        mListener.onAddressSelected(pos);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void onAddressSelected(int position);
    }

    private LoadingView mLoadingView;

    private TextInputLayout mAddressWrapper;
    private AutoCompleteTextView mAutoCompleteTextView;

    private Spinner mHousingTypeSpinner;
    private TextView mHousingTypeError;
    private TextView mHousingTypeHint;

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
        mAutoCompleteTextView = findViewById(R.id.actv_address);
        // remove hint from `TextInputLayout`
        mAddressWrapper.setHint(null);
        // set the hint back on the `EditText`
        mAutoCompleteTextView.setHint(R.string.address_autocomplete_hint);

        mHousingTypeSpinner = findViewById(R.id.sp_housing_type);
        mHousingTypeError = findViewById(R.id.tv_housing_type_error);
        mHousingTypeHint = findViewById(R.id.tv_housing_type_hint);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (mAutoCompleteTextView != null) {
            super.setUiFieldsObservable(mAutoCompleteTextView);
            mAutoCompleteTextView.setOnItemClickListener(this);
        }
        if(mHousingTypeSpinner != null) {
            mHousingTypeSpinner.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mAutoCompleteTextView.clearFocus();
                    KeyboardUtil.hideKeyboard(v.getContext());
                }
                return false;
            });
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAutoCompleteTextView.requestFocus();
    }

    /**
     * @return Zip or postal code.
     */
    public String getAddress() {
        return mAutoCompleteTextView.getText().toString();
    }

    /**
     * Shows the address.
     * @param address New address.
     */
    public void setAddress(String address) {
        mAutoCompleteTextView.setText(address);
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
     * Updates the housing type field error display.
     * @param show Whether the error should be shown.
     */
    public void updateHousingTypeError(boolean show) {
        if (show) {
            mHousingTypeError.setVisibility(VISIBLE);
        } else {
            mHousingTypeError.setVisibility(GONE);
        }
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * @return Selected housing type.
     */
    public IdDescriptionPairDisplayVo getHousingType() {
        return (IdDescriptionPairDisplayVo) mHousingTypeSpinner.getSelectedItem();
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setHousingTypeAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mHousingTypeSpinner.setAdapter(adapter);
    }

    /**
     * Displays a new housing type.
     * @param position Housing type index.
     */
    public void setHousingType(int position) {
        mHousingTypeSpinner.setSelection(position);
    }

    @Override
    protected void setColors() {
        super.setColors();
        mAutoCompleteTextView.setTextColor(UIStorage.getInstance().getTextSecondaryColor());
        mAutoCompleteTextView.setHintTextColor(UIStorage.getInstance().getTextTertiaryColor());
        UIStorage.getInstance().setCursorColor(mAutoCompleteTextView);
    }

    public void showHousingTypeHint(boolean show) {
        if(show) {
            mHousingTypeHint.setVisibility(VISIBLE);
            mHousingTypeSpinner.setVisibility(VISIBLE);
        }
        else {
            mHousingTypeHint.setVisibility(GONE);
            mHousingTypeSpinner.setVisibility(GONE);
            updateHousingTypeError(false);
        }
    }

    public void setAddressAdapter(GooglePlacesArrayAdapter adapter) {
        mAutoCompleteTextView.setAdapter(adapter);
    }
}
