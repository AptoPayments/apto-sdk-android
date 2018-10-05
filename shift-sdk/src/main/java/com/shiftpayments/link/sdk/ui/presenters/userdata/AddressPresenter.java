package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.userdata.AddressView;

import java8.util.concurrent.CompletableFuture;
import me.ledge.common.models.countries.Usa;
import me.ledge.common.models.countries.UsaState;

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
    private LoadingSpinnerManager mLoadingSpinnerManager;

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
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
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
        mLoadingSpinnerManager.showLoading(false);
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
            mLoadingSpinnerManager.showLoading(true);
            startAddressVerification(()-> mActivity.runOnUiThread(() -> {
                if(mModel.hasVerifiedAddress()) {
                    validateData();
                }
                else {
                    mView.updateAddressError(true, R.string.address_address_error);
                }
            }));
        }
    }

    private void validateData() {
        if (mModel.hasValidData()) {
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
        mView.updateZipError(!mModel.hasValidZip(), R.string.address_zip_error);
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }

    private void startAddressVerification(AddressVerificationCallback callback) {
        /*String address = String.format("%s,+%s,+%s,+%s",
                mView.getAddress(), mModel.getCity(), mModel.getState(), mModel.getZip());
        String formattedAddress = address.replace(' ', '+');
        new GeocodingHandler().reverseGeocode(mActivity, formattedAddress, mModel.getCountry(),
                response -> {
                    mLoadingSpinnerManager.showLoading(false);
                    boolean isValidAddress = false;

                    if (response != null && !response.getResults().isEmpty()) {
                        ResultVo result = response.getResults().get(0);
                        isValidAddress = !result.getFormattedAddress().isEmpty();
                    }
                    mModel.setIsAddressValid(isValidAddress);
                    if(callback != null) {
                        callback.execute();
                    }
                },
                ex -> {
                    mLoadingSpinnerManager.showLoading(false);
                    ApiErrorUtil.showErrorMessage(ex, mActivity);
                });*/
    }

    @Override
    public void onAddressLostFocus() {
        startAddressVerification(()-> mActivity.runOnUiThread(
                () -> mView.updateAddressError(mModel.hasValidAddress(), R.string.address_address_error)));
    }
}

interface AddressVerificationCallback {
    void execute();
}

