package us.ledge.link.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.views.ViewWithToolbar;

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

    private TextView mAmountText;
    private DiscreteSeekBar mAmountSlider;

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

        mAmountText = (TextView) findViewById(R.id.tv_loan_amount);
        mAmountSlider = (DiscreteSeekBar) findViewById(R.id.dsb_loan_amount);
    }

    /** {@inheritDoc} */
    @Override
    public void setListener(ViewListener listener) {
        super.setListener(listener);
        mAmountSlider.setOnProgressChangeListener(listener);
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
}
