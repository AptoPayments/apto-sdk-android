package me.ledge.link.sdk.sdk.models.userdata;

import android.text.TextUtils;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.userdata.AddressActivity;
import me.ledge.link.sdk.sdk.activities.userdata.LoanAmountActivity;
import me.ledge.link.sdk.sdk.models.Model;
import me.ledge.link.sdk.sdk.vos.UserDataVo;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationModel extends AbstractUserDataModel implements UserDataModel {

    private static final long DEFAULT_PHONE_NUMBER = -1;

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private long mPhone;
    private PhoneNumberUtil mPhoneUtil;

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
        mPhoneUtil = PhoneNumberUtil.getInstance();
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

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.firstName = mFirstName;
        base.lastName = mLastName;
        base.emailAddress = mEmail;
        base.phoneNumber = mPhone;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);

        setFirstName(base.firstName);
        setLastName(base.lastName);
        setEmail(base.emailAddress);

        if (base.phoneNumber > 0) {
            mPhone = base.phoneNumber;
        }
    }

    /**
     * @return First name.
     */
    public String getFirstName() {
        return mFirstName;
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
     * @return Last name.
     */
    public String getLastName() {
        return mLastName;
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
     * @return Email address.
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Stores a valid email address.
     * @param email Email address.
     */
    public void setEmail(String email) {
        VerbalExpression emailRegex = VerbalExpression.regex()
                .startOfLine()
                .anythingBut("@").atLeast(1)
                .then("@")
                .anythingBut("@").atLeast(1)
                .endOfLine()
                .build();

        if (emailRegex.testExact(email)) {
            mEmail = email;
        } else {
            mEmail = null;
        }
    }

    /**
     * @return Phone number.
     */
    public long getPhone() {
        return mPhone;
    }

    /**
     * Parses and stores a valid phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone) {
        mPhone = DEFAULT_PHONE_NUMBER;

        try {
            PhoneNumber number = mPhoneUtil.parse(phone, "US");
            if (mPhoneUtil.isValidNumber(number)) {
                mPhone = number.getNationalNumber();
            }
        } catch (NumberParseException npe) {
            // Do nothing.
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
