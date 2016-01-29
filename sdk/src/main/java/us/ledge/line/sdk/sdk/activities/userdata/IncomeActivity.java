package us.ledge.line.sdk.sdk.activities.userdata;

import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.MvpActivity;
import us.ledge.line.sdk.sdk.models.userdata.IncomeModel;
import us.ledge.line.sdk.sdk.presenters.userdata.IncomePresenter;
import us.ledge.line.sdk.sdk.views.userdata.IncomeView;

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
