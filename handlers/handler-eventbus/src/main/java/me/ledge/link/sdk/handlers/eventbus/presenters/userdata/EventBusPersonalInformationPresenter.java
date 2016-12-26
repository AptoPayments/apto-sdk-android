package me.ledge.link.sdk.handlers.eventbus.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;
import me.ledge.link.sdk.ui.views.userdata.PersonalInformationView;

/**
 * An {@link PersonalInformationPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Adrian
 */
public class EventBusPersonalInformationPresenter extends PersonalInformationPresenter {

    /**
     * Creates a new {@link EventBusPersonalInformationPresenter} instance.
     * @param activity See {@link PersonalInformationPresenter}.
     */
    public EventBusPersonalInformationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PersonalInformationView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }
}
