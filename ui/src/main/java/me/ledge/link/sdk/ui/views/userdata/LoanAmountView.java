package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import me.ledge.link.sdk.ui.vos.LoanPurposeDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountView
        extends UserDataView<LoanAmountView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends NextButtonListener, DiscreteSeekBar.OnProgressChangeListener { }

    private RelativeLayout mLoadingOverlay;
    private TextView mAmountText;
    private DiscreteSeekBar mAmountSlider;

    private Spinner mPurposeSpinner;
    private TextView mPurposeErrorField;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public LoanAmountView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public LoanAmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mLoadingOverlay = (RelativeLayout) findViewById(R.id.rl_loading_overlay);
        mAmountText = (TextView) findViewById(R.id.tv_loan_amount);
        mAmountSlider = (DiscreteSeekBar) findViewById(R.id.dsb_loan_amount);
        mPurposeSpinner = (Spinner) findViewById(R.id.sp_loan_purpose);
        mPurposeErrorField = (TextView) findViewById(R.id.tv_loan_purpose_error);

        showLoading(false);
        updatePurposeError(false);
    }

    /** {@inheritDoc} */
    @Override
    public void setListener(ViewListener listener) {
        super.setListener(listener);
        mAmountSlider.setOnProgressChangeListener(listener);
    }

    /**
     * Sets a new {@link DiscreteSeekBar#NumericTransformer} on the {@link DiscreteSeekBar}.
     * @param transformer The new transformer.
     */
    public void setSeekBarTransformer(DiscreteSeekBar.NumericTransformer transformer) {
        mAmountSlider.setNumericTransformer(transformer);
    }

    /**
     * Stores new minimum and maximum loan amounts.
     * @param min Minimum.
     * @param max Maximum.
     */
    public void setMinMax(int min, int max) {
        mAmountSlider.setMin(min);
        mAmountSlider.setMax(max);
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setPurposeAdapter(HintArrayAdapter<LoanPurposeDisplayVo> adapter) {
        mPurposeSpinner.setAdapter(adapter);
    }

    /**
     * Shows a new loan amount.
     * @param amount New loan amount.
     */
    public void setAmount(int amount) {
        mAmountSlider.setProgress(amount);
    }

    /**
     * @return Loan amount.
     */
    public int getAmount() {
        return mAmountSlider.getProgress();
    }

    /**
     * Updates the amount text field.
     * @param text The new text.
     */
    public void updateAmountText(String text) {
        mAmountText.setText(text);
    }

    /**
     * @return The selected loan purpose.
     */
    public LoanPurposeDisplayVo getPurpose() {
        return (LoanPurposeDisplayVo) mPurposeSpinner.getSelectedItem();
    }

    /**
     * Sets a new loan purpose.
     * @param index Loan purpose index.
     */
    public void setPurpose(int index) {
        mPurposeSpinner.setSelection(index);
    }

    /**
     * Updates the loan purpose error field visibility.
     * @param show Whether the error should be shown.
     */
    public void updatePurposeError(boolean show) {
        if (show) {
            mPurposeErrorField.setVisibility(VISIBLE);
        } else {
            mPurposeErrorField.setVisibility(GONE);
        }
    }

    /**
     * Changes the loading overlay visibility.
     * @param show Whether the loading overlay should be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingOverlay.setVisibility(VISIBLE);
        } else {
            mLoadingOverlay.setVisibility(GONE);
        }
    }
}
