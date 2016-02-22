package me.ledge.link.sdk.ui.models.userdata;

import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Generic {@link UserDataModel} implementation.
 * @author Wijnand
 */
public abstract class AbstractUserDataModel extends AbstractActivityModel implements UserDataModel {

    protected UserDataVo mBase;

    /** {@inheritDoc} */
    @Override
    public UserDataVo getBaseData() {
        return mBase;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseData(UserDataVo base) {
        mBase = base;
    }
}
