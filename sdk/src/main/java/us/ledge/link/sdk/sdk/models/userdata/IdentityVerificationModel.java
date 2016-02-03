package us.ledge.link.sdk.sdk.models.userdata;

import ru.lanwen.verbalregex.VerbalExpression;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.activities.userdata.IncomeActivity;
import us.ledge.link.sdk.sdk.models.Model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private static final int EXPECTED_SSN_LENGTH = 9; // TODO: Move to values/ints.xml?

    private Date mBirthday;
    private long mSocialSecurityNumber;

    /**
     * Creates a new {@link IdentityVerificationModel} instance.
     */
    public IdentityVerificationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mBirthday = null;
        mSocialSecurityNumber = -1;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.id_verification_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return IncomeActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidBirthday() && hasValidSsn();
    }

    /**
     * Stores the birthday.
     * @param year Year of birth.
     * @param monthOfYear Month of birth.
     * @param dayOfMonth Day of birth.
     */
    public void setBirthday(int year, int monthOfYear, int dayOfMonth) {
        try {
            mBirthday = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
        } catch (IllegalArgumentException iae) {
            mBirthday = null;
        }
    }

    /**
     * Tries to store the SSN based on a raw String.
     * @param ssn Raw social security number.
     */
    public void setSocialSecurityNumber(String ssn) {
        VerbalExpression ssnRegex = VerbalExpression.regex()
                .startOfLine()
                .digit().count(EXPECTED_SSN_LENGTH)
                .endOfLine()
                .build();

        if(!ssnRegex.testExact(ssn)) {
            mSocialSecurityNumber = -1;
            return;
        }

        try {
            mSocialSecurityNumber = Long.parseLong(ssn);
        } catch (NumberFormatException nfe) {
            mSocialSecurityNumber = -1;
        }
    }

    /**
     * @return Error message to show when an incorrect birthday has been entered.
     */
    public int getBirthdayErrorString() {
        return R.string.id_verification_birthday_error;
    }

    /**
     * @return Error message to show when an incorrect SSN has been entered.
     */
    public int getSsnErrorString() {
        return R.string.id_verification_social_security_error;
    }

    /**
     * @return Whether a valid birthday has been stored.
     */
    public boolean hasValidBirthday() {
        return mBirthday != null;
    }

    /**
     * @return Whether a valid SSN has been stored.
     */
    public boolean hasValidSsn() {
        return mSocialSecurityNumber > 0;
    }
}
