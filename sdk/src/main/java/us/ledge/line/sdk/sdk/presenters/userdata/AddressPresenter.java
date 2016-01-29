package us.ledge.line.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import us.ledge.line.sdk.sdk.models.userdata.AddressModel;
import us.ledge.line.sdk.sdk.presenters.ActivityPresenter;
import us.ledge.line.sdk.sdk.presenters.Presenter;
import us.ledge.line.sdk.sdk.views.userdata.AddressView;
import us.ledge.line.sdk.sdk.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends ActivityPresenter<AddressModel, AddressView>
        implements NextButtonListener {

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public AddressPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // TODO
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }
}
