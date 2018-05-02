package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.MonthlyIncomeModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.MonthlyIncomeDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.MonthlyIncomeView;

/**
 * Wires up the MVP pattern for the monthly income screen.
 * @author Wijnand
 */
public class MonthlyIncomeActivity
        extends UserDataActivity<MonthlyIncomeModel, MonthlyIncomeView, MonthlyIncomePresenter> {

    /** {@inheritDoc} */
    @Override
    protected MonthlyIncomeView createView() {
        return (MonthlyIncomeView) View.inflate(this, R.layout.act_income_monthly, null);
    }

    /** {@inheritDoc} */
    @Override
    protected MonthlyIncomePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof MonthlyIncomeDelegate) {
            return new MonthlyIncomePresenter(this, (MonthlyIncomeDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement MonthlyIncomeDelegate!");
        }
    }
}
