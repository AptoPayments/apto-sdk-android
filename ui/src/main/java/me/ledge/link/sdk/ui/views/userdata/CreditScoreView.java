package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;

/**
 * Displays the credit score screen.
 * @author wijnand
 */
public class CreditScoreView extends UserDataView<NextButtonListener> {

    private RadioGroup mRadioGroup;
    private TextView mErrorText;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public CreditScoreView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public CreditScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_credit_score);
        mErrorText = (TextView) findViewById(R.id.tv_credit_score_error);
        showError(false);
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
