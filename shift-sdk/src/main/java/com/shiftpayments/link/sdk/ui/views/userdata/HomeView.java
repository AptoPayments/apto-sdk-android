package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.ui.R;
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
public class HomeView
        extends UserDataView<HomeView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading, View.OnClickListener {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void onZipFieldFilled();
    }

    private LoadingView mLoadingView;

    private TextInputLayout mZipWrapper;
    private EditText mZipField;

    private Spinner mHousingTypeSpinner;
    private TextView mHousingTypeError;
    private TextView mHousingTypeHint;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public HomeView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);

        mZipWrapper = (TextInputLayout) findViewById(R.id.til_zip_code);
        mZipField = (EditText) findViewById(R.id.et_zip_code);

        mHousingTypeSpinner = (Spinner) findViewById(R.id.sp_housing_type);
        mHousingTypeError = (TextView) findViewById(R.id.tv_housing_type_error);
        mHousingTypeHint = (TextView) findViewById(R.id.tv_housing_type_hint);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        if (mZipField != null) {
            mZipField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(mZipField.getText().toString().length()==getResources().getInteger(R.integer.zip_code_length_validation) && mListener!=null)
                    {
                        mListener.onZipFieldFilled();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });
        }
        if(mHousingTypeSpinner != null) {
            mHousingTypeSpinner.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mZipField.clearFocus();
                    KeyboardUtil.hideKeyboard(v.getContext());
                }
                return false;
            });
        }
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
     * Updates the ZIP field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateZipError(boolean show, int errorMessageId) {
        updateErrorDisplay(mZipWrapper, show, errorMessageId);
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
}
