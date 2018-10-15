package com.shiftpayments.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.IdDocument;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.shiftpayments.link.sdk.ui.utils.DateUtil.BIRTHDATE_DATE_FORMAT;

/**
 * Concrete {@link Model} for the ID verification screen.
 * @author Adrian
 */
public class IdentityVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private int mMinimumAge;
    private Date mBirthday;
    private IdDocument mIdDocument;
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
        return hasValidBirthday() && hasValidDocument();
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
        IdDocument idDocument = (IdDocument) base.getUniqueDataPoint(DataPointVo.DataPointType.IdDocument, null);
        if(idDocument!=null && idDocument.getIdValue()!=null) {
            setDocumentNumber(idDocument.getIdValue());
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
        if(hasValidDocument() || mSocialSecurityNumberNotSpecified) {
            IdDocument baseIdDocument = (IdDocument) base.getUniqueDataPoint(DataPointVo.DataPointType.IdDocument, new IdDocument());
            baseIdDocument.setIdValue(getDocumentNumber());
            baseIdDocument.setIdType(mIdDocument.getIdType());
            baseIdDocument.setCountry(mIdDocument.getCountry());
            baseIdDocument.setNotSpecified(mSocialSecurityNumberNotSpecified);
        }
        return base;
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mMinimumAge = 0;
        mBirthday = null;
        mIdDocument = new IdDocument();
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
     * @return the ID document number
     */
    public String getDocumentNumber() {
        return mIdDocument.getIdValue();
    }

    /**
     * Stores the document number
     * @param documentNumber The document number.
     */
    public void setDocumentNumber(String documentNumber) {
        if (TextUtils.isEmpty(documentNumber)) {
            mIdDocument.setIdValue(null);
        } else {
            mIdDocument.setIdValue(documentNumber);
        }
    }

    public void setCountry(String country) {
        mIdDocument.setCountry(country);
    }

    public void setDocumentType(IdDocument.IdDocumentType documentType) {
        mIdDocument.setIdType(documentType);
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
        return R.string.id_verification_document_number_error;
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
     * @return Whether a valid document has been stored.
     */
    public boolean hasValidDocument() {
        return hasValidDocumentNumber() && hasValidDocumentType() && hasValidCountry();
    }

    private boolean hasValidDocumentNumber() {
        return mIdDocument.getIdValue() != null && !mIdDocument.getIdValue().isEmpty();
    }

    private boolean hasValidDocumentType() {
        return mIdDocument.getIdType() != null;
    }

    private boolean hasValidCountry() {
        return mIdDocument.getCountry() != null && !mIdDocument.getCountry().isEmpty();
    }

    public void setSocialSecurityNotAvailable(boolean notAvailable) {
        mSocialSecurityNumberNotSpecified = notAvailable;
    }
}
