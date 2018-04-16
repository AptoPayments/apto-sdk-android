package com.shift.link.sdk.ui.mocks.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shift.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shift.link.sdk.ui.presenters.userdata.AnnualIncomeDelegate;
import com.shift.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;

public class MockAnnualIncomePresenter extends AnnualIncomePresenter {

    public MockAnnualIncomePresenter(AppCompatActivity activity, AnnualIncomeDelegate delegate) {
        super(activity, delegate);
        mResponseHandler = new MockResponseHandler();
    }

    @Override
    protected void setupToolbar() {
        // Do nothing.
    }

    public void setModelMaxIncome(int maxIncome) {
        mModel.setMaxIncome(maxIncome);
    }

    public void setMultiplier(int multiplier) {
        mIncomeMultiplier = multiplier;
    }

}
