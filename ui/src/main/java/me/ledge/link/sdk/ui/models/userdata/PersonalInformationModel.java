package me.ledge.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import java.util.LinkedList;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.PersonalName;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import ru.lanwen.verbalregex.VerbalExpression;


/**
 * Concrete {@link Model} for the personal information screen.
 * @author Adrian
 */

public class PersonalInformationModel extends AbstractUserDataModel implements UserDataModel {

    private PersonalName mPersonalName;
    private Email mEmail;
    private boolean mEmailNotSpecified;

    /**
     * Creates a new {@link PersonalInformationModel} instance.
     */

    public PersonalInformationModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mPersonalName = new PersonalName();
        mEmail = new Email();
        mEmailNotSpecified = false;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.personal_info_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasName() && hasEmail();
    }

    public boolean hasName() {
        return hasFirstName() && hasLastName();
    }

    public boolean isEmailNotSpecified() {
        return mEmailNotSpecified;
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasName()) {
            PersonalName personalName = (PersonalName) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.PersonalName, new PersonalName());
            personalName.firstName = mPersonalName.firstName;
            personalName.lastName = mPersonalName.lastName;
        }
        if(hasEmail() || mEmailNotSpecified) {
            Email emailAddress = (Email) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Email, new Email());
            emailAddress.email = mEmail.email;
            emailAddress.setNotSpecified(mEmailNotSpecified);
        }

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        PersonalName personalName = (PersonalName) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName, null);
        if(personalName!=null) {
            setPersonalName(personalName);
        }

        Email emailAddress = (Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, null);
        if(emailAddress!=null) {
            setEmail(emailAddress);
            mEmailNotSpecified = emailAddress.isNotSpecified();
        }
    }

    public void setPersonalName(PersonalName personalName) {
        if(personalName != null) {
            setFirstName(personalName.firstName);
            setLastName(personalName.lastName);
        }
    }

    /**
     * @return First name.
     */
    public String getFirstName() {
        return mPersonalName.firstName;
    }

    /**
     * Stores a valid first name.
     * @param firstName First name.
     */
    public void setFirstName(String firstName) {
        if (TextUtils.isEmpty(firstName)) {
            mPersonalName.firstName = null;
        } else {
            mPersonalName.firstName = firstName;
        }
    }

    /**
     * @return Last name.
     */
    public String getLastName() {
        return mPersonalName.lastName;
    }

    /**
     * Stores a valid last name.
     * @param lastName Last name.
     */
    public void setLastName(String lastName) {
        if (TextUtils.isEmpty(lastName)) {
            mPersonalName.lastName = null;
        } else {
            mPersonalName.lastName = lastName;
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
        VerbalExpression emailRegex = VerbalExpression.regex()
                .startOfLine()
                .anythingBut(" ")
                .then("@")
                .anythingBut(" ")
                .then(".")
                .anythingBut(" ")
                .endOfLine()
                .build();

        if (emailRegex.testExact(email)) {
            mEmail.email = email;
        } else {
            mEmail.email = null;
        }
    }

    /**
     * @return Whether a first name has been set.
     */
    public boolean hasFirstName() {
        return !TextUtils.isEmpty(mPersonalName.firstName);
    }

    /**
     * @return Whether a last name has been set.
     */
    public boolean hasLastName() {
        return !TextUtils.isEmpty(mPersonalName.lastName);
    }

    /**
     * @return Whether an email address has been set.
     */
    public boolean hasEmail() {
        return !TextUtils.isEmpty(mEmail.email);
    }

    public boolean hasAllRequiredData(boolean isNameRequired, boolean isEmailRequired) {
        boolean hasAllData = true;

        LinkedList<Boolean> requiredDataList = new LinkedList<>();
        if(isNameRequired) {
            requiredDataList.add(hasName());
        }
        if(isEmailRequired) {
            requiredDataList.add(hasEmail());
        }

        for (boolean hasData:requiredDataList) {
            hasAllData = hasAllData && hasData;
        }
        return hasAllData;
    }

    public void setEmailNotAvailable(boolean notAvailable) {
        mEmailNotSpecified = notAvailable;
    }
}

