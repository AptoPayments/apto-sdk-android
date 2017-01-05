package me.ledge.link.sdk.handlers.eventbus.presenters.verifications;

import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationPresenter;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.views.verification.EmailVerificationView;

/**
 * An {@link EmailVerificationPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Adrian
 */
public class EventBusEmailVerificationPresenter extends EmailVerificationPresenter {

    /**
     * Creates a new {@link EventBusEmailVerificationPresenter} instance.
     * @param activity See {@link EmailVerificationPresenter}.
     */
    public EventBusEmailVerificationPresenter(AppCompatActivity activity, EmailVerificationDelegate delegate) {
        super(activity, delegate);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(EmailVerificationView view) {
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
     * Called when the start email verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(StartEmailVerificationResponseVo response) {
        setVerificationResponse(response);
    }

    /**
     * Called when the get verification status API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(VerificationStatusResponseVo response) {
        setVerificationResponse(response);
    }

    /**
     * Called when the start phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(CreateUserResponseVo response) {
        setCreateUserResponse(response);
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
