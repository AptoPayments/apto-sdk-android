package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.link.TermsActivity;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class LinkModule extends LedgeBaseModule {

    public LinkModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(TermsActivity.class);
    }

    @Override
    public void onClose() {
        this.onBack();
    }

    public void onFinish(int result) {
        this.getActivity().setResult(result);
        startModule(new UserDataCollectorModule(this.getActivity()));
    }
}
