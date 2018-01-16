package me.ledge.link.sdk.ui.workflow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public abstract class LedgeBaseModule implements NavigationCommand, BaseDelegate {

    private Activity mActivity;
    public Command onBack;
    public Command onFinish;
    private ProgressDialog mProgressDialog;

    public LedgeBaseModule(Activity activity) {
        mActivity = activity;
    }

    public abstract void initialModuleSetup();

    protected void startModule(LedgeBaseModule module) {
        WeakReference<LedgeBaseModule> moduleWeakReference = new WeakReference<>(module);
        ModuleManager.getInstance().setModule(moduleWeakReference);
        module.initialModuleSetup();
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
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

    protected void showLoading(boolean show) {
        if(mActivity.isFinishing())
        {
            return;
        }
        mActivity.runOnUiThread(() -> {
            if (show) {
                mProgressDialog = ProgressDialog.show(mActivity,null,null);
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mProgressDialog.setContentView(R.layout.include_rl_loading_transparent);
            } else {
                if(mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }
}
