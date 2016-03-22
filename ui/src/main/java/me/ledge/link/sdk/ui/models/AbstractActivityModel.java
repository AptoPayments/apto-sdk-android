package me.ledge.link.sdk.ui.models;

import android.app.Activity;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.activities.MvpActivity;

import java.util.ArrayList;

/**
 * Partial implementation of the {@link ActivityModel}.
 * @author Wijnand
 */
public abstract class AbstractActivityModel implements ActivityModel {

    /**
     * Safely tries to fetch the {@link Activity} to be started from the list.
     * @param currentActivity The currently visible {@link Activity}.
     * @param positionOffset Position offset.
     * @return The {@link Class} of the {@link Activity} to start OR {@code null} if not found.
     */
    protected Class safeGetActivityAtPosition(Activity currentActivity, int positionOffset) {
        ArrayList<Class<? extends MvpActivity>> order = LedgeLinkUi.getProcessOrder();
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
    public Class getNextActivity(Activity current) {
        return safeGetActivityAtPosition(current, 1);
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return safeGetActivityAtPosition(current, -1);
    }
}
