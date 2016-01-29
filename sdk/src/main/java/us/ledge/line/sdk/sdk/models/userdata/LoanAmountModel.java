package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.userdata.PersonalInformationActivity;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountModel implements UserDataModel {

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
    public Class getPreviousActivity() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return PersonalInformationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAmount();
    }

    /**
     * Stores the loan amount.
     * @param amount The loan amount.
     */
    public void setAmount(int amount) {
        mAmount = amount;
    }

    /**
     * @return Whether a valid loan amount has been set.
     */
    public boolean hasValidAmount() {
        return mAmount >= MINIMUM_AMOUNT;
    }
}
