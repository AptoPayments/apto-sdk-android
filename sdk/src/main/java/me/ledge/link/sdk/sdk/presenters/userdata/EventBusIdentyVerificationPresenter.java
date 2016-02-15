package me.ledge.link.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.sdk.sdk.views.userdata.IdentityVerificationView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * An {@link IdentityVerificationPresenter} that uses the {@link EventBus} to receive API responses.
 * @author Wijnand
 */
public class EventBusIdentyVerificationPresenter extends IdentityVerificationPresenter {

    private EventBus mBus;

    /**
     * Creates a new {@link EventBusIdentyVerificationPresenter} instance.
     * @param activity See {@link IdentityVerificationPresenter#IdentityVerificationPresenter}.
     */
    public EventBusIdentyVerificationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();
        mBus = EventBus.getDefault();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
        super.attachView(view);
        mBus.register(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mBus.unregister(this);
        super.detachView();
    }

    /**
     * Called when the user creation API response has been received.
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
