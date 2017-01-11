package me.ledge.link.sdk.ui;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by adrian on 29/12/2016.
 */

public abstract class LedgeBaseModule implements Router {

    private Activity mActivity;

    public LedgeBaseModule(Activity activity) {
        mActivity = activity;
    }

    public abstract void initialModuleSetup();

    protected void startModule(LedgeBaseModule module) {
        module.initialModuleSetup();
    }

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * Starts another activity.
     * @param activity The Activity to start.
     */
    @Override
    public void startActivity(Class activity) {
        if (activity != null) {
            mActivity.startActivity(getStartIntent(activity));
        }
        mActivity.finish();
    }

    /**
     * @param activity The Activity to start.
     * @return The {@link Intent} to use to start the next Activity.
     */
    private Intent getStartIntent(Class activity) {
        return new Intent(mActivity, activity);
    }
}
