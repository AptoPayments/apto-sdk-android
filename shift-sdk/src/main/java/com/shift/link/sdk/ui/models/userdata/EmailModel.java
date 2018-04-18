package com.shift.link.sdk.ui.models.userdata;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.Email;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.utils.EmailUtil;

/**
 * Created by pauteruel on 19/02/2018.
 */

public class EmailModel extends AbstractUserDataModel implements UserDataModel {

    private Email mEmail;

    /**
     * Creates a new {@link EmailModel} instance.
     */

    public EmailModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mEmail = new Email();
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.email_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasEmail();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasEmail()) {
            Email emailAddress = (Email) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Email, new Email());
            emailAddress.email = mEmail.email;
            emailAddress.setVerification(mEmail.getVerification());
        }

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);

        Email emailAddress = (Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, null);
        if(emailAddress!=null) {
            setEmail(emailAddress.email);
            mEmail.setVerification(emailAddress.getVerification());
        }
    }

    /**
     * @return Email address.
     */
    public String getEmail() {
        return mEmail.email;
    }

    public void setEmail(Email emailAddress) {
        if(emailAddress != null) {
            setEmail(emailAddress.email);
        }
    }

    /**
     * Stores a valid email address.
     * @param email Email address.
     */
    public void setEmail(String email) {
        if (EmailUtil.isValidEmail(email)) {
            mEmail.email = email;
        } else {
            mEmail.email = null;
        }
    }

    /**
     * @return Whether a email address has been set.
     */
    public boolean hasEmail() {
        return mEmail.email != null;
    }
}




