package us.ledge.line.sdk.sdk.models.userdata;

import android.text.TextUtils;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.ActivityModel;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountModel implements ActivityModel, UserDataModel, Model {

    private static final int DEFAULT_AMOUNT = -1;
    private static final int MINIMUM_AMOUNT = 1;
    private int mAmount;

    /**
     * Creates a new {@link LoanAmountModel} instance.
     */
    public LoanAmountModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mAmount = DEFAULT_AMOUNT;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_amount_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAmount();
    }

    /**
     * Tries to store the loan amount based on a raw String.
     * @param rawAmount The raw amount.
     */
    public void setAmount(String rawAmount) {
        if (TextUtils.isEmpty(rawAmount) || !TextUtils.isDigitsOnly(rawAmount)) {
            mAmount = DEFAULT_AMOUNT;
            return;
        }

        try {
            mAmount = Integer.parseInt(rawAmount);
        } catch (NumberFormatException nfe) {
            mAmount = DEFAULT_AMOUNT;
        }
    }

    /**
     * @return Whether a valid loan amount has been set.
     */
    public boolean hasValidAmount() {
        return mAmount >= MINIMUM_AMOUNT;
    }
}
