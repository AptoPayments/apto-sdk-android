package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.CreditScoreDelegate;
import com.shift.link.sdk.ui.presenters.userdata.CreditScorePresenter;
import com.shift.link.sdk.ui.views.userdata.CreditScoreView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.CreditScoreModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.CreditScoreDelegate;
import com.shift.link.sdk.ui.presenters.userdata.CreditScorePresenter;
import com.shift.link.sdk.ui.views.userdata.CreditScoreView;

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
    protected CreditScorePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof CreditScoreDelegate) {
            return new CreditScorePresenter(this, (CreditScoreDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement CreditScoreDelegate!");
        }
    }
}
