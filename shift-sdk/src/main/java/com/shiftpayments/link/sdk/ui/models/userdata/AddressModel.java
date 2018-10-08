package com.shiftpayments.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Housing;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the address validation screen.
 * @author Adrian
 */
public class AddressModel extends AbstractUserDataModel implements UserDataModel {

    private Address mAddress;
    private IdDescriptionPairDisplayVo mHousingType;
    private String mStreet;
    private String mStreetNumber;

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
        mHousingType = null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasValidData() {
        return hasValidAddress() && hasValidHousingType();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasValidAddress()) {
            Address baseAddress = (Address) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Address, new Address());
            baseAddress.update(getAddress());
        }

        if(hasValidHousingType()) {
            Housing baseHousing = (Housing) base.getUniqueDataPoint(
                    DataPointVo.DataPointType.Housing, new Housing());
            baseHousing.housingType = getHousingType();
        }

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);

        Address address = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        if(address!=null) {
            mAddress=address;
        }

        Housing housing = (Housing) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Housing, null);
        if(housing!=null) {
            setHousingType(housing.housingType);
        }
    }

    /**
     * @return Address.
     */
    public Address getAddress() {
        return mAddress;
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
     * Stores a valid zip code.
     * @param zip Zip or postal code.
     */
    public void setZip(String zip) {
        if (TextUtils.isEmpty(zip)) {
            mAddress.zip = zip;
        } else {
            mAddress.zip = null;
        }
    }

    /**
     * Stores a valid country code.
     * @param country Country code.
     */
    public void setCountry(String country) {
        if (TextUtils.isEmpty(country)) {
            mAddress.country = null;
        } else {
            mAddress.country = country;
        }
    }

    /**
     * @return The full address
     */
    public String getFullAddress() {
        return mAddress.toString();
    }

    /**
     * Stores the street
     * @param street street
     */
    public void setStreet(String street) {

        if (TextUtils.isEmpty(street)) {
            mStreet = null;
        } else {
            mStreet = street;
            if (mStreetNumber != null && !mStreetNumber.isEmpty()) {
                setStreetAddress(mStreet, mStreetNumber);
            }
        }
    }

    /**
     * Stores the street number
     * @param streetNumber street number
     */
    public void setStreetNumber(String streetNumber) {
        if (TextUtils.isEmpty(streetNumber)) {
            mStreetNumber = null;
        } else {
            mStreetNumber = streetNumber;
            if (mStreet != null && !mStreet.isEmpty()) {
                setStreetAddress(mStreet, mStreetNumber);
            }
        }
    }

    private void setStreetAddress(String streetName, String streetNumber) {
        // TODO: format according to country
        mAddress.address = streetNumber + " " + streetName ;
    }

    /**
     * @return Whether a valid address has been set.
     */
    public boolean hasValidAddress() {
        return mAddress != null;
    }

    /**
     * @return The selected housing type.
     */
    public IdDescriptionPairDisplayVo getHousingType() {
        return mHousingType;
    }

    /**
     * Stores a new housing type.
     * @param type New housing type.
     */
    public void setHousingType(IdDescriptionPairDisplayVo type) {
        mHousingType = type;
    }

    /**
     * @return Whether a valid housing type has been selected.
     */
    public boolean hasValidHousingType() {
        return getHousingType() != null && getHousingType().getKey() >= 0;
    }
}
