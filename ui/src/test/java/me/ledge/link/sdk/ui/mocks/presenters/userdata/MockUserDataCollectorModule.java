package me.ledge.link.sdk.ui.mocks.presenters.userdata;

import android.app.Activity;

import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomeDelegate;

public class MockUserDataCollectorModule implements MonthlyIncomeDelegate {

    public MockUserDataCollectorModule(Activity activity) {

    }

    @Override
    public void monthlyIncomeStored() {
        // Do nothing.
    }

    @Override
    public void monthlyIncomeOnBackPressed() {
        // Do nothing.
    }
}
