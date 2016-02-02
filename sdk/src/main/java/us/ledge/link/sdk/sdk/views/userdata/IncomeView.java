package us.ledge.link.sdk.sdk.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the income screen.
 * @author Wijnand
 */
public class IncomeView
        extends UserDataView<IncomeView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends NextButtonListener, DiscreteSeekBar.OnProgressChangeListener { }

    private TextView mIncomeText;
    private DiscreteSeekBar mIncomeSlider;

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

        mIncomeText = (TextView) findViewById(R.id.tv_income);
        mIncomeSlider = (DiscreteSeekBar) findViewById(R.id.dsb_income);
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
}
