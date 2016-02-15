package me.ledge.link.sdk.sdk.models.userdata;

import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.sdk.sdk.vos.UserDataVo;
import ru.lanwen.verbalregex.VerbalExpression;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.userdata.IncomeActivity;
import me.ledge.link.sdk.sdk.models.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private static final int DEFAULT_SSN = -1;
    private static final int EXPECTED_SSN_LENGTH = 9; // TODO: Move to values/ints.xml?

    private int mMinimumAge;
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
        mMinimumAge = 0;
        mBirthday = null;
        mSocialSecurityNumber = DEFAULT_SSN;
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
     * Stores a new minimum age.
     * @param age New age.
     */
    public void setMinimumAge(int age) {
        mMinimumAge = age;
    }

    /**
     * @return Birthday.
     */
    public Date getBirthday() {
        return mBirthday;
    }

    /**
     * Stores the birthday.
     * @param year Year of birth.
     * @param monthOfYear Month of birth.
     * @param dayOfMonth Day of birth.
     */
    public void setBirthday(int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar birth = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            Calendar minAge = GregorianCalendar.getInstance();
            minAge.add(Calendar.YEAR, mMinimumAge * -1);

            if (birth.compareTo(minAge) < 0) {
                mBirthday = birth.getTime();
            } else {
                mBirthday = null;
            }
        } catch (IllegalArgumentException iae) {
            mBirthday = null;
        }
    }

    /**
     * @return social security Number.
     */
    public long getSocialSecurityNumber() {
        return mSocialSecurityNumber;
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
            mSocialSecurityNumber = DEFAULT_SSN;
            return;
        }

        try {
            mSocialSecurityNumber = Long.parseLong(ssn);
        } catch (NumberFormatException nfe) {
            mSocialSecurityNumber = DEFAULT_SSN;
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

    /**
     * Creates the data object to create a new user on the API.
     * @return The API request data object.
     */
    public CreateUserRequestVo getUserRequestData() {
        CreateUserRequestVo data = new CreateUserRequestVo();
        UserDataVo base = getBaseData();

        data.first_name = base.firstName;
        data.last_name = base.lastName;
        data.birthdate = getBirthday().toString();
        data.ssn = getSocialSecurityNumber();
        data.email = base.emailAddress;
        data.phone = base.phoneNumber;
        data.income = base.income;
        data.street = base.address;
        data.apt = base.apartmentNumber;
        data.city = base.city;
        data.state = base.state;
        data.zip_code = base.zip;

        return  data;
    }
}
