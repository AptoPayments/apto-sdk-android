package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java8.util.concurrent.CompletableFuture;
import me.ledge.common.models.countries.Usa;
import me.ledge.common.models.countries.UsaState;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.tasks.AddressVerificationTask;
import me.ledge.link.sdk.ui.views.userdata.AddressView;

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
    private AddressVerificationTask mAddressVerificationTask;

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
        if(mAddressVerificationTask != null) {
            mAddressVerificationTask.cancel(true);
        }
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

        if(!mIsStrictAddressValidationEnabled || mModel.hasVerifiedAddress()) {
            validateData();
        }
        else {
            mView.showLoading(true);
            startAddressVerification(e -> {
                mActivity.runOnUiThread(() -> {
                    mView.showLoading(false);
                    if (e == null) {
                        if(mModel.hasVerifiedAddress()) {
                            validateData();
                        }
                        else {
                            mView.updateAddressError(true, R.string.address_address_error);
                        }
                    } else {
                        mView.displayErrorMessage(e.getMessage());
                    }
                });
            });
        }
    }

    private void validateData() {
        if (mModel.hasAllData()) {
            saveData();
            mDelegate.addressStored();
        }
        else {
            updateErrorLabels();
        }
    }

    private void updateErrorLabels() {
        mView.updateAddressError(!mModel.hasValidAddress(), R.string.address_address_error);
        mView.updateCityError(!mModel.hasValidCity(), R.string.address_city_error);
        mView.updateStateError(!mModel.hasValidState(), R.string.address_state_error);
        mView.updateZipError(!mModel.hasValidZip(), R.string.address_zip_code_error);
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }

    private void startAddressVerification(VerifyAddressCallback callback) {
        mAddressVerificationTask = new AddressVerificationTask(mActivity, mModel, callback);
        mAddressVerificationTask.execute(mView.getAddress());
    }

    @Override
    public void onAddressLostFocus() {
        startAddressVerification(e -> {});
    }
}
