package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.utils.TimeAtAddressRange;
import me.ledge.link.api.vos.datapoints.TimeAtAddress;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressModel extends AbstractUserDataModel {

    public static final int DEFAULT_INDICATION = 0;

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
        mTimeAtAddress = new TimeAtAddress(DEFAULT_INDICATION, false, false);
    }

    /**
     * @param indication The credit score range to check.
     * @return Whether this is a valid credit score range.
     */
    private boolean isValidRange(int indication) {
        return indication >= TimeAtAddressRange.LESS_THAN_SIX_MONTHS && indication <= TimeAtAddressRange.MORE_THAN_TWO_YEARS;
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
     * @return Credit score range.
     * @see TimeAtAddressRange
     */
    public int getTimeAtAddressRange() {
        return mTimeAtAddress.timeAtAddressRange;
    }

    /**
     * Stores a new credit score range.
     * @param indication New range.
     * @see TimeAtAddressRange
     */
    public void setTimeAtAddressRange(int indication) {
        if (isValidRange(indication)) {
            mTimeAtAddress.timeAtAddressRange = indication;
        } else {
            mTimeAtAddress.timeAtAddressRange = DEFAULT_INDICATION;
        }
    }
}
