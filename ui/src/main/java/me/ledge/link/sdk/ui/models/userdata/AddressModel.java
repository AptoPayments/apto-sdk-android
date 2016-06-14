package me.ledge.link.sdk.ui.models.userdata;

import android.text.TextUtils;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import ru.lanwen.verbalregex.VerbalExpression;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class AddressModel extends AbstractUserDataModel implements UserDataModel {

    private String mAddress;
    private String mApartmentNumber;
    private String mCity;
    private String mState;
    private String mZip;
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
        mAddress = null;
        mApartmentNumber = null;
        mCity = null;
        mState = null;
        mZip = null;
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
    public UserDataVo getBaseData() {
        UserDataVo base = super.getBaseData();
        base.address = getAddress();
        base.apartmentNumber = getApartmentNumber();
        base.city = getCity();
        base.state = getState();
        base.zip = getZip();
        base.housingType = getHousingType();

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
        setHousingType(base.housingType);
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
        VerbalExpression addressRegex = VerbalExpression.regex()
                .startOfLine()
                .digit().atLeast(1)
                .space()
                .anythingBut(" ").atLeast(2)
                .anything()
                .endOfLine()
                .build();

        if (addressRegex.test(address)) {
            mAddress = address;
        } else {
            mAddress = null;
        }
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
        if (TextUtils.isEmpty(city)) {
            mCity = null;
        } else {
            mCity = city;
        }
    }

    /**
     * @return State abbreviation.
     */
    public String getState() {
        return mState;
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
            mState = state.toUpperCase();
        } else {
            mState = null;
        }
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
            mZip = zip;
        } else {
            mZip = null;
        }
    }

    /**
     * @return Whether a valid address has been set.
     */
    public boolean hasValidAddress() {
        return mAddress != null;
    }

    /**
     * @return Whether a valid city has been set.
     */
    public boolean hasValidCity() {
        return mCity != null;
    }

    /**
     * @return Whether a valid state has been set.
     */
    public boolean hasValidState() {
        return mState != null;
    }

    /**
     * @return Whether a valid ZIP code has been set.
     */
    public boolean hasValidZip() {
        return mZip != null;
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
