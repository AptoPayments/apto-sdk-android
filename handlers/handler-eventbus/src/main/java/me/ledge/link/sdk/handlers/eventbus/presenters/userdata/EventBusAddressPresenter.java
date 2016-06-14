package me.ledge.link.sdk.handlers.eventbus.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.responses.config.HousingTypeListResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.AddressPresenter;
import me.ledge.link.sdk.ui.views.userdata.AddressView;
import org.greenrobot.eventbus.Subscribe;

/**
 * An {@link AddressPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Wijnand
 */
public class EventBusAddressPresenter extends AddressPresenter {

    /**
     * Creates a new {@link AddressPresenter} instance.
     * @param activity Activity.
     */
    public EventBusAddressPresenter(AppCompatActivity activity) {
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
    public void handleToken(HousingTypeListResponseVo response) {
        if (response != null) {
            setHousingTypesList(response.data);
        }
    }
}
