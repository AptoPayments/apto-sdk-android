package com.shiftpayments.link.sdk.ui.models.verification;

import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.models.userdata.AbstractUserDataModel;
import com.shiftpayments.link.sdk.ui.models.userdata.UserDataModel;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Concrete {@link Model} for the birthdate screen.
 * @author Adrian
 */
public class BirthdateVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private static final String DATE_FORMAT = "MM-dd-yyyy";
    private int mMinimumAge;
    private Date mBirthdate;
    private VerificationVo mVerification;

    /**
     * Creates a new {@link BirthdateVerificationModel} instance.
     */
    public BirthdateVerificationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mMinimumAge = 0;
        mBirthdate = null;
        mVerification = new VerificationVo();
    }

    /**
     * @return Formatted birthday.
     */
    public String getFormattedBirthdate() {
        SimpleDateFormat birthdayFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return birthdayFormat.format(mBirthdate);
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
        return R.string.birthdate_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidBirthdate();
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate, null);
        if(baseBirthdate!=null) {
            setBirthdate(baseBirthdate.getDate());
            mVerification.setVerificationId(baseBirthdate.getVerification().getVerificationId());
        }
    }

    private void setBirthdate(String date) {
        mBirthdate = getDateFromString(date);
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        if(hasValidBirthdate()) {
            Birthdate baseBirthdate = (Birthdate) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.BirthDate, new Birthdate());
            baseBirthdate.setDate(getFormattedBirthdate());
            baseBirthdate.setVerification(mVerification);
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
     * @return Birthdate.
     */
    public Date getBirthdate() {
        return mBirthdate;
    }

    /**
     * Stores the birthday.
     * @param year Year of birth.
     * @param monthOfYear Month of birth.
     * @param dayOfMonth Day of birth.
     */
    public void setBirthdate(int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar birth = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            mBirthdate = birth.getTime();
        } catch (IllegalArgumentException iae) {
            mBirthdate = null;
        }
    }

    public void setBirthdate(String birthdate, String format) {
        mBirthdate = new DateUtil().getDateFromString(birthdate, format);
    }

    /**
     * @return Error message to show when an incorrect birthday has been entered.
     */
    public int getBirthdateErrorString() {
        return R.string.birthdate_error;
    }

    /**
     * @return Whether a valid birthday has been stored.
     */
    public boolean hasValidBirthdate() {
        if (mBirthdate == null) {
            return false;
        }

        Calendar birth = GregorianCalendar.getInstance();
        birth.setTime(mBirthdate);

        Calendar minAge = GregorianCalendar.getInstance();
        minAge.add(Calendar.YEAR, mMinimumAge * -1);

        return birth.compareTo(minAge) < 0;
    }

    /**
     * Creates the data object to create a new user on the API.
     * @return The API request data object.
     */
    public DataPointList getUserData() {
        DataPointList data = new DataPointList();
        DataPointList base = getBaseData();
        data.setDataPoints(base.getDataPoints());

        Birthdate baseBirthDate = (Birthdate) base.getUniqueDataPoint(
                DataPointVo.DataPointType.BirthDate,
                new Birthdate());
        baseBirthDate.setDate(getFormattedBirthdate());

        return data;
    }

    public VerificationRequestVo getVerificationRequest() {
        VerificationRequestVo request = new VerificationRequestVo();
        request.secret = getFormattedBirthdate();
        return request;
    }

    public String getVerificationId() {
        return mVerification.getVerificationId();
    }

    public VerificationVo getVerification() {
        return mVerification;
    }
}
