package me.ledge.link.sdk.handlers.eventbus.presenters.verifications;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.verifications.FinishPhoneVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartPhoneVerificationResponseVo;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.views.verification.PhoneVerificationView;

/**
 * An {@link PhoneVerificationPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Adrian
 */
public class EventBusPhoneVerificationPresenter extends PhoneVerificationPresenter {

    /**
     * Creates a new {@link EventBusPhoneVerificationPresenter} instance.
     * @param activity See {@link PhoneVerificationPresenter}.
     */
    public EventBusPhoneVerificationPresenter(AppCompatActivity activity, PhoneVerificationDelegate delegate) {
        super(activity, delegate);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PhoneVerificationView view) {
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
     * Called when the start phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(StartPhoneVerificationResponseVo response) {
        setVerificationResponse(response);
    }

    /**
     * Called when the finish phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(FinishPhoneVerificationResponseVo response) {
        setVerificationResponse(response);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        setApiError(error);
    }
}
