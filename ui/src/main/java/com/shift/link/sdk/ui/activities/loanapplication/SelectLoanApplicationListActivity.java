package com.shift.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shift.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListDelegate;
import com.shift.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.loanapplication.SelectPendingApplicationListView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.loanapplication.SelectLoanApplicationListModel;
import com.shift.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListDelegate;
import com.shift.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.loanapplication.SelectPendingApplicationListView;


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
