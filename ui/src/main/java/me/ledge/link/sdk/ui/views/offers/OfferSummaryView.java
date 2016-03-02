package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;
import me.ledge.common.views.RowView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;

/**
 * Displays an offer summary.
 * @author wijnand
 */
public class OfferSummaryView extends CardView implements RowView<OfferSummaryModel> {

    private TextView mLenderNameField;
    private TextView mInterestField;
    private TextView mAmountField;
    private TextView mMonthlyPaymentField;

    private OfferSummaryModel mData;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public OfferSummaryView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public OfferSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     * @param defStyleAttr See {@link CardView#CardView}.
     */
    public OfferSummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Locates all child Views.
     */
    private void findAllViews() {
        mLenderNameField = (TextView) findViewById(R.id.tv_lender);
        mInterestField = (TextView) findViewById(R.id.tv_interest);
        mAmountField = (TextView) findViewById(R.id.tv_amount);
        mMonthlyPaymentField = (TextView) findViewById(R.id.tv_monthly);
    }

    /**
     * Updates all fields with the offer data OR empty Strings when no data is set.
     */
    private void updateAllFields() {
        if (mData == null) {
            mLenderNameField.setText("");
            mInterestField.setText("");
            mAmountField.setText("");
            mMonthlyPaymentField.setText("");
        } else {
            mLenderNameField.setText(mData.getLenderName());
            mInterestField.setText(mData.getInterestText());
            mAmountField.setText(mData.getAmountText());
            mMonthlyPaymentField.setText(mData.getMonthlyPaymentText());
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /** {@inheritDoc} */
    @Override
    public void setData(OfferSummaryModel data) {
        mData = data;
        updateAllFields();
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {
        mData = null;
        updateAllFields();
    }
}
