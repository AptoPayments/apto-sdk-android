package com.shiftpayments.link.sdk.ui.activities.card;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.TransactionDetailsPresenter;
import com.shiftpayments.link.sdk.ui.views.card.TransactionDetailsView;


/**
 * Created by adrian on 27/11/2017.
 */

public class TransactionDetailsActivity extends FragmentMvpActivity {

    public static final String EXTRA_TRANSACTION = "com.shiftpayments.link.sdk.ui.presenters.card.TRANSACTION";

    /** {@inheritDoc} */
    @Override
    protected TransactionDetailsView createView() {
        return (TransactionDetailsView) View.inflate(this, R.layout.act_transaction_details, null);
    }

    /** {@inheritDoc} */
    @Override
    protected TransactionDetailsPresenter createPresenter(BaseDelegate delegate) {
        Intent intent = getIntent();
        TransactionVo transactionVo = intent.getParcelableExtra(EXTRA_TRANSACTION);
        return new TransactionDetailsPresenter(this, transactionVo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
