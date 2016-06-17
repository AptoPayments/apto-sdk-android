package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the income screen.
 * @author Wijnand
 */
public class AnnualIncomeView
        extends UserDataView<AnnualIncomeView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener, ViewWithIndeterminateLoading {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener
            extends StepperListener, NextButtonListener, DiscreteSeekBar.OnProgressChangeListener { }

    private LoadingView mLoadingView;
    private TextView mIncomeText;
    private DiscreteSeekBar mIncomeSlider;
    private Spinner mEmploymentStatusSpinner;
    private TextView mEmploymentStatusError;
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

        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
        mIncomeText = (TextView) findViewById(R.id.tv_income);
        mIncomeSlider = (DiscreteSeekBar) findViewById(R.id.dsb_income);

        mEmploymentStatusSpinner = (Spinner) findViewById(R.id.sp_employment_status);
        mEmploymentStatusError = (TextView) findViewById(R.id.tv_employment_status_error);

        mSalaryFrequencySpinner = (Spinner) findViewById(R.id.sp_salary_frequency);
        mSalaryFrequencyError = (TextView) findViewById(R.id.tv_salary_frequency_error);
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

    /**
     * Shows a new income.
     * @param income New income.
     */
    public void setIncome(int income) {
        mIncomeSlider.setProgress(income);
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


    /** {@inheritDoc} */
    @Override
    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
    }

    /**
     * @return Selected housing type.
     */
    public IdDescriptionPairDisplayVo getEmploymentStatus() {
        return (IdDescriptionPairDisplayVo) mEmploymentStatusSpinner.getSelectedItem();
    }

    /**
     * Displays a new housing type.
     * @param position Housing type index.
     */
    public void setEmploymentStatus(int position) {
        mEmploymentStatusSpinner.setSelection(position);
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the employment status {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setEmploymentStatusAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mEmploymentStatusSpinner.setAdapter(adapter);
    }

    /**
     * Updates the employment status field error display.
     * @param show Whether the error should be shown.
     */
    public void updateEmploymentStatusError(boolean show) {
        if (show) {
            mEmploymentStatusError.setVisibility(VISIBLE);
        } else {
            mEmploymentStatusError.setVisibility(GONE);
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
}
