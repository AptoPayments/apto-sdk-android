package us.ledge.line.sdk.sdk.models.userdata;

import android.text.TextUtils;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.userdata.AddressActivity;
import us.ledge.line.sdk.sdk.activities.userdata.LoanAmountActivity;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationModel implements UserDataModel {

    private static final long DEFAULT_PHONE_NUMBER = -1;
    private static final long EXPECTED_PHONE_LENGTH = 10;

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private long mPhone;

    /**
     * Creates a new {@link PersonalInformationModel} instance.
     */
    public PersonalInformationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mFirstName = null;
        mLastName = null;
        mEmail = null;
        mPhone = DEFAULT_PHONE_NUMBER;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.personal_info_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return LoanAmountActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return AddressActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasFirstName() && hasLastName() && hasEmail() && hasPhone();
    }

    /**
     * Stores a valid first name.
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        if (TextUtils.isEmpty(firstName)) {
            mFirstName = null;
        } else {
            mFirstName = firstName;
        }
    }

    /**
     * Stores a valid last name.
     * @param lastName Last name.
     */
    public void setLastName(String lastName) {
        if (TextUtils.isEmpty(lastName)) {
            mLastName = null;
        } else {
            mLastName = lastName;
        }
    }

    /**
     * Stores a valid email address.
     * @param email Email address.
     */
    public void setEmail(String email) {
        if (TextUtils.isEmpty(email) || !email.contains("@") || !email.contains(".")
                || email.indexOf("@") != email.lastIndexOf("@")
                || email.indexOf("@") > email.lastIndexOf(".")
                || email.lastIndexOf(".") == email.length() - 1) {

            mEmail = null;
        } else {
            mEmail = email;
        }
    }

    /**
     * Parses and stores a valid phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != EXPECTED_PHONE_LENGTH) {
            mPhone = DEFAULT_PHONE_NUMBER;
            return;
        }

        try {
            mPhone = Long.parseLong(phone);
        } catch (NumberFormatException nfe) {
            mPhone = DEFAULT_PHONE_NUMBER;
        }
    }

    /**
     * @return Whether a first name has been set.
     */
    public boolean hasFirstName() {
        return mFirstName != null;
    }

    /**
     * @return Whether a last name has been set.
     */
    public boolean hasLastName() {
        return mLastName != null;
    }

    /**
     * @return Whether an email address has been set.
     */
    public boolean hasEmail() {
        return mEmail != null;
    }

    /**
     * @return Whether a phone number has been set.
     */
    public boolean hasPhone() {
        return mPhone > 0;
    }
}
