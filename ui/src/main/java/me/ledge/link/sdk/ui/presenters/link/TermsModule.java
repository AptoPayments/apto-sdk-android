package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.link.TermsActivity;

/**
 * Created by adrian on 29/12/2016.
 */

public class TermsModule extends LedgeBaseModule implements TermsDelegate {

    private static TermsModule mInstance;
    public Command onFinish;
    public Command onBack;


    public static synchronized TermsModule getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new TermsModule(activity);
        }
        return mInstance;
    }

    private TermsModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(TermsActivity.class);
    }

    @Override
    public void termsPresented() {
        onFinish.execute();
    }

    @Override
    public void termsOnBackPressed() {
        onBack.execute();
    }
}
