package me.ledge.link.sdk.ui;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;

import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public abstract class LedgeBaseModule implements Router, BaseDelegate {

    private Activity mActivity;

    public LedgeBaseModule(Activity activity) {
        mActivity = activity;
    }

    public abstract void initialModuleSetup();

    protected void startModule(LedgeBaseModule module) {
        WeakReference<LedgeBaseModule> moduleWeakReference = new WeakReference<LedgeBaseModule>(module);
        ModuleManager.getInstance().setModule(moduleWeakReference);
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
