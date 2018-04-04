package me.ledge.link.sdk.ui.activities.custodianselector;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.custodianselector.AddCustodianListModel;
import me.ledge.link.sdk.ui.presenters.custodianselector.AddCustodianListDelegate;
import me.ledge.link.sdk.ui.presenters.custodianselector.AddCustodianListPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.custodianselector.AddCustodianListView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(UIStorage.getInstance().getStatusBarColor());
        }
    }
}
