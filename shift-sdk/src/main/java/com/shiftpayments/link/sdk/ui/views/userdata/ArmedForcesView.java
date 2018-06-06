package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the member of the armed forces screen.
 * @author Adrian
 */
public class ArmedForcesView extends UserDataView<ArmedForcesView.ViewListener> {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {}

    private RadioGroup mRadioGroup;
    private TextView mErrorText;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public ArmedForcesView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public ArmedForcesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mRadioGroup = findViewById(R.id.rg_armed_forces);
        mErrorText = findViewById(R.id.tv_armed_forces_error);
        showError(false);
    }

    @Override
    public void setColors() {
        super.setColors();

        ColorStateList colorStateList = UIStorage.getInstance().getRadioButtonColors();
        for(int i=0; i<mRadioGroup.getChildCount(); i++) {
            View v = mRadioGroup.getChildAt(i);
            if(v instanceof AppCompatRadioButton) {
                ((AppCompatRadioButton) v).setSupportButtonTintList(colorStateList);
            }
        }
    }

    /**
     * @return The ID of the selected radio button OR -1 when none is selected.
     */
    public int getSelectionId() {
        return mRadioGroup.getCheckedRadioButtonId();
    }

    /**
     * Selects a new option.
     * @param id The ID of the radio button to select.
     */
    public void setSelection(int id) {
        mRadioGroup.check(id);
    }

    /**
     * Updates the error text visibility.
     * @param show Whether the error should be shown.
     */
    public void showError(boolean show) {
        if (show) {
            mErrorText.setVisibility(VISIBLE);
        } else {
            mErrorText.setVisibility(GONE);
        }
    }
}