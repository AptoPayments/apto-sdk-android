package com.shift.link.sdk.ui.models.userdata;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.ui.models.AbstractActivityModel;

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
