package me.ledge.link.sdk.ui.mocks.presenters.userdata;

import android.app.Activity;

import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomeDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomeDelegate;

public class MockUserDataCollectorModule implements MonthlyIncomeDelegate, AnnualIncomeDelegate {

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

    @Override
    public void annualIncomeStored() {
        // Do nothing.
    }

    @Override
    public void annualIncomeOnBackPressed() {
        // Do nothing.
    }
}
