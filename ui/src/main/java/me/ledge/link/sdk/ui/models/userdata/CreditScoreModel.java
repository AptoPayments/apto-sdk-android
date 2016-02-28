package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.utils.CreditScoreRange;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Model} for the credit score screen.
 * @author wijnand
 */
public class CreditScoreModel extends AbstractUserDataModel {

    public static final int DEFAULT_CREDIT_INDICATION = 0;

    private int mCreditScoreRange;

    /**
     * Creates a new {@link CreditScoreModel} instance.
     */
    public CreditScoreModel() {
        super();
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mCreditScoreRange = DEFAULT_CREDIT_INDICATION;
    }

    /**
     * @param indication The credit score indication to check.
     * @return Whether this is a valid credit score indication.
     */
    private boolean isValidIndication(int indication) {
        return indication >= CreditScoreRange.EXCELLENT && indication <= CreditScoreRange.POOR;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return isValidIndication(mCreditScoreRange);
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.credit_score_label;
    }

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.creditScoreRange = mCreditScoreRange;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);
        setCreditScoreRange(base.creditScoreRange);
    }

    /**
     * @return Credit score range.
     * @see CreditScoreRange
     */
    public int getCreditScoreRange() {
        return mCreditScoreRange;
    }

    /**
     * Stores a new credit score range.
     * @param indication New range.
     * @see CreditScoreRange
     */
    public void setCreditScoreRange(int indication) {
        if (isValidIndication(indication)) {
            mCreditScoreRange = indication;
        } else {
            mCreditScoreRange = DEFAULT_CREDIT_INDICATION;
        }
    }
}
