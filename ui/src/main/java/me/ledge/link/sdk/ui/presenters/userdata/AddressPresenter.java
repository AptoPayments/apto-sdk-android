package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.Lookup;

import java.io.IOException;

import java8.util.concurrent.CompletableFuture;
import me.ledge.common.models.countries.Usa;
import me.ledge.common.models.countries.UsaState;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.AddressView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView>
        implements AddressView.ViewListener {

    private Usa mStates;
    private AddressDelegate mDelegate;
    private boolean mIsStrictAddressValidationEnabled;

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public AddressPresenter(AppCompatActivity activity, AddressDelegate delegate) {
        super(activity);
        createStates();
        mDelegate = delegate;
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
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 3, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().isStrictAddressValidationEnabled())
                .thenAccept(this::setIsAddressValidationEnabled);

        // Create adapter.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                mActivity, R.array.usa_state_names, android.R.layout.simple_spinner_dropdown_item);
        mView.setStateSpinnerAdapter(adapter);

        // Set data.
        mView.setAddress(mModel.getStreetAddress());
        mView.setApartment(mModel.getApartmentNumber());
        mView.setCity(mModel.getCity());
        mView.setZipCode(mModel.getZip());

        mView.updateStateError(false, 0);
        mView.updateZipError(false, 0);

        UsaState state = mStates.getStateByCode(mModel.getState());
        if (state != null) {
            mView.setState(state.getName());
        }

        mView.setListener(this);
        mView.showLoading(false);
    }

    private void setIsAddressValidationEnabled(boolean isAddressValidationEnabled) {
        mIsStrictAddressValidationEnabled = isAddressValidationEnabled;
    }

    @Override
    public void onBack() {
        mDelegate.addressOnBackPressed();
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
        onAddressLostFocus();
        mView.showLoading(true);

        // Store data.
        mModel.setStreetAddress(mView.getAddress());
        mModel.setApartmentNumber(mView.getApartment());
        mModel.setCity(mView.getCity());
        mModel.setZip(mView.getZipCode());

        UsaState state = mStates.getStateByName(mView.getState());
        if (state == null) {
            mModel.setState(null);
        } else {
            mModel.setState(state.getCode());
        }
    }

    private void validateData() {
        mView.updateAddressError(!mModel.hasValidAddress(mIsStrictAddressValidationEnabled), R.string.address_address_error);
        mView.updateCityError(!mModel.hasValidCity(), R.string.address_city_error);
        mView.updateStateError(!mModel.hasValidState(), R.string.address_state_error);
        mView.updateZipError(!mModel.hasValidZip(), R.string.address_zip_code_error);

        if (mModel.hasAllData() && mModel.hasValidAddress(mIsStrictAddressValidationEnabled)) {
            saveData();
            mDelegate.addressStored();
        }
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }

    private void validateAddress(String streetAddress) throws SmartyException, IOException {
        Client client = new ClientBuilder(mActivity.getString(R.string.smarty_streets_auth_id), mActivity.getString(R.string.smarty_streets_auth_token))
                .buildUsStreetApiClient();

        Lookup lookup = new Lookup();
        lookup.setStreet(streetAddress);
        lookup.setCity(mModel.getCity());
        lookup.setState(mModel.getState());
        client.send(lookup);

        mModel.setIsAddressValid(!lookup.getResult().isEmpty());
        mActivity.runOnUiThread(()-> {
            mView.showLoading(false);
            validateData();
        });
    }

    @Override
    public void onAddressLostFocus() {
        Thread thread = new Thread(() -> {
            try  {
                validateAddress(mView.getAddress());
            } catch (Exception e) {
                mActivity.runOnUiThread(()-> mView.displayErrorMessage(e.getMessage()));
            }
        });
        thread.start();
    }
}
