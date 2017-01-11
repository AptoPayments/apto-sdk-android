package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AnnualIncomeModel;
import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.views.userdata.AnnualIncomeView;

/**
 * Wires up the MVP pattern for the income screen.
 * @author Wijnand
 */
public class AnnualIncomeActivity
        extends UserDataActivity<AnnualIncomeModel, AnnualIncomeView, AnnualIncomePresenter> {

    /** {@inheritDoc} */
    @Override
    protected AnnualIncomeView createView() {
        return (AnnualIncomeView) View.inflate(this, R.layout.act_income_annual, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AnnualIncomePresenter createPresenter() {
        return new AnnualIncomePresenter(this, UserDataCollectorModule.getInstance(this));
    }
}
