package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.CreditScoreModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.CreditScoreDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.CreditScorePresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.CreditScoreView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.CreditScoreDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.CreditScorePresenter;

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
