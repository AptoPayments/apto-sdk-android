package com.shiftpayments.link.sdk.ui.activities.custodianselector;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.custodianselector.AddCustodianListModel;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.AddCustodianListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.AddCustodianListPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.custodianselector.AddCustodianListView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.AddCustodianListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.AddCustodianListPresenter;


/**
 * Wires up the MVP pattern for the screen that shows the custodians list.
 * @author Adrian
 */
public class AddCustodianListActivity
        extends MvpActivity<AddCustodianListModel, AddCustodianListView, AddCustodianListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddCustodianListView createView() {
        return (AddCustodianListView) View.inflate(this, R.layout.act_add_custodian, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddCustodianListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddCustodianListDelegate) {
            return new AddCustodianListPresenter(this, (AddCustodianListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddCustodianListDelegate!");
        }
    }
}
