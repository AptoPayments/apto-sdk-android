package com.shiftpayments.link.sdk.ui.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.ui.models.AbstractActivityModel;

/*
 * Generic {@link UserDataModel} implementation.
 * @author Adrian
 */

public abstract class AbstractUserDataModel extends AbstractActivityModel implements UserDataModel {

    protected DataPointList mBase;

    /** {@inheritDoc} */
    @Override
    public DataPointList getBaseData() {
        return mBase;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointList base) {
        mBase = base;
    }
}
