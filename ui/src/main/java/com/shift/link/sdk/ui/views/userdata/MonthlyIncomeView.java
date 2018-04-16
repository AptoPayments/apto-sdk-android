package com.shift.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.views.ViewWithToolbar;
import com.shift.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * TODO: Class documentation.
 * TODO: Most is copied from {@link AnnualIncomeView}.
 * @author Wijnand
 */
public class MonthlyIncomeView
        extends UserDataView<MonthlyIncomeView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener
            extends StepperListener, NextButtonListener, DiscreteSeekBar.OnProgressChangeListener { }

    private TextView mIncomeText;
    private DiscreteSeekBar mIncomeSlider;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public MonthlyIncomeView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public MonthlyIncomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mIncomeText = (TextView) findViewById(R.id.tv_income);
        mIncomeSlider = (DiscreteSeekBar) findViewById(R.id.dsb_income);
    }

    @Override
    public void setColors() {
        super.setColors();

        int color = UIStorage.getInstance().getPrimaryColor();
        mIncomeText.setTextColor(color);
        mIncomeSlider.setRippleColor(color);
        mIncomeSlider.setScrubberColor(color);
        mIncomeSlider.setTrackColor(color);
        mIncomeSlider.setThumbColor(color, color);
    }

    /** {@inheritDoc} */
    @Override
    public void setListener(ViewListener listener) {
        super.setListener(listener);
        mIncomeSlider.setOnProgressChangeListener(listener);
    }

    /**
     * Stores new minimum and maximum income amounts.
     * @param min Minimum.
     * @param max Maximum.
     */
    public void setMinMax(int min, int max) {
        mIncomeSlider.setMin(min);
        mIncomeSlider.setMax(max);
    }

    public void setSeekBarTransformer(DiscreteSeekBar.NumericTransformer transformer) {
        mIncomeSlider.setNumericTransformer(transformer);
    }

    /**
     * Shows a new income.
     * @param income New income.
     */
    public void setIncome(double income) {
        mIncomeSlider.setProgress((int)income);
    }

    /**
     * @return Income in thousands of US dollars.
     */
    public int getIncome() {
        return mIncomeSlider.getProgress();
    }

    /**
     * Updates the income text field.
     * @param text The new text.
     */
    public void updateIncomeText(String text) {
        mIncomeText.setText(text);
    }
}
