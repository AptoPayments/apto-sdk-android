package com.shiftpayments.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.details.LoanApplicationDetailsModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays loan application details.
 * @author Wijnand
 */
public class LoanApplicationDetailsView extends ScrollView implements View.OnClickListener {

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the big button has been pressed.
         * @param action The action to take.
         */
        void bigButtonClickHandler(int action);
    }

    private ImageView mLenderImage;
    private TextView mLenderNameField;
    private TextView mApplicationStateField;
    private TextView mInterestRateField;
    private TextView mTotalAmountField;
    private TextView mPaymentField;
    private TextView mDurationField;
    private TextView mActionButton;

    private ViewListener mListener;
    private LoanApplicationDetailsModel mData;

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
        mActionButton.setOnClickListener(this);
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

        mActionButton.setVisibility(GONE);
        if (data.getBigButtonModel().isVisible()) {
            mActionButton.setVisibility(VISIBLE);
            mActionButton.setText(data.getBigButtonModel().getLabelResource());
            mActionButton.setTextColor(UIStorage.getInstance().getPrimaryContrastColor());
            mActionButton.setBackgroundColor(UIStorage.getInstance().getPrimaryColor());
        }
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
        mActionButton.setText("");
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener != null && view.getId() == R.id.tv_bttn_action) {
            mListener.bigButtonClickHandler(mData.getBigButtonModel().getAction());
        }
    }

    /**
     * Displays the latest data.
     */
    public void setData(LoanApplicationDetailsModel data) {
        mData = data;

        if (data == null) {
            resetView();
        } else {
            populateView(data);
        }
    }

    /**
     * Stores a new callback listener that this View will invoke.
     * @param listener New callback listener.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

}
