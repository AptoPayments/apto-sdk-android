package me.ledge.link.sdk.handlers.otto.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.AddressPresenter;
import me.ledge.link.sdk.ui.views.userdata.AddressView;

/**
 * An {@link AddressPresenter} that uses the Otto Bus to receive API responses.
 * @author Wijnand
 */
public class OttoAddressPresenter extends AddressPresenter {

    /**
     * Creates a new {@link OttoAddressPresenter} instance.
     * @param activity Activity.
     */
    public OttoAddressPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(AddressView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /**
     * Called when the housing types list API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(ConfigResponseVo response) {
        if (response != null) {
            setHousingTypesList(response.housingTypeOpts.data);
        }
    }
}
