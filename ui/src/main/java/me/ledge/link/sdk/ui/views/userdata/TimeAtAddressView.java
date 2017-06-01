package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import me.ledge.link.api.vos.responses.config.TimeAtAddressVo;

import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressView extends UserDataView<TimeAtAddressView.ViewListener> {

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
    public TimeAtAddressView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public TimeAtAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_time_at_address);
        mErrorText = (TextView) findViewById(R.id.tv_time_at_address_error);
        showError(false);
    }

    @Override
    public void setColors() {
        super.setColors();

        int color = UIStorage.getInstance().getPrimaryColor();
        for(int i=0; i<mRadioGroup.getChildCount(); i++) {
            View v = mRadioGroup.getChildAt(i);
            if(v instanceof AppCompatRadioButton) {
                ((AppCompatRadioButton) v).setSupportButtonTintList(ColorStateList.valueOf(color));
            }
        }
    }

    public void makeRadioButtons(TimeAtAddressVo[] typesList) {
        float density = getResources().getDisplayMetrics().density;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int margin = (int)(8*density);
        params.setMargins(0, 0, 0, margin);

        for (TimeAtAddressVo type : typesList) {
            AppCompatRadioButton rb = new AppCompatRadioButton(getContext());
            rb.setText(type.description);
            rb.setId(type.timeAtAddressId);
            rb.setPadding(margin, 0, 0, 0);
            mRadioGroup.addView(rb, params);
        }
    }

    /**
     * @return The ID of the selected radio button OR -1 when none is selected.
     */
    public int getScoreRangeId() {
        return mRadioGroup.getCheckedRadioButtonId();
    }

    /**
     * Selects a new score range.
     * @param id The ID of the radio button to select.
     */
    public void setScoreRangeId(int id) {
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
