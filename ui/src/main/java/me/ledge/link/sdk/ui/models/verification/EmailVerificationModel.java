package me.ledge.link.sdk.ui.models.verification;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.VerificationVo;
import me.ledge.link.api.vos.requests.verifications.EmailVerificationRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;
import me.ledge.link.sdk.ui.storages.UserStorage;

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
    protected void init() {
        setBaseData(UserStorage.getInstance().getUserData());
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.email_verification_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return isEmailVerified();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        DataPointVo.Email email = getEmailFromBaseData();
        email.setVerification(mVerification);
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        DataPointVo.Email email = (DataPointVo.Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email,
                new DataPointVo.Email());
        if(email.hasVerification()) {
            mVerification = email.getVerification();
        }
        else {
            mVerification = new VerificationVo();
        }
    }

    public DataPointVo.Email getEmailFromBaseData() {
        DataPointList base = super.getBaseData();
        DataPointVo.Email email = (DataPointVo.Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, new DataPointVo.Email());
        return email;
    }

    public boolean isEmailVerified() {
        return mVerification.isVerified();
    }

    public String getVerificationId() {
        return mVerification.getVerificationId();
    }

    public EmailVerificationRequestVo getEmailVerificationRequest() {
        EmailVerificationRequestVo request = new EmailVerificationRequestVo();
        DataPointVo.Email email = getEmailFromBaseData();
        request.email = email.email;

        return request;
    }

    public DataPointList getLoginData() {
        DataPointList base = super.getBaseData();
        DataPointVo.Email emailAddress = (DataPointVo.Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, new DataPointVo.Email());
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());

        DataPointList data = new DataPointList();
        data.add(emailAddress);
        data.add(phoneNumber);
        return data;
    }
}
