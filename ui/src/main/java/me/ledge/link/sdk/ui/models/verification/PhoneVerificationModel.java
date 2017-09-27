package me.ledge.link.sdk.ui.models.verification;

import android.text.TextUtils;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.VerificationVo;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.requests.verifications.StartVerificationRequestVo;
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
        PhoneNumberVo phoneNumber = getPhoneFromBaseData();
        phoneNumber.setVerification(mVerification);
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        PhoneNumberVo phoneNumber = (PhoneNumberVo) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Phone, new PhoneNumberVo());
        if(phoneNumber.hasVerification()) {
            mVerification = phoneNumber.getVerification();
        }
        else {
            mVerification = new VerificationVo();
        }
    }

    public PhoneNumberVo getPhoneFromBaseData() {
        DataPointList base = super.getBaseData();
        return (PhoneNumberVo) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Phone, new PhoneNumberVo());
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
        return request;
    }

    public String getVerificationId() {
        return mVerification.getVerificationId();
    }

    public StartVerificationRequestVo getPhoneVerificationRequest() {
        StartVerificationRequestVo request = new StartVerificationRequestVo();
        request.data = getPhoneFromBaseData();
        request.datapoint_type = DataPointVo.DataPointType.Phone;
        return request;
    }
}
