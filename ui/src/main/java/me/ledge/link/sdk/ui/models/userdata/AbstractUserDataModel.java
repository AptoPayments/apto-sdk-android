package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;

/*
 * Generic {@link UserDataModel} implementation.
 * @author Adrian
 */

public abstract class AbstractUserDataModel extends AbstractActivityModel implements UserDataModel {

    protected DataPointVo.DataPointList mBase;

    /** {@inheritDoc} */
    @Override
    public DataPointVo.DataPointList getBaseData() {
        return mBase;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(DataPointVo.DataPointList base) {
        mBase = base;
    }
}
