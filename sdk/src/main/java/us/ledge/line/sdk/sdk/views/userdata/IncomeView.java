package us.ledge.line.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.Model;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class IncomeView
        extends UserDataView<IncomeView.ViewListener>
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
    public IncomeView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public IncomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mAmountText = (TextView) findViewById(R.id.tv_income);
        mAmountSlider = (DiscreteSeekBar) findViewById(R.id.dsb_income);
    }

    /** {@inheritDoc} */
    @Override
    public void setListener(ViewListener listener) {
        super.setListener(listener);
        mAmountSlider.setOnProgressChangeListener(listener);
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
