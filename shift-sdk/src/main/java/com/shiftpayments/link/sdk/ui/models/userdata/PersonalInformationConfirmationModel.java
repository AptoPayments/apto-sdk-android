package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.IdDocument;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;
import com.shiftpayments.link.sdk.ui.utils.PhoneHelperUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static com.shiftpayments.link.sdk.ui.utils.DateUtil.BIRTHDATE_DATE_FORMAT;


/**
 * Concrete {@link Model} for the personal information confirmation screen.
 * @author Adrian
 */

public class PersonalInformationConfirmationModel extends AbstractUserDataModel implements UserDataModel {

    private PersonalName mPersonalName;
    private Email mEmail;
    private Address mAddress;
    private PhoneNumberVo mPhoneNumber;
    private Birthdate mBirthdate;
    private IdDocument mIdDocument;

    /**
     * Creates a new {@link PersonalInformationConfirmationModel} instance.
     */
    public PersonalInformationConfirmationModel() { }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.personal_info_label;
    }

    @Override
    public boolean hasValidData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        mPersonalName = (PersonalName) base.getUniqueDataPoint(DataPointVo.DataPointType.PersonalName, null);
        mEmail = (Email) base.getUniqueDataPoint(DataPointVo.DataPointType.Email, null);
        mAddress = (Address) base.getUniqueDataPoint(DataPointVo.DataPointType.Address, null);
        mPhoneNumber = (PhoneNumberVo) base.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
        mBirthdate = (Birthdate) base.getUniqueDataPoint(DataPointVo.DataPointType.BirthDate, null);
        mIdDocument = (IdDocument) base.getUniqueDataPoint(DataPointVo.DataPointType.IdDocument, null);
    }

    public String getFirstName() {
        return mPersonalName.firstName;
    }

    public String getLastName() {
        return mPersonalName.lastName;
    }

    public boolean hasPersonalName() {
        return mPersonalName != null;
    }

    public String getEmail() {
        return mEmail.toString();
    }

    public boolean hasEmail() {
        return mEmail != null;
    }

    public String getAddress() {
        return mAddress.toString();
    }

    public boolean hasAddress() {
        return mAddress != null;
    }

    public String getPhoneNumber() {
        return PhoneHelperUtil.formatPhone(mPhoneNumber.phoneNumber, true);
    }

    public boolean hasPhoneNumber() {
        return mPhoneNumber != null;
    }

    public String getDateOfBirth(Locale locale) {
        Date date = new DateUtil(locale).getDateFromString(mBirthdate.getDate(), BIRTHDATE_DATE_FORMAT);
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    public boolean hasDateOfBirth() {
        return mBirthdate != null;
    }

    public String getIdDocument() {
        return mIdDocument.toString();
    }

    public String getIdDocumentType() {
        return mIdDocument.getIdType().toString();
    }

    public boolean hasIdDocument() {
        return mIdDocument != null;
    }
}

