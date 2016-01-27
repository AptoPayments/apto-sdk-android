package us.ledge.line.sdk.sdk.models.userdata;

import android.text.TextUtils;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.ActivityModel;
import us.ledge.line.sdk.sdk.models.Model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationModel implements UserDataModel, ActivityModel, Model {

    private static final int EXPECTED_SSN_LENGTH = 10;

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
        if(TextUtils.isEmpty(ssn) || !TextUtils.isDigitsOnly(ssn) || ssn.length() < EXPECTED_SSN_LENGTH) {
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
