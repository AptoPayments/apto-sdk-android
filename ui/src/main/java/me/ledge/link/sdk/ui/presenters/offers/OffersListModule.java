package me.ledge.link.sdk.ui.presenters.offers;

import android.app.Activity;

import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.presenters.link.LinkModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class OffersListModule extends LedgeBaseModule implements OffersListDelegate{

    public OffersListModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(OffersListActivity.class);
    }

    @Override
    public void onClose() {
        this.onBack();
    }

    public void onFinish(int result) {
        this.getActivity().setResult(result);
        startModule(new LinkModule(this.getActivity()));
    }

    @Override
    public void onUpdateUserProfile() {
        startModule(new UserDataCollectorModule(this.getActivity()));
    }
}
