package com.shift.link.sdk.ui.models.verification;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.Email;
import com.shift.link.sdk.api.vos.datapoints.VerificationVo;
import com.shift.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.models.userdata.AbstractUserDataModel;
import com.shift.link.sdk.ui.models.userdata.UserDataModel;
import com.shift.link.sdk.ui.storages.UserStorage;

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
        Email email = getEmailFromBaseData();
        email.setVerification(mVerification);
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
}