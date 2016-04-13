package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.details.LoanApplicationDetailsModel;

/**
 * Displays loan application details.
 * @author Wijnand
 */
public class LoanApplicationDetailsView extends ScrollView {

    private ImageView mLenderImage;
    private TextView mLenderNameField;
    private TextView mApplicationStateField;
    private TextView mInterestRateField;
    private TextView mTotalAmountField;
    private TextView mPaymentField;
    private TextView mDurationField;
    private TextView mActionButton;

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     */
    public LoanApplicationDetailsView(Context context) {
        super(context);
    }

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs See {@link ScrollView#ScrollView}.
     */
    public LoanApplicationDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all relevant child Views.
     */
    private void findAllViews() {
        mLenderImage = (ImageView) findViewById(R.id.iv_lender_logo);
        mLenderNameField = (TextView) findViewById(R.id.tv_lender_name);
        mApplicationStateField = (TextView) findViewById(R.id.tv_application_state);
        mInterestRateField = (TextView) findViewById(R.id.tv_loan_interest_rate);
        mTotalAmountField = (TextView) findViewById(R.id.tv_loan_amount);
        mPaymentField = (TextView) findViewById(R.id.tv_payment);
        mDurationField = (TextView) findViewById(R.id.tv_loan_duration);
        mActionButton = (TextView) findViewById(R.id.tv_bttn_action);
    }

    /**
     * Sets up all callback listeners.
     */
    private void setUpListeners() {
        // TODO
    }

    /**
     * Populates this View with the latest data.
     * @param data New data.
     */
    private void populateView(LoanApplicationDetailsModel data) {
        mLenderImage.setVisibility(GONE);
        mLenderNameField.setVisibility(GONE);

        if (data.hasImageLoader() && data.getLenderImage() != null) {
            mLenderImage.setVisibility(VISIBLE);
            data.getImageLoader().load(data.getLenderImage(), mLenderImage);
        } else {
            mLenderNameField.setText(data.getLenderName());
            mLenderNameField.setVisibility(VISIBLE);
        }

        mApplicationStateField.setText(data.getStatusText());
        mApplicationStateField.setTextColor(data.getStatusColor());

        mInterestRateField.setText(data.getInterestText());
        mTotalAmountField.setText(data.getAmountText());
        mPaymentField.setText(data.getMonthlyPaymentText());
        mDurationField.setText(data.getDurationText());
    }

    /**
     * Clears this View.
     */
    private void resetView() {
        mLenderImage.setImageResource(android.R.color.transparent);
        mLenderNameField.setText("");
        mApplicationStateField.setText("");
        mInterestRateField.setText("");
        mTotalAmountField.setText("");
        mPaymentField.setText("");
        mDurationField.setText("");
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
    }

    /**
     * Displays the latest data.
     */
    public void setData(LoanApplicationDetailsModel data) {
        if (data == null) {
            resetView();
        } else {
            populateView(data);
        }
    }
}
