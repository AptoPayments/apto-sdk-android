package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.presenters.offers.OffersListModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;

/**
 * Created by adrian on 29/12/2016.
 */


    private LoanInfoModule mLoanInfoModule;
    private UserDataCollectorModule mUserDataCollectorModule;
    private OffersListModule mOffersListModule;

    public LinkModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        showLoanInfo();
    }

    public void showLoanInfo() {
        mLoanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        mLoanInfoModule.onFinish = this::showUserDataCollector;
        startModule(mLoanInfoModule);
    }

    public void showUserDataCollector() {
        mUserDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        mUserDataCollectorModule.onFinish = this::showOffersList;
        startModule(mUserDataCollectorModule);
    }

    public void showOffersList() {
        mOffersListModule = OffersListModule.getInstance(this.getActivity());
        mOffersListModule.onUpdateUserProfile = this::showUserDataCollector;
        mOffersListModule.onBack = this::showLoanInfo;
        startModule(mOffersListModule);
    }
}
