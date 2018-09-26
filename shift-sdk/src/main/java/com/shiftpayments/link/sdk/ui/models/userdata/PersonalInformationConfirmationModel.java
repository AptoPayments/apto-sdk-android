package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;


/**
 * Concrete {@link Model} for the personal information confirmation screen.
 * @author Adrian
 */

public class PersonalInformationConfirmationModel extends AbstractUserDataModel implements UserDataModel {

    private PersonalName mPersonalName;
    private Email mEmail;
    private Address mAddress;
    private PhoneNumberVo mPhoneNumber;

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
        return mPhoneNumber.toString();
    }

    public boolean hasPhoneNumber() {
        return mPhoneNumber != null;
    }
}

