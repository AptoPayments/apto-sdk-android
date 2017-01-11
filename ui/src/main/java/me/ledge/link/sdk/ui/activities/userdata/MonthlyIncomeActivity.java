package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.MonthlyIncomeModel;
import me.ledge.link.sdk.ui.presenters.userdata.MonthlyIncomePresenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.views.userdata.MonthlyIncomeView;

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
    protected MonthlyIncomePresenter createPresenter() {
        return new MonthlyIncomePresenter(this, UserDataCollectorModule.getInstance(this));
    }
}
