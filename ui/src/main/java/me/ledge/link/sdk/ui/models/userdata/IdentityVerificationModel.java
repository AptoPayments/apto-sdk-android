package me.ledge.link.sdk.ui.models.userdata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import me.ledge.link.sdk.api.vos.datapoints.Birthdate;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.datapoints.SSN;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.utils.DateUtil;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Adrian
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private int mExpectedSSNLength;
    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private int mMinimumAge;
    private Date mBirthday;
    private String mSocialSecurityNumber;
    private boolean mSocialSecurityNumberNotSpecified;

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
        mExpectedSSNLength = 0;
        mBirthday = null;
        mSocialSecurityNumber = null;
        mSocialSecurityNumberNotSpecified = false;
    }

    /**
     * @return Formatted birthday.
     */
    public String getFormattedBirthday() {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return birthdayFormat.format(mBirthday);
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
                DataPointVo.DataPointType.BirthDate, null);
        if(baseBirthdate!=null) {
            setBirthday(baseBirthdate.getDate(), DATE_FORMAT);
        }
        SSN baseSSN = (SSN) base.getUniqueDataPoint(DataPointVo.DataPointType.SSN, null);
        if(baseSSN!=null && baseSSN.getSocialSecurityNumber()!=null) {
            setSocialSecurityNumber(baseSSN.getSocialSecurityNumber());
        }
    }

    public void setBirthday(String date, String format) {
        mBirthday = new DateUtil().getDateFromString(date, format);
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        if(hasValidBirthday()) {
            Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.BirthDate, new Birthdate());
            baseBirthdate.setDate(getFormattedBirthday());
        }
        if(hasValidSsn() || mSocialSecurityNumberNotSpecified) {
            SSN baseSSN = (SSN) base.getUniqueDataPoint(DataPointVo.DataPointType.SSN, new SSN());
            baseSSN.setSocialSecurityNumber(getSocialSecurityNumber());
            baseSSN.setNotSpecified(mSocialSecurityNumberNotSpecified);
        }
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
     * Stores the expected SSN length.
     * @param length SSN length.
     */
    public void setExpectedSSNLength(int length) {
        mExpectedSSNLength = length;
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
                .digit().count(mExpectedSSNLength)
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
        baseSSN.setNotSpecified(mSocialSecurityNumberNotSpecified);
        Birthdate baseBirthDate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate,
                new Birthdate());
        baseBirthDate.setDate(getFormattedBirthday());

        return data;
    }

    public void setSocialSecurityNotAvailable(boolean notAvailable) {
        mSocialSecurityNumberNotSpecified = notAvailable;
    }
}
