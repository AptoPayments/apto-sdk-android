package me.ledge.link.sdk.ui.models.userdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import me.ledge.link.api.vos.datapoints.Birthdate;
import me.ledge.link.api.vos.datapoints.CreditScore;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.SSN;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Adrian
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private static final int EXPECTED_SSN_LENGTH = 9; // TODO: Move to values/ints.xml?
    private static final String DATE_FORMAT = "MM-dd-yyyy";
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
     * @return Formatted birthday.
     */
    public String getFormattedBirthday() {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return birthdayFormat.format(mBirthday);
    }

    public Date getDateFromString(String dateString) {
        if(dateString != null) {
            SimpleDateFormat birthdayFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            try {
                return birthdayFormat.parse(dateString);
            } catch (ParseException e) {
                return null;
            }
        }
        else {
            return null;
        }
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

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate, new Birthdate());
        setBirthday(baseBirthdate.getDate());

        SSN baseSSN = (SSN) base.getUniqueDataPoint(DataPointVo.DataPointType.SSN, new SSN());
        setSocialSecurityNumber(baseSSN.getSocialSecurityNumber());
    }

    private void setBirthday(String date) {
        mBirthday = getDateFromString(date);
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate, new CreditScore());
        baseBirthdate.setDate(getFormattedBirthday());

        SSN baseSSN = (SSN) base.getUniqueDataPoint(DataPointVo.DataPointType.SSN, new SSN());
        baseSSN.setSocialSecurityNumber(getSocialSecurityNumber());
        return base;
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
            mBirthday = birth.getTime();
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
        if (mBirthday == null) {
            return false;
        }

        Calendar birth = GregorianCalendar.getInstance();
        birth.setTime(mBirthday);

        Calendar minAge = GregorianCalendar.getInstance();
        minAge.add(Calendar.YEAR, mMinimumAge * -1);

        return birth.compareTo(minAge) < 0;
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
    public DataPointList getUserData() {
        DataPointList data = new DataPointList();
        DataPointList base = getBaseData();
        data.setDataPoints(base.getDataPoints());

        SSN baseSSN = (SSN) base.getUniqueDataPoint(
                DataPointVo.DataPointType.SSN,
                new SSN());
        baseSSN.setSocialSecurityNumber(this.getSocialSecurityNumber());
        Birthdate baseBirthDate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate,
                new Birthdate());
        baseBirthDate.setDate(getFormattedBirthday());

        return data;
    }
}
