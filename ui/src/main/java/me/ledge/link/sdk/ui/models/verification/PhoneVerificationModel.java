package me.ledge.link.sdk.ui.models.verification;

import android.text.TextUtils;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.VerificationVo;
import me.ledge.link.api.vos.requests.verifications.PhoneVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link Model} for the phone information screen.
 * @author Adrian
 */
public class PhoneVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private VerificationVo mVerification;

    /**
     * Creates a new {@link PhoneVerificationModel} instance.
     */
    public PhoneVerificationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        setBaseData(UserStorage.getInstance().getUserData());
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.phone_verification_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasVerificationCode();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        DataPointVo.PhoneNumber phoneNumber = getPhoneFromBaseData();
        phoneNumber.setVerification(mVerification);
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber,
                new DataPointVo.PhoneNumber());
        if(phoneNumber.hasVerification()) {
            mVerification = phoneNumber.getVerification();
        }
        else {
            mVerification = new VerificationVo();
        }
    }

    public DataPointVo.PhoneNumber getPhoneFromBaseData() {
        DataPointList base = super.getBaseData();
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
        return phoneNumber;
    }

    /**
     * @return Verification code.
     */
    public String getVerificationCode() {
        return mVerification.getSecret();
    }

    /**
     * Stores a valid verification code.
     * @param verificationCode Verification code.
     */
    public void setVerificationCode(String verificationCode) {
        if (TextUtils.isEmpty(verificationCode)) {
            mVerification.setSecret(null);
        } else {
            mVerification.setSecret(verificationCode);
        }
    }
    
    /**
     * @return Whether a verification code has been set.
     */
    public boolean hasVerificationCode() {
        return mVerification.getSecret() != null;
    }

    public VerificationRequestVo getVerificationRequest() {
        VerificationRequestVo request = new VerificationRequestVo();
        request.secret = getVerificationCode();
        request.verification_id = getVerificationId();
        return request;
    }

    private int getVerificationId() {
        return mVerification.getVerificationId();
    }

    public PhoneVerificationRequestVo getPhoneVerificationRequest() {
        PhoneVerificationRequestVo request = new PhoneVerificationRequestVo();
        DataPointVo.PhoneNumber phoneNumber = getPhoneFromBaseData();
        request.country_code = phoneNumber.getPhone().getCountryCode();
        request.phone_number = String.valueOf(phoneNumber.getPhone().getNationalNumber());

        return request;
    }
}
