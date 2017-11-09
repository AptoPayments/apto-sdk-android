package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.link.WelcomeActivity;

public class WelcomeModule extends LedgeBaseModule implements WelcomeDelegate {

    private static WelcomeModule mInstance;
    public Command onFinish;
    public Command onBack;


    public static synchronized WelcomeModule getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new WelcomeModule(activity);
        }
        return mInstance;
    }

    private WelcomeModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(WelcomeActivity.class);
    }

    @Override
    public void welcomeScreenOnNextPressed() {
        onFinish.execute();
    }

    @Override
    public void welcomeScreenOnBackPressed() {
        onBack.execute();
    }
}
