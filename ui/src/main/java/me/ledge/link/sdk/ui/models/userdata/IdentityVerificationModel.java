package me.ledge.link.sdk.ui.models.userdata;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.sdk.ui.vos.UserDataVo;
import ru.lanwen.verbalregex.VerbalExpression;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private static final int EXPECTED_SSN_LENGTH = 9; // TODO: Move to values/ints.xml?

    private int mMinimumAge;
    private Date mBirthday;
    private String mSocialSecurityNumber;

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
        mSocialSecurityNumber = null;
    }

    /**
     * @param number The number to format.
     * @return E.164 formatted phone number.
     */
    private String getFormattedPhone(Phonenumber.PhoneNumber number) {
        String formatted = null;

        if (number != null) {
            formatted = PhoneNumberUtil.getInstance().format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        }

        return formatted;
    }

    /**
     * @param birthday Date to format.
     * @return Formatted birthday.
     */
    private String getFormattedBirthday(Date birthday) {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        return birthdayFormat.format(birthday);
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
    public String getSocialSecurityNumber() {
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

        if(ssnRegex.testExact(ssn)) {
            mSocialSecurityNumber = ssn;
        } else {
            mSocialSecurityNumber = null;
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
        return getBirthday() != null;
    }

    /**
     * @return Whether a valid SSN has been stored.
     */
    public boolean hasValidSsn() {
        return getSocialSecurityNumber() != null;
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
        data.birthdate = getFormattedBirthday(getBirthday());
        data.ssn = getSocialSecurityNumber();
        data.email = base.emailAddress;
        data.phone_number = getFormattedPhone(base.phoneNumber);
        data.income = base.income;
        data.street = base.address;
        data.apt = base.apartmentNumber;
        data.city = base.city;
        data.state = base.state;
        data.zip_code = base.zip;
        data.credit_range = base.creditScoreRange;

        return  data;
    }
}
