package me.ledge.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.utils.PhoneHelperUtil;
import ru.lanwen.verbalregex.VerbalExpression;


/**
 * Concrete {@link Model} for the personal information screen.
 * @author Adrian
 */

public class PersonalInformationModel extends AbstractUserDataModel implements UserDataModel {

    private DataPointVo.PersonalName mPersonalName;
    private DataPointVo.Email mEmail;
    private DataPointVo.PhoneNumber mPhone;

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
        mPersonalName = new DataPointVo.PersonalName();
        mEmail = new DataPointVo.Email();
        mPhone = new DataPointVo.PhoneNumber();
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.personal_info_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasFirstName() && hasLastName() && hasEmail() && hasPhone();
    }


    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        DataPointVo.PersonalName personalName = (DataPointVo.PersonalName) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName, new DataPointVo.PersonalName());
        personalName.firstName = mPersonalName.firstName;
        personalName.lastName = mPersonalName.lastName;
        DataPointVo.Email emailAddress = (DataPointVo.Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, new DataPointVo.Email());
        emailAddress.email = mEmail.email;
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
        phoneNumber.phoneNumber = mPhone.phoneNumber;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        DataPointVo.PersonalName personalName = (DataPointVo.PersonalName) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PersonalName,
                new DataPointVo.PersonalName());
        setPersonalName(personalName);
        DataPointVo.Email emailAddress = (DataPointVo.Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email,
                new DataPointVo.Email());
        setEmail(emailAddress);
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) base.getUniqueDataPoint(
                DataPointVo.DataPointType.PhoneNumber,
                new DataPointVo.PhoneNumber());
        setPhone(phoneNumber);
    }

    public void setPersonalName(DataPointVo.PersonalName personalName) {
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

    public void setEmail(DataPointVo.Email emailAddress) {
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
                .anythingBut("@").atLeast(1)
                .then("@")
                .anythingBut("@").atLeast(1)
                .endOfLine()
                .build();

        if (emailRegex.testExact(email)) {
            mEmail.email = email;
        } else {
            mEmail.email = null;
        }
    }

    /**
     * @return Phone number.
     */
    public PhoneNumber getPhone() {
        return mPhone.phoneNumber;
    }

    public void setPhone(DataPointVo.PhoneNumber phoneNumber) {
        if(phoneNumber != null) {
            setPhone(phoneNumber.phoneNumber);
        }
    }

    /**
     * Parses and stores a valid phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone) {
        //TODO: check country code
        PhoneNumber number = PhoneHelperUtil.parsePhone(phone);
        setPhone(number);
    }

    /**
     * Stores a valid phone number.
     * @param number Phone number object.
     */
    public void setPhone(PhoneNumber number) {
        //TODO: refactor phoneNumber in DataPoint to String
        if (number != null && PhoneHelperUtil.isValidNumber(number)) {
            mPhone.phoneNumber = number;
        } else {
            mPhone.phoneNumber = null;
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

    /**
     * @return Whether a phone number has been set.
     */
    public boolean hasPhone() {
        return mPhone.phoneNumber != null;
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

