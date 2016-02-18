package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import me.ledge.common.models.countries.Usa;
import me.ledge.common.models.countries.UsaState;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.AddressView;
import me.ledge.link.sdk.ui.views.userdata.NextButtonListener;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView>
        implements NextButtonListener {

    private Usa mStates;

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public AddressPresenter(AppCompatActivity activity) {
        super(activity);
        createStates();
    }

    /**
     * Generates the list of USA states.
     */
    private void createStates() {
        String[] codes = mActivity.getResources().getStringArray(R.array.usa_state_codes);
        String[] names = mActivity.getResources().getStringArray(R.array.usa_state_names);
        mStates = new Usa(codes, names);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);

        // Create adapter.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                mActivity, R.array.usa_state_names, android.R.layout.simple_spinner_item);
        mView.setStateSpinnerAdapter(adapter);

        // Set data.
        mView.setAddress(mModel.getAddress());
        mView.setApartment(mModel.getApartmentNumber());
        mView.setCity(mModel.getCity());
        mView.setZipCode(mModel.getZip());

        UsaState state = mStates.getStateByCode(mModel.getState());
        if (state != null) {
            mView.setState(state.getName());
        }

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
        mModel.setZip(mView.getZipCode());

        UsaState state = mStates.getStateByName(mView.getState());
        if (state == null) {
            mModel.setState(null);
        } else {
            mModel.setState(state.getCode());
        }

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
