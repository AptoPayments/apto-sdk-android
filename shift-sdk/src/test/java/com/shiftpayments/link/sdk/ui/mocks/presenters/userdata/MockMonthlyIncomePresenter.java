package com.shiftpayments.link.sdk.ui.mocks.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.presenters.userdata.MonthlyIncomeDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;

public class MockMonthlyIncomePresenter extends MonthlyIncomePresenter {

    public MockMonthlyIncomePresenter(AppCompatActivity activity, MonthlyIncomeDelegate delegate) {
        super(activity, delegate);
    }

    @Override
    protected void setupToolbar() {
        // Do nothing.
    }

    public void setMultiplier(int multiplier) {
        mIncomeMultiplier = multiplier;
    }

}
