package com.shift.link.sdk.ui.activities.custodianselector;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.custodianselector.AddCustodianListModel;
import com.shift.link.sdk.ui.presenters.custodianselector.AddCustodianListDelegate;
import com.shift.link.sdk.ui.presenters.custodianselector.AddCustodianListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.custodianselector.AddCustodianListView;


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
