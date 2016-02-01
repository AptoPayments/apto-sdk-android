package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.vos.UserDataVo;

/**
 * Generic {@link UserDataModel} implementation.
 * @author Wijnand
 */
public abstract class AbstractUserDataModel implements UserDataModel {

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
