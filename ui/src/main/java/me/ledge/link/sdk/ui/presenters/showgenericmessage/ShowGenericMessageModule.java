package me.ledge.link.sdk.ui.presenters.showgenericmessage;

import android.app.Activity;

import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;
import me.ledge.link.sdk.ui.activities.showgenericmessage.ShowGenericMessageActivity;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;

public class ShowGenericMessageModule extends LedgeBaseModule implements ShowGenericMessageDelegate {

    private static ShowGenericMessageModule mInstance;
    private static GenericMessageConfigurationVo mConfig;

    public static synchronized ShowGenericMessageModule getInstance(Activity activity, GenericMessageConfigurationVo actionConfig) {
        mConfig = actionConfig;
        if (mInstance == null) {
            mInstance = new ShowGenericMessageModule(activity);
        }
        return mInstance;
    }

    private ShowGenericMessageModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(ShowGenericMessageActivity.class);
    }

    @Override
    public void showGenericMessageScreenOnNextPressed() {
        onFinish.execute();
    }

    @Override
    public void showGenericMessageScreenOnBackPressed() {
        onBack.execute();
    }

    public GenericMessageConfigurationVo getConfig() {
        return mConfig;
    }
}
