package com.shiftpayments.link.sdk.ui.models.userdata;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.utils.PhoneHelperUtil;


/**
 * Concrete {@link Model} for the phone screen.
 * @author Adrian
 */

public class PhoneModel extends AbstractUserDataModel implements UserDataModel {

    private PhoneNumberVo mPhone;

    /**
     * Creates a new {@link PhoneModel} instance.
     */

    public PhoneModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mPhone = new PhoneNumberVo();
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.phone_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasPhone();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasPhone()) {
            PhoneNumberVo phoneNumber = (PhoneNumberVo) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Phone, new PhoneNumberVo());
            phoneNumber.phoneNumber = mPhone.phoneNumber;
            phoneNumber.setVerification(mPhone.getVerification());
        }

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);

        PhoneNumberVo phoneNumber = (PhoneNumberVo) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Phone, null);
        if(phoneNumber!=null) {
            setPhone(phoneNumber.phoneNumber);
            mPhone.setVerification(phoneNumber.getVerification());
        }
    }

    /**
     * @return Phone number.
     */
    public PhoneNumber getPhone() {
        return mPhone.phoneNumber;
    }

    /**
     * Parses and stores a valid phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone) {
        PhoneNumber number = PhoneHelperUtil.parsePhone(phone);
        setPhone(number);
    }

    /**
     * Stores a valid phone number.
     * @param number Phone number object.
     */
    public void setPhone(PhoneNumber number) {
        if (number != null && PhoneHelperUtil.isValidNumber(number)) {
            if(!number.equals(mPhone.phoneNumber)) {
                mPhone.invalidateVerification();
                mPhone.phoneNumber = number;
            }
        } else {
            mPhone.phoneNumber = null;
        }
    }

    /**
     * @return Whether a phone number has been set.
     */
    public boolean hasPhone() {
        return mPhone.phoneNumber != null;
    }
}

