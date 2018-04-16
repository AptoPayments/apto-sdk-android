package com.shift.link.sdk.ui.models.userdata;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.TimeAtAddress;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressModel extends AbstractUserDataModel {

    public static final int DEFAULT_INDICATION = -1;

    private TimeAtAddress mTimeAtAddress;

    /**
     * Creates a new {@link TimeAtAddressModel} instance.
     */
    public TimeAtAddressModel() {
        super();
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mTimeAtAddress = new TimeAtAddress();
    }

    /**
     * @param indication The time address range to check.
     * @return Whether this is a valid time at address range.
     */
    private boolean isValidRange(int indication) {
        return indication >= 0;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return isValidRange(mTimeAtAddress.timeAtAddressRange);
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.time_at_address_label;
    }

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        DataPointList base = super.getBaseData();
        TimeAtAddress baseTimeAtAddress = (TimeAtAddress) base.getUniqueDataPoint(
                DataPointVo.DataPointType.TimeAtAddress, new TimeAtAddress());
        baseTimeAtAddress.timeAtAddressRange = getTimeAtAddressRange();
        return base;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        super.setBaseData(base);
        TimeAtAddress baseTimeAtAddress = (TimeAtAddress) base.getUniqueDataPoint(
                DataPointVo.DataPointType.TimeAtAddress, null);
        if(baseTimeAtAddress!=null) {
            setTimeAtAddressRange(baseTimeAtAddress.timeAtAddressRange);
        }
    }

    /**
     * @return Time at address range.
     */
    public int getTimeAtAddressRange() {
        return mTimeAtAddress.timeAtAddressRange;
    }

    /**
     * Stores a new time at address range.
     * @param indication New range.
     */
    public void setTimeAtAddressRange(int indication) {
        if (isValidRange(indication)) {
            mTimeAtAddress.timeAtAddressRange = indication;
        } else {
            mTimeAtAddress.timeAtAddressRange = DEFAULT_INDICATION;
        }
    }
}
