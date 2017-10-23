package me.ledge.link.sdk.ui.views.link;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;
import me.ledge.link.sdk.ui.views.userdata.UserDataView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountView
        extends UserDataView<LoanAmountView.ViewListener>
        implements ViewWithToolbar, ViewWithIndeterminateLoading, View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener
            extends StepperListener, NextButtonListener, DiscreteSeekBar.OnProgressChangeListener {
    }

    private LoadingView mLoadingView;
    private TextView mAmountText;
    private DiscreteSeekBar mAmountSlider;

    private Spinner mPurposeSpinner;
    private TextView mPurposeErrorField;

    private TextView mDisclaimersHeader;
    private TextView mDisclaimersField;

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

        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
        mAmountText = (TextView) findViewById(R.id.tv_loan_amount);
        mAmountSlider = (DiscreteSeekBar) findViewById(R.id.dsb_loan_amount);
        mPurposeSpinner = (Spinner) findViewById(R.id.sp_loan_purpose);
        mPurposeErrorField = (TextView) findViewById(R.id.tv_loan_purpose_error);
        mDisclaimersHeader = (TextView) findViewById(R.id.tv_disclaimers_header);
        mDisclaimersField = (TextView) findViewById(R.id.tv_disclaimers_body);
        mDisclaimersField.setMovementMethod(LinkMovementMethod.getInstance());

        setColors(UIStorage.getInstance().getPrimaryColor());
        updatePurposeError(false);
        setToolbarIcon();
    }

    private void setToolbarIcon() {
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.ic_action_update_profile);
        getToolbar().setOverflowIcon(drawable);
    }

    private void setColors(int color) {
        mAmountText.setTextColor(color);
        mAmountSlider.setRippleColor(color);
        mAmountSlider.setScrubberColor(color);
        mAmountSlider.setTrackColor(color);
        mAmountSlider.setThumbColor(color, color);
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
    public void setPurposeAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
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
    public IdDescriptionPairDisplayVo getPurpose() {
        return (IdDescriptionPairDisplayVo) mPurposeSpinner.getSelectedItem();
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

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void showLoanAmount(boolean show) {
        if(show) {
            mAmountText.setVisibility(VISIBLE);
            mAmountSlider.setVisibility(VISIBLE);
        }
        else {
            mAmountText.setVisibility(GONE);
            mAmountSlider.setVisibility(GONE);
        }
    }

    public void showLoanPurpose(boolean show) {
        if(show) {
            mPurposeSpinner.setVisibility(VISIBLE);
        }
        else {
            mPurposeSpinner.setVisibility(GONE);
            updatePurposeError(false);
        }
    }

    public void showGetOffersButtonAndDisclaimers(boolean show) {
        if(show) {
            mNextButton.setVisibility(VISIBLE);
            mStepper.setVisibility(GONE);
        }
        else {
            mNextButton.setVisibility(GONE);
            mDisclaimersHeader.setVisibility(GONE);
            mDisclaimersField.setVisibility(GONE);
            getToolbar().setOverflowIcon(null);
        }
    }

    public void setDisclaimers(String disclaimers) {
        mDisclaimersHeader.setVisibility(VISIBLE);
        mDisclaimersField.setVisibility(VISIBLE);
        mDisclaimersField.setText(Html.fromHtml(disclaimers));
    }
}