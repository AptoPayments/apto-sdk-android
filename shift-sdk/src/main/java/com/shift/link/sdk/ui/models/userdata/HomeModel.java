package com.shift.link.sdk.ui.models.userdata;

import android.text.TextUtils;

import com.shift.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shift.link.sdk.api.vos.datapoints.Address;
import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.Housing;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.Model;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Concrete {@link Model} for the address validation screen.
 * @author Adrian
 */
public class HomeModel extends AbstractUserDataModel implements UserDataModel {

    private Address mAddress;
    private IdDescriptionPairDisplayVo mHousingType;

    /**
     * Creates a new {@link HomeModel} instance.
     */
    public HomeModel() {
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
        return R.string.home_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return hasValidZip() && hasValidHousingType();
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        if(hasValidZip()) {
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
