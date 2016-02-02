package us.ledge.link.sdk.sdk.models.userdata;

import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.activities.userdata.IncomeActivity;
import us.ledge.link.sdk.sdk.activities.userdata.PersonalInformationActivity;
import us.ledge.link.sdk.sdk.models.Model;
import us.ledge.link.sdk.sdk.vos.UserDataVo;

/**
 * Concrete {@link Model} for the address screen.
 * TODO: Validation in setters.
 * @author Wijnand
 */
public class AddressModel extends AbstractUserDataModel implements UserDataModel {

    private String mAddress;
    private String mApartmentNumber;
    private String mCity;
    private String mState;
    private String mZip;

    /**
     * Creates a new {@link AddressModel} instance.
     */
    public AddressModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mAddress = null;
        mApartmentNumber = null;
        mCity = null;
        mState = null;
        mZip = null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return PersonalInformationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return IncomeActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.address = mAddress;
        base.apartmentNumber = mApartmentNumber;
        base.city = mCity;
        base.state = mState;
        base.zip = mZip;

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        super.setBaseData(base);

        setAddress(base.address);
        setApartmentNumber(base.apartmentNumber);
        setCity(base.city);
        setState(base.state);
        setZip(base.zip);
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return mAddress;
    }

    /**
     * Stores a valid address.
     * @param address The address.
     */
    public void setAddress(String address) {
        mAddress = address;
    }

    /**
     * @return Apartment number.
     */
    public String getApartmentNumber() {
        return mApartmentNumber;
    }

    /**
     * Stores a valid apartment or unit number.
     * @param apartmentNumber Apartment number.
     */
    public void setApartmentNumber(String apartmentNumber) {
        mApartmentNumber = apartmentNumber;
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mCity;
    }

    /**
     * Stores a valid city name.
     * @param city City name.
     */
    public void setCity(String city) {
        mCity = city;
    }

    /**
     * Stores a valid state name.
     * @param state State name.
     */
    public void setState(String state) {
        mState = state;
    }

    /**
     * @return Zip or postal code.
     */
    public String getZip() {
        return mZip;
    }

    /**
     * Stores a valid zip code.
     * @param zip Zip or postal code.
     */
    public void setZip(String zip) {
        mZip = zip;
    }
}
