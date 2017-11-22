package me.ledge.link.sdk.ui.workflow;

import android.app.Activity;

import me.ledge.link.sdk.ui.activities.ActionNotSupportedActivity;

/**
 * Created by adrian on 09/11/2017.
 */

public class ActionNotSupportedModule extends LedgeBaseModule {

    public ActionNotSupportedModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(ActionNotSupportedActivity.class);
    }

}
