package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.common.models.countries.Usa;
import me.ledge.common.models.countries.UsaState;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.HousingTypeVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.AddressView;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the address screen.
 * @author Wijnand
 */
public class AddressPresenter
        extends UserDataPresenter<AddressModel, AddressView>
        implements AddressView.ViewListener {

    private Usa mStates;
    private HintArrayAdapter<IdDescriptionPairDisplayVo> mHousingTypeAdapter;
    private AddressDelegate mDelegate;

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
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 3, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);

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
        mView.updateHousingTypeError(false);
        mView.showLoading(true);

        UsaState state = mStates.getStateByCode(mModel.getState());
        if (state != null) {
            mView.setState(state.getName());
        }

        if (mHousingTypeAdapter == null) {
            mView.setHousingTypeAdapter(generateHousingTypeAdapter(null));

            // Load housing types list.
            LedgeLinkUi.getHousingTypeList();
        } else {
            mView.setHousingTypeAdapter(mHousingTypeAdapter);

            if (mModel.hasValidHousingType()) {
                mView.setHousingType(mHousingTypeAdapter.getPosition(mModel.getHousingType()));
            }
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
        mResponseHandler.unsubscribe(this);
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
        mModel.setHousingType(mView.getHousingType());

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
        mView.updateHousingTypeError(!mModel.hasValidHousingType());

        if (mModel.hasAllData()) {
            saveData();
            mDelegate.addressStored();
        }
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
    @Subscribe
    public void handleToken(ConfigResponseVo response) {
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
    public void setHousingTypesList(HousingTypeVo[] typesList) {
        mHousingTypeAdapter = generateHousingTypeAdapter(typesList);

        if (mView != null) {
            mView.showLoading(false);
            mView.setHousingTypeAdapter(mHousingTypeAdapter);

            if (mModel.hasValidHousingType()) {
                mView.setHousingType(mHousingTypeAdapter.getPosition(mModel.getHousingType()));
            }
        }
    }
}
