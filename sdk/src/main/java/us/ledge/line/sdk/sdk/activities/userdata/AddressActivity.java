package us.ledge.line.sdk.sdk.activities.userdata;

import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.MvpActivity;
import us.ledge.line.sdk.sdk.models.userdata.AddressModel;
import us.ledge.line.sdk.sdk.presenters.userdata.AddressPresenter;
import us.ledge.line.sdk.sdk.views.userdata.AddressView;

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
