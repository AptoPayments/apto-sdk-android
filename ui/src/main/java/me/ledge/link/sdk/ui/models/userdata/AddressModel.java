package me.ledge.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class AddressModel extends AbstractUserDataModel implements UserDataModel {

    private DataPointVo.Address mAddress;
    private IdDescriptionPairDisplayVo mHousingType;

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
        mAddress = new DataPointVo.Address();
        mHousingType = null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidAddress() && hasValidCity() && hasValidState() && hasValidZip() && hasValidHousingType();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        DataPointVo.Address baseAddress = (DataPointVo.Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new DataPointVo.Address());
        baseAddress.update(getAddress());

        DataPointVo.Housing baseHousing = (DataPointVo.Housing) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Housing, new DataPointVo.Housing());
        baseHousing.housingType = getHousingType().getKey();

        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);

        DataPointVo.Address address = (DataPointVo.Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address,
                new DataPointVo.Address());
        setAddress(address);

        DataPointVo.Housing housing = (DataPointVo.Housing) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Housing,
                new DataPointVo.Housing());
        setHousingType(new IdDescriptionPairDisplayVo(housing.housingType, ""));
    }

    /**
     * @return Address.
     */
    public DataPointVo.Address getAddress() {
        return mAddress;
    }

    public void setAddress(DataPointVo.Address address) {
        setStreetAddress(address.address);
        setApartmentNumber(address.apUnit);
        setCity(address.city);
        setState(address.stateCode);
        setZip(address.zip);
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
     * @return Whether a valid address has been set.
     */
    public boolean hasValidAddress() {
        return mAddress.address != null;
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
