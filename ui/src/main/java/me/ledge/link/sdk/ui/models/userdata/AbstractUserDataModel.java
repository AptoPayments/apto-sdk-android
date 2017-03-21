package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;

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
