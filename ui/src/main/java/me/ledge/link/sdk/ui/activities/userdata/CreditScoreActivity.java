package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.CreditScoreModel;
import me.ledge.link.sdk.ui.presenters.userdata.CreditScorePresenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.views.userdata.CreditScoreView;

/**
 * Wires up the MVP pattern for the credit score screen.
 * @author wijnand
 */
public class CreditScoreActivity extends UserDataActivity<CreditScoreModel, CreditScoreView, CreditScorePresenter> {

    /** {@inheritDoc} */
    @Override
    protected CreditScoreView createView() {
        return (CreditScoreView) View.inflate(this, R.layout.act_credit_score, null);
    }

    /** {@inheritDoc} */
    @Override
    protected CreditScorePresenter createPresenter() {
        return new CreditScorePresenter(this, UserDataCollectorModule.getInstance(this));
    }
}
