package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.userdata.AddressActivity;
import us.ledge.line.sdk.sdk.activities.userdata.IdentityVerificationActivity;
import us.ledge.line.sdk.sdk.models.Model;
import us.ledge.line.sdk.sdk.vos.UserDataVo;

/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class IncomeModel extends AbstractUserDataModel implements UserDataModel {

    private int mIncome;

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.income_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return AddressActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return IdentityVerificationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.income = mIncome;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);

        // TODO: Validation checks.
        mIncome = base.income;
    }
}
