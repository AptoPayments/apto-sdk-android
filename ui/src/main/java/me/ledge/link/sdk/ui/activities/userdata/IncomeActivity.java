package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.userdata.IncomeModel;
import me.ledge.link.sdk.ui.presenters.userdata.IncomePresenter;
import me.ledge.link.sdk.ui.views.userdata.IncomeView;

/**
 * Wires up the MVP pattern for the income screen.
 * @author Wijnand
 */
public class IncomeActivity
        extends MvpActivity<IncomeModel, IncomeView, IncomePresenter> {

    /** {@inheritDoc} */
    @Override
    protected IncomeView createView() {
        return (IncomeView) View.inflate(this, R.layout.act_income, null);
    }

    /** {@inheritDoc} */
    @Override
    protected IncomePresenter createPresenter() {
        return new IncomePresenter(this);
    }
}
