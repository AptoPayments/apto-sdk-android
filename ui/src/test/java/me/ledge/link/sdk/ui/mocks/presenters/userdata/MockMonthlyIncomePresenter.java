package me.ledge.link.sdk.ui.mocks.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;

public class MockMonthlyIncomePresenter extends MonthlyIncomePresenter {

    public MockMonthlyIncomePresenter(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected void setupToolbar() {
        // Do nothing.
    }

    @Override
    public void startNextActivity() {
        // Do nothing.
    }
}
