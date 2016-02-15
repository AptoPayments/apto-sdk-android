package me.ledge.link.sdk.sdk.models.userdata;

import android.app.Activity;
import me.ledge.link.sdk.sdk.LedgeLinkUi;
import me.ledge.link.sdk.sdk.vos.UserDataVo;

import java.util.ArrayList;

/**
 * Generic {@link UserDataModel} implementation.
 * @author Wijnand
 */
public abstract class AbstractUserDataModel implements UserDataModel {

    protected UserDataVo mBase;

    /**
     * Safely tries to fetch the {@link Activity} to be started from the list.
     * @param currentActivity The currently visible {@link Activity}.
     * @param positionOffset Position offset.
     * @return The {@link Class} of the {@link Activity} to start OR {@code null} if not found.
     */
    private Class safeGetActivityAtPosition(Activity currentActivity, int positionOffset) {
        ArrayList<Class<?>> order = LedgeLinkUi.getProcessOrder();
        int currentIndex = order.indexOf(currentActivity.getClass());
        int targetIndex = currentIndex + positionOffset;
        Class result = null;

        if (targetIndex >= 0 && targetIndex < order.size()) {
            result = LedgeLinkUi.getProcessOrder().get(targetIndex);
        }

        return result;
    }

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

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return safeGetActivityAtPosition(current, 1);
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return safeGetActivityAtPosition(current, -1);
    }
}
