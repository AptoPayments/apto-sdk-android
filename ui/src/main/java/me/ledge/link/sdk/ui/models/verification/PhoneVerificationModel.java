package me.ledge.link.sdk.ui.models.verification;

import android.text.TextUtils;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.VerificationVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;

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
        mVerification = new VerificationVo();
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
    public DataPointVo.DataPointList getBaseData() {
        DataPointVo.DataPointList base = super.getBaseData();
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
        phoneNumber.setVerification(mVerification);
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointVo.DataPointList base) {
        super.setBaseData(base);
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber,
                new DataPointVo.PhoneNumber());
        if(phoneNumber.hasVerification()) {
            mVerification = phoneNumber.getVerification();
        }
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
}
