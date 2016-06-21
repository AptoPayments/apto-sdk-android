package me.ledge.link.sdk.handlers.otto.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import com.squareup.otto.Subscribe;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.DisclaimersListResponseVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;

/**
 * An {@link IdentityVerificationPresenter} that uses the Otto Bus to receive API responses.
 * @author Wijnand
 */
public class OttoIdentyVerificationPresenter extends IdentityVerificationPresenter {

    /**
     * Creates a new {@link OttoIdentyVerificationPresenter} instance.
     * @param activity See {@link IdentityVerificationPresenter#IdentityVerificationPresenter}.
     */
    public OttoIdentyVerificationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
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
     * Called when the user creation API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(CreateUserResponseVo response) {
        setCreateUserResponse(response);
    }

    /**
     * Called when the user update API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleUserDetails(UserResponseVo response) {
        setUpdateUserResponse(response);
    }

    @Subscribe
    public void handlePartnerDisclaimersList(DisclaimersListResponseVo response) {
        setDisclaimers(parseDisclaimersList(response));
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
