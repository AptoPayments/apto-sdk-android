package com.shiftpayments.link.sdk.ui.models.verification;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.models.userdata.AbstractUserDataModel;
import com.shiftpayments.link.sdk.ui.models.userdata.UserDataModel;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;

/**
 * Concrete {@link Model} for the email information screen.
 * @author Adrian
 */
public class EmailVerificationModel extends AbstractUserDataModel implements UserDataModel {

    private VerificationVo mVerification;

    /**
     * Creates a new {@link EmailVerificationModel} instance.
     */
    public EmailVerificationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        setBaseData(UserStorage.getInstance().getUserData());
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.email_verification_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasValidData() {
        return isEmailVerified();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        storeVerification();
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        Email email = (Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email,
                new Email());
        if(email.hasVerification()) {
            mVerification = email.getVerification();
        }
        else {
            mVerification = new VerificationVo();
        }
    }

    public Email getEmailFromBaseData() {
        DataPointList base = super.getBaseData();
        return (Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, new Email());
    }

    public boolean isEmailVerified() {
        return mVerification.isVerified();
    }

    public String getVerificationId() {
        return mVerification.getVerificationId();
    }

    public StartVerificationRequestVo getEmailVerificationRequest() {
        StartVerificationRequestVo request = new StartVerificationRequestVo();
        request.data = getEmailFromBaseData();
        request.datapoint_type = DataPointVo.DataPointType.Email;
        request.show_verification_secret = true;
        return request;
    }

    public String getEmail() {
        Email email = (Email) getBaseData().
                getUniqueDataPoint(DataPointVo.DataPointType.Email, new Email());
        return email.email;
    }

    public boolean hasEmail() {
        return getEmail()!=null && !getEmail().isEmpty();
    }

    /**
     * @return Whether a verification code has been set.
     */
    public boolean hasVerificationCode() {
        return mVerification.getSecret() != null;
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

    public VerificationRequestVo getVerificationRequest() {
        VerificationRequestVo request = new VerificationRequestVo();
        request.secret = getVerificationCode();
        request.verificationId = getVerificationId();
        return request;
    }

    public void setVerificationStatus(String status) {
        mVerification.setVerificationStatus(status);
        storeVerification();
    }

    public void setVerification(String id, String type) {
        mVerification = new VerificationVo(id, type);
        storeVerification();
    }

    private void storeVerification() {
        Email email = getEmailFromBaseData();
        email.setVerification(mVerification);
    }
}
