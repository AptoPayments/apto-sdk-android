package me.ledge.link.sdk.ui.presenters.offers;

import android.app.Activity;

import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;

/**
 * Created by adrian on 29/12/2016.
 */

public class OffersListModule extends LedgeBaseModule implements OffersListDelegate{
    private static OffersListModule mInstance;
    public Command onUpdateUserProfile;
    public Command onBack;

    public static synchronized  OffersListModule getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new OffersListModule(activity);
        }
        return mInstance;
    }

    private OffersListModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(OffersListActivity.class);
    }

    @Override
    public void onUpdateUserProfile() {
        onUpdateUserProfile.execute();
    }

    @Override
    public void onBackPressed() {
        onBack.execute();
    }

    @Override
    public void onApplicationReceived() {
        startActivity(IntermediateLoanApplicationActivity.class);
    }
}
