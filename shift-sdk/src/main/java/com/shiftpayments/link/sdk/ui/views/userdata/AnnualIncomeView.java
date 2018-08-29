package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Displays the income screen.
 * @author Wijnand
 */
public class AnnualIncomeView
        extends UserDataView<AnnualIncomeView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener
            extends StepperListener, NextButtonListener, DiscreteSeekBar.OnProgressChangeListener { }

    private LoadingView mLoadingView;
    private TextView mIncomeText;
    private DiscreteSeekBar mIncomeSlider;
    private TextView mIncomeTypeHint;
    private Spinner mIncomeTypeSpinner;
    private TextView mIncomeTypeError;
    private TextView mSalaryFrequencyHint;
    private Spinner mSalaryFrequencySpinner;
    private TextView mSalaryFrequencyError;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public AnnualIncomeView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public AnnualIncomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mIncomeText = findViewById(R.id.tv_income);
        mIncomeSlider = findViewById(R.id.dsb_income);

        mIncomeTypeHint = findViewById(R.id.tv_income_type_hint);
        mIncomeTypeSpinner = findViewById(R.id.sp_income_type);
        mIncomeTypeError = findViewById(R.id.tv_income_type_error);

        mSalaryFrequencyHint = findViewById(R.id.tv_salary_frequency_hint);
        mSalaryFrequencySpinner = findViewById(R.id.sp_salary_frequency);
        mSalaryFrequencyError = findViewById(R.id.tv_salary_frequency_error);
    }

    /** {@inheritDoc} */
    @Override
    public void setListener(ViewListener listener) {
        super.setListener(listener);
        mIncomeSlider.setOnProgressChangeListener(listener);
    }

    @Override
    public void setColors() {
        super.setColors();

        int color = UIStorage.getInstance().getUiPrimaryColor();
        mIncomeText.setTextColor(color);
        mIncomeSlider.setRippleColor(color);
        mIncomeSlider.setScrubberColor(color);
        mIncomeSlider.setTrackColor(color);
        mIncomeSlider.setThumbColor(color, color);
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

    /**
     * Shows a new income.
     * @param income New income.
     */
    public void setIncome(long income) {
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


    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * @return Selected housing type.
     */
    public IdDescriptionPairDisplayVo getIncomeType() {
        return (IdDescriptionPairDisplayVo) mIncomeTypeSpinner.getSelectedItem();
    }

    /**
     * Displays a new housing type.
     * @param position Housing type index.
     */
    public void setIncomeType(int position) {
        mIncomeTypeSpinner.setSelection(position);
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the employment status {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setIncomeTypesAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mIncomeTypeSpinner.setAdapter(adapter);
    }

    /**
     * Updates the employment status field error display.
     * @param show Whether the error should be shown.
     */
    public void updateIncomeTypeError(boolean show) {
        if (show) {
            mIncomeTypeError.setVisibility(VISIBLE);
        } else {
            mIncomeTypeError.setVisibility(GONE);
        }
    }

    /**
     * @return Selected salary frequency.
     */
    public IdDescriptionPairDisplayVo getSalaryFrequency() {
        return (IdDescriptionPairDisplayVo) mSalaryFrequencySpinner.getSelectedItem();
    }

    /**
     * Displays a new salary frequency.
     * @param position salary frequency index.
     */
    public void setSalaryFrequency(int position) {
        mSalaryFrequencySpinner.setSelection(position);
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the salary frequency {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setSalaryFrequencyAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mSalaryFrequencySpinner.setAdapter(adapter);
    }

    /**
     * Updates the salary frequency field error display.
     * @param show Whether the error should be shown.
     */
    public void updateSalaryFrequencyError(boolean show) {
        if (show) {
            mSalaryFrequencyError.setVisibility(VISIBLE);
        } else {
            mSalaryFrequencyError.setVisibility(GONE);
        }
    }

    public void showEmploymentFields(boolean show) {
        if (show) {
            mIncomeTypeSpinner.setVisibility(VISIBLE);
            mIncomeTypeHint.setVisibility(VISIBLE);
            mSalaryFrequencySpinner.setVisibility(VISIBLE);
            mSalaryFrequencyHint.setVisibility(VISIBLE);
        } else {
            mIncomeTypeSpinner.setVisibility(GONE);
            mIncomeTypeHint.setVisibility(GONE);
            updateIncomeTypeError(false);
            mSalaryFrequencySpinner.setVisibility(GONE);
            mSalaryFrequencyHint.setVisibility(GONE);
            updateSalaryFrequencyError(false);
        }
    }
}
