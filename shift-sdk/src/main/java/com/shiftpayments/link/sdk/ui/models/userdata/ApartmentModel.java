package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the apartment screen.
 * @author Wijnand
 */
public class ApartmentModel extends AbstractUserDataModel implements UserDataModel {

    private Address mAddress;

    /**
     * Creates a new {@link ApartmentModel} instance.
     */
    public ApartmentModel() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mAddress = new Address();
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.apartment_title;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasValidData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();

        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());
        baseAddress.update(mAddress);

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

    public void setAddress(Address address) {
        mAddress = address;
    }

    public String getAddress() {
        return mAddress.toString();
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
}
