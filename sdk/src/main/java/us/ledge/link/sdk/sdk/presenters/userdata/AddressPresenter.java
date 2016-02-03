package us.ledge.link.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.models.userdata.AddressModel;
import us.ledge.link.sdk.sdk.presenters.Presenter;
import us.ledge.link.sdk.sdk.views.userdata.AddressView;
import us.ledge.link.sdk.sdk.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView>
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
    public void attachView(AddressView view) {
        super.attachView(view);

        // Set data.
        mView.setAddress(mModel.getAddress());
        mView.setApartment(mModel.getApartmentNumber());
        mView.setCity(mModel.getCity());
        mView.setState(mModel.getState());
        mView.setZipCode(mModel.getZip());

        mView.setListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Store data.
        mModel.setAddress(mView.getAddress());
        mModel.setApartmentNumber(mView.getApartment());
        mModel.setCity(mView.getCity());
        mModel.setState(mView.getState());
        mModel.setZip(mView.getZipCode());

        // Validate data.
        mView.updateAddressError(!mModel.hasValidAddress(), R.string.address_address_error);
        mView.updateCityError(!mModel.hasValidCity(), R.string.address_city_error);
        mView.updateStateError(!mModel.hasValidState(), R.string.address_state_error);
        mView.updateZipError(!mModel.hasValidZip(), R.string.address_zip_code_error);

        super.nextClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }
}
