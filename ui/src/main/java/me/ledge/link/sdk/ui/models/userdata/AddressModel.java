package me.ledge.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class AddressModel extends AbstractUserDataModel implements UserDataModel {

    private Address mAddress;
    private boolean mIsAddressValid;

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
        mAddress = new Address();
        mIsAddressValid = false;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAddress() && hasValidCity() && hasValidState() && hasValidZip();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());
        baseAddress.update(getAddress());

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);

        Address address = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        if(address!=null) {
            setAddress(address);
        }
    }

    /**
     * @return Address.
     */
    public Address getAddress() {
        return mAddress;
    }

    public void setAddress(Address address) {
        setStreetAddress(address.address);
        setApartmentNumber(address.apUnit);
        setCity(address.city);
        setState(address.stateCode);
        setZip(address.zip);
        setCountry(address.country);
    }

    public String getStreetAddress() {
        return mAddress.address;
    }

    /**
     * Stores a valid address.
     * @param address The address.
     */
    public void setStreetAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            mAddress.address = null;
        } else {
            mAddress.address = address;
        }
    }

    /**
     * Stores a valid country.
     * @param country The country.
     */
    public void setCountry(String country) {
        if (TextUtils.isEmpty(country)) {
            mAddress.country = null;
        } else {
            mAddress.country = country;
        }
    }

    /**
     * @return Apartment number.
     */
    public String getApartmentNumber() {
        return mAddress.apUnit;
    }

    /**
     * Stores a valid apartment or unit number.
     * @param apartmentNumber Apartment number.
     */
    public void setApartmentNumber(String apartmentNumber) {
        mAddress.apUnit = apartmentNumber;
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mAddress.city;
    }

    /**
     * Stores a valid city name.
     * @param city City name.
     */
    public void setCity(String city) {
        if (TextUtils.isEmpty(city)) {
            mAddress.city = null;
        } else {
            mAddress.city = city;
        }
    }

    /**
     * @return State abbreviation.
     */
    public String getState() {
        return mAddress.stateCode;
    }

    /**
     * Stores a valid state abbreviation.
     * @param state State abbreviation.
     */
    public void setState(String state) {
        VerbalExpression stateRegex = VerbalExpression.regex()
                .startOfLine()
                .add("[A-Za-z]").count(2)
                .endOfLine()
                .build();

        if (stateRegex.testExact(state)) {
            mAddress.stateCode = state.toUpperCase();
        } else {
            mAddress.stateCode = null;
        }
    }

    /**
     * @return Zip or postal code.
     */
    public String getZip() {
        return mAddress.zip;
    }

    /**
     * Stores a valid zip code.
     * @param zip Zip or postal code.
     */
    public void setZip(String zip) {
        VerbalExpression.Builder plusFourRegex = VerbalExpression.regex()
                .then("-")
                .digit().count(4);

        VerbalExpression zipRegex = VerbalExpression.regex()
                .startOfLine()
                .digit().count(5)
                .maybe(plusFourRegex)
                .endOfLine()
                .build();

        if (zipRegex.testExact(zip)) {
            mAddress.zip = zip;
        } else {
            mAddress.zip = null;
        }
    }

    /**
     * @return Whether an address has been set.
     */
    public boolean hasValidAddress() {
        return mAddress.address != null;
    }

    /**
     * @return Whether the address has been verified and is valid.
     */
    public boolean hasVerifiedAddress() {
        return mIsAddressValid;
    }

    /**
     * @return Whether a valid city has been set.
     */
    public boolean hasValidCity() {
        return mAddress.city != null;
    }

    /**
     * @return Whether a valid state has been set.
     */
    public boolean hasValidState() {
        return mAddress.stateCode != null;
    }

    /**
     * @return Whether a valid ZIP code has been set.
     */
    public boolean hasValidZip() {
        return mAddress.zip != null;
    }

    public void setIsAddressValid(boolean isAddressValid) {
        mIsAddressValid = isAddressValid;
    }
}
