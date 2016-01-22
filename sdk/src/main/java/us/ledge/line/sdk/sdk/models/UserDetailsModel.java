package us.ledge.line.sdk.sdk.models;

import android.content.Context;
import android.text.TextUtils;
import us.ledge.line.sdk.sdk.R;

import java.util.Date;

/**
 * Concrete {@link Model} for the user details screen.
 * @author Wijnand
 */
public class UserDetailsModel implements Model {

    private static final int EXPECTED_SSN_LENGTH = 10;

    private final Context mContext;

    private Date mBirthday;
    private long mSocialSecurityNumber;

    /**
     * Creates a new {@link UserDetailsModel} instance.
     * @param context Context used to fetch resources.
     */
    public UserDetailsModel(Context context) {
        mContext = context;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mBirthday = null;
        mSocialSecurityNumber = -1;
    }

    /**
     * Tries to store the birthday based on a raw String.
     * @param birthday Raw birthday.
     */
    public void setBirthday(String birthday) {
        // TODO
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
    public String getBirthdayErrorString() {
        return mContext.getString(R.string.user_details_birthday_error);
    }

    /**
     * @return Error message to show when an incorrect SSN has been entered.
     */
    public String getSsnErrorString() {
        return mContext.getString(R.string.user_details_social_security_error);
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
