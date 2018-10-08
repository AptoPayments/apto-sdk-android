package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.HousingTypeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.geocoding.handlers.GeocodingHandler;
import com.shiftpayments.link.sdk.ui.geocoding.handlers.GooglePlacesArrayAdapter;
import com.shiftpayments.link.sdk.ui.geocoding.vos.AddressComponentVo;
import com.shiftpayments.link.sdk.ui.geocoding.vos.ResultVo;
import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.userdata.AddressView;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import java.util.ArrayList;

import java8.util.concurrent.CompletableFuture;

import static com.shiftpayments.link.sdk.ui.geocoding.handlers.GooglePlacesArrayAdapter.GOOGLE_PLACES_TAG;

/**
 * Concrete {@link Presenter} for the address validation screen.
 * @author Adrian
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView> implements AddressView.ViewListener {

    private HintArrayAdapter<IdDescriptionPairDisplayVo> mHousingTypeAdapter;
    private AddressDelegate mDelegate;
    private boolean mIsHousingTypeRequired;
    private LoadingSpinnerManager mLoadingSpinnerManager;
    private boolean mIsNextClickHandlerPending = false;
    private GeocodingHandler mGeocodingHandler;
    private GooglePlacesArrayAdapter mGooglePlacesArrayAdapter;
    private ArrayList<String> mAllowedCountries;

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public AddressPresenter(AppCompatActivity activity, AddressDelegate delegate, ArrayList<String> allowedCountries) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
        mIsHousingTypeRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.Housing));
        mAllowedCountries = allowedCountries;
    }

    /**
     * @param typesList List of housing types.
     * @return Adapter used to display the list of housing types.
     */
    private HintArrayAdapter<IdDescriptionPairDisplayVo> generateHousingTypeAdapter(HousingTypeVo[] typesList) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(mActivity, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint
                = new IdDescriptionPairDisplayVo(-1, mActivity.getString(R.string.address_housing_type_hint));

        adapter.add(hint);

        if (typesList != null) {
            for (HousingTypeVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.housing_type_id, type.description));
            }
        }

        return adapter;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);

        // Set data.
        mView.setAddress(mModel.getFullAddress());

        // Create the adapter and set it to the AutoCompleteTextView
        mGooglePlacesArrayAdapter = new GooglePlacesArrayAdapter(mActivity, android.R.layout.simple_list_item_1,
                mAllowedCountries);
        mView.setAddressAdapter(mGooglePlacesArrayAdapter);

        mView.updateAddressError(false, 0);
        mView.updateHousingTypeError(false);
        mView.showHousingTypeHint(mIsHousingTypeRequired);
        if(mIsHousingTypeRequired) {
            if (mHousingTypeAdapter == null) {
                mLoadingSpinnerManager.showLoading(true);
                // Load housing types list.
                CompletableFuture
                        .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                        .exceptionally(ex -> {
                            ApiErrorUtil.showErrorMessage(ex, mActivity);
                            return null;
                        })
                        .thenAccept(this::handleHousingTypes);
            } else {
                mView.setHousingTypeAdapter(mHousingTypeAdapter);

                if (mModel.hasValidHousingType()) {
                    mView.setHousingType(mModel.getHousingType().getKey());
                }
            }
        }
        else {
            mLoadingSpinnerManager.showLoading(false);
        }
        mView.setListener(this);
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
        if(mGeocodingHandler != null) {
            mLoadingSpinnerManager.showLoading(true);
            mIsNextClickHandlerPending = true;
        }
        else {
            // Store data.
            if(mIsHousingTypeRequired) {
                mModel.setHousingType(mView.getHousingType());
            }
            validateData();
        }
    }

    private void validateData() {
        if(mIsHousingTypeRequired) {
            mView.updateHousingTypeError(!mModel.hasValidHousingType());
            if (mModel.hasValidData()) {
                saveDataAndExit();
            }
        }
        else {
            if(mModel.hasValidAddress()) {
                saveDataAndExit();
            }
        }
    }

    private void saveDataAndExit() {
        saveData();
        mDelegate.addressAndHousingTypeStored();
    }

    /** {@inheritDoc} */
    @Override
    public AddressModel createModel() {
        return new AddressModel();
    }

    /**
     * Called when the housing types list API response has been received.
     * @param response API response.
     */
    private void handleHousingTypes(ConfigResponseVo response) {
        if (isHousingTypesPresent(response)) {
            setHousingTypesList(response.housingTypeOpts.data);
        }
    }

    private boolean isHousingTypesPresent(ConfigResponseVo response) {
        return response!=null && response.housingTypeOpts!=null;
    }

    /**
     * Stores a new list of housing types and updates the View.
     * @param typesList New list.
     */
    private void setHousingTypesList(HousingTypeVo[] typesList) {
        mHousingTypeAdapter = generateHousingTypeAdapter(typesList);

        if (mView != null) {
            mActivity.runOnUiThread(()-> {
                mLoadingSpinnerManager.showLoading(false);
                mView.setHousingTypeAdapter(mHousingTypeAdapter);

                if (mModel.hasValidHousingType()) {
                    mView.setHousingType(mModel.getHousingType().getKey());
                }
            });
        }
    }

    private void getPlaceDetails(String placeId) {
        if(mGeocodingHandler != null) {
            mGeocodingHandler.cancel();
        }
        mGeocodingHandler = new GeocodingHandler();
        mGeocodingHandler.reverseGeocode(mActivity, placeId,
                response -> {
                    mGeocodingHandler = null;
                    if (response == null) {
                        Log.e(GOOGLE_PLACES_TAG, "Reverse geocoding failed");
                        return;
                    } else if(!response.status.equals("OK")) {
                        Log.e(GOOGLE_PLACES_TAG, "Get places details status: " + response.status);
                        return;
                    }
                    ResultVo result = response.result;
                    for (AddressComponentVo addressComponent : result.getAddressComponents()) {
                        switch (addressComponent.getTypes().get(0)) {
                            case "locality":
                                mModel.setCity(addressComponent.getLongName());
                                break;
                            case "administrative_area_level_1":
                                mModel.setState(addressComponent.getShortName());
                                break;
                            case "country":
                                mModel.setCountry(addressComponent.getShortName());
                                break;
                            case "postal_code":
                                mModel.setZip(addressComponent.getShortName());
                                break;
                            case "street_number":
                                mModel.setStreetNumber(addressComponent.getShortName());
                                break;
                            case "route":
                                mModel.setStreet(addressComponent.getLongName());
                                break;
                        }
                    }
                    if(mIsNextClickHandlerPending) {
                        this.nextClickHandler();
                    }
                },
                e -> {
                    mGeocodingHandler = null;
                    mLoadingSpinnerManager.showLoading(false);
                    ApiErrorUtil.showErrorMessage(e, mActivity);
                });
    }

    @Override
    public void onAddressSelected(int position) {
        GooglePlacesArrayAdapter.PlaceAutocomplete item = mGooglePlacesArrayAdapter.getItem(position);
        getPlaceDetails(item.placeId);
    }
}
