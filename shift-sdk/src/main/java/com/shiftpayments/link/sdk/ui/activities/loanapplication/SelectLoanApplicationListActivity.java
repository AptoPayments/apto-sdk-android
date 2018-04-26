package com.shiftpayments.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationListModel;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.loanapplication.SelectPendingApplicationListView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListPresenter;


/**
 * Wires up the MVP pattern for financial account list selection.
 * @author Adrian
 */
public class SelectLoanApplicationListActivity
        extends MvpActivity<SelectLoanApplicationListModel, SelectPendingApplicationListView, SelectLoanApplicationListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected SelectPendingApplicationListView createView() {
        return (SelectPendingApplicationListView) View.inflate(this, R.layout.act_select_pending_application, null);
    }

    /** {@inheritDoc} */
    @Override
    protected SelectLoanApplicationListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof SelectLoanApplicationListDelegate) {
            return new SelectLoanApplicationListPresenter(this, (SelectLoanApplicationListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement SelectLoanApplicationListDelegate!");
        }
    }
}
