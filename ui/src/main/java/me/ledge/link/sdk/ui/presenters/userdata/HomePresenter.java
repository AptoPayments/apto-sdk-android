package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.smartystreets.api.ClientBuilder;
import com.smartystreets.api.exceptions.SmartyException;
import com.smartystreets.api.us_zipcode.Client;
import com.smartystreets.api.us_zipcode.Lookup;
import com.smartystreets.api.us_zipcode.Result;

import java.io.IOException;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.HousingTypeVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.HomeModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.userdata.HomeView;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;

/**
 * Concrete {@link Presenter} for the address validation screen.
 * @author Adrian
 */
public class HomePresenter
        extends UserDataPresenter<HomeModel, HomeView>
        implements HomeView.ViewListener {

    private HintArrayAdapter<IdDescriptionPairDisplayVo> mHousingTypeAdapter;
    private HomeDelegate mDelegate;
    private boolean mIsHousingTypeRequired;

    /**
     * Creates a new {@link HomePresenter} instance.
     * @param activity Activity.
     */
    public HomePresenter(AppCompatActivity activity, HomeDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
        mIsHousingTypeRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.Housing));
        if(mIsHousingTypeRequired) {
            // Load housing types list.
            CompletableFuture
                    .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                    .exceptionally(ex -> {
                        mView.displayErrorMessage(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::handleHousingTypes);
        }
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
    public void attachView(HomeView view) {
        super.attachView(view);

        // Set data.
        mView.setZipCode(mModel.getZip());

        mView.updateZipError(false, 0);
        mView.updateHousingTypeError(false);
        mView.showHousingTypeHint(mIsHousingTypeRequired);
        if(mIsHousingTypeRequired) {
            if (mHousingTypeAdapter == null) {
                mView.showLoading(true);
            } else {
                mView.setHousingTypeAdapter(mHousingTypeAdapter);

                if (mModel.hasValidHousingType()) {
                    mView.setHousingType(mModel.getHousingType().getKey());
                }
            }
        }
        else {
            mView.showLoading(false);
        }
        mView.setListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.homeOnBackPressed();
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
        mView.showLoading(true);
        startZipValidation();

        // Store data.
        mModel.setZip(mView.getZipCode());
        if(mIsHousingTypeRequired) {
            mModel.setHousingType(mView.getHousingType());
        }
        validateData();
    }

    private void validateData() {
        mView.updateZipError(!mModel.hasValidZip(), R.string.address_zip_code_error);
        if(mIsHousingTypeRequired) {
            mView.updateHousingTypeError(!mModel.hasValidHousingType());
            if (mModel.hasAllData()) {
                saveDataAndExit();
            }
        }
        else {
            if(mModel.hasValidZip()) {
                saveDataAndExit();
            }
        }
    }

    private void saveDataAndExit() {
        saveData();
        mDelegate.zipCodeAndHousingTypeStored();
    }

    /** {@inheritDoc} */
    @Override
    public HomeModel createModel() {
        return new HomeModel();
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
            mView.showLoading(false);
            mView.setHousingTypeAdapter(mHousingTypeAdapter);

            if (mModel.hasValidHousingType()) {
                mView.setHousingType(mModel.getHousingType().getKey());
            }
        }
    }

    @Override
    public void onZipFieldLostFocus() {
        startZipValidation();
    }

    private void startZipValidation() {
        Thread thread = new Thread(() -> {
            try  {
                lookUpZipCode(mView.getZipCode());
                mActivity.runOnUiThread(()-> mView.showLoading(false));
            } catch (Exception e) {
                mActivity.runOnUiThread(()-> mView.displayErrorMessage(e.getMessage()));
            }
        });
        thread.start();
    }

    private void lookUpZipCode(String zipCode) throws SmartyException, IOException {
        Client client = new ClientBuilder(mActivity.getString(R.string.smarty_streets_auth_id), mActivity.getString(R.string.smarty_streets_auth_token))
                .buildUsZipCodeApiClient();

        Lookup lookup = new Lookup();
        lookup.setZipCode(zipCode);
        client.send(lookup);
        Result results = lookup.getResult();
        if (results.isValid()) {
            mModel.setState(results.getZipCode(0).getStateAbbreviation());
            mModel.setCity(results.getCity(0).getCity());
        }
    }
}
