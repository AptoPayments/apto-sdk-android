package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.SSN;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.lanwen.verbalregex.VerbalExpression;

import static com.shiftpayments.link.sdk.ui.utils.DateUtil.BIRTHDATE_DATE_FORMAT;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Adrian
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private int mExpectedSSNLength;
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

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.id_verification_title_get_offers;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasValidData() {
        return hasValidBirthday() && hasValidSsn();
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate, null);
        if(baseBirthdate!=null) {
            mBirthday = new DateUtil().getDateFromString(baseBirthdate.getDate(), BIRTHDATE_DATE_FORMAT);
        }
        SSN baseSSN = (SSN) base.getUniqueDataPoint(DataPointVo.DataPointType.SSN, null);
        if(baseSSN!=null && baseSSN.getSocialSecurityNumber()!=null) {
            setSocialSecurityNumber(baseSSN.getSocialSecurityNumber());
        }
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
     * Initializes this class.
     */
    private void init() {
        mMinimumAge = 0;
        mExpectedSSNLength = 0;
        mBirthday = null;
        mSocialSecurityNumber = null;
        mSocialSecurityNumberNotSpecified = false;
    }

    /**
     * @return Formatted birthday.
     */
    private String getFormattedBirthday() {
        return new DateUtil().getBirthdayFormat().format(mBirthday);
    }

    public String getBirthdateDay() {
        return String.valueOf(mBirthday.getDay());
    }

    public String getBirthdateYear() {
        return String.valueOf(mBirthday.getYear());
    }

    public int getBirthdateMonth() {
        return mBirthday.getMonth();
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
     * Stores the birthday.
     * @param year Year of birth.
     * @param monthOfYear Month of birth.
     * @param dayOfMonth Day of birth.
     */
    public void setBirthday(int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar birth = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            birth.setLenient(false);
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
