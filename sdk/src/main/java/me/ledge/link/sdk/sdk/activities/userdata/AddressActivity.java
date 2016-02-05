package me.ledge.link.sdk.sdk.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.MvpActivity;
import me.ledge.link.sdk.sdk.models.userdata.AddressModel;
import me.ledge.link.sdk.sdk.presenters.userdata.AddressPresenter;
import me.ledge.link.sdk.sdk.views.userdata.AddressView;

/**
 * Wires up the MVP pattern for the address screen.
 * @author Wijnand
 */
public class AddressActivity
        extends MvpActivity<AddressModel, AddressView, AddressPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddressView createView() {
        return (AddressView) View.inflate(this, R.layout.act_address, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddressPresenter createPresenter() {
        return new AddressPresenter(this);
    }
}
