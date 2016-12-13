package me.ledge.link.sdk.ui.models.verification;

import android.text.TextUtils;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Model} for the phone information screen.
 * @author Adrian
 */
public class PhoneVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private String mVerificationCode;

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
        mVerificationCode = null;

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
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);
    }

    /**
     * @return Verification code.
     */
    public String getVerificationCode() {
        return mVerificationCode;
    }

    /**
     * Stores a valid verification code.
     * @param verificationCode Verification code.
     */
    public void setVerificationCode(String verificationCode) {
        if (TextUtils.isEmpty(verificationCode)) {
            mVerificationCode = null;
        } else {
            mVerificationCode = verificationCode;
        }
    }


    /**
     * @return Whether a verification code has been set.
     */
    public boolean hasVerificationCode() {
        return mVerificationCode != null;
    }
}
