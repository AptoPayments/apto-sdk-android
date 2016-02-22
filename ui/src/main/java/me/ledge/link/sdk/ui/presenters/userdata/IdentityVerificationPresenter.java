package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.Toast;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.fragments.DatePickerFragment;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Presenter} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationPresenter
        extends UserDataPresenter<IdentityVerificationModel, IdentityVerificationView>
        implements Presenter<IdentityVerificationModel, IdentityVerificationView>, IdentityVerificationView.ViewListener,
        DatePickerDialog.OnDateSetListener {

    private String mToken;

    /**
     * Creates a new {@link IdentityVerificationPresenter} instance.
     */
    public IdentityVerificationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public IdentityVerificationModel createModel() {
        return new IdentityVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromParcel() {
        super.populateModelFromParcel();
        mModel.setMinimumAge(mActivity.getResources().getInteger(R.integer.min_age));
    }

    /** {@inheritDoc} */
    @Override
    protected Intent getStartIntent(Class activity) {
        Intent intent = super.getStartIntent(activity);
        intent.removeExtra(UserDataVo.USER_DATA_KEY);
        intent.putExtra(OffersListPresenter.BEARER_TOKEN_KEY, mToken);

        return intent;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
        super.attachView(view);
        mView.setListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void birthdayClickHandler() {
        DatePickerFragment fragment = new DatePickerFragment();

        if (mModel.hasValidBirthday()) {
            fragment.setDate(mModel.getBirthday());
        }

        fragment.setListener(this);
        fragment.show(mActivity.getFragmentManager(), DatePickerFragment.TAG);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Validate input.
        mModel.setSocialSecurityNumber(mView.getSocialSecurityNumber());

        mView.updateBirthdayError(!mModel.hasValidBirthday(), mModel.getBirthdayErrorString());
        mView.updateSocialSecurityError(!mModel.hasValidSsn(), mModel.getSsnErrorString());

        if (mModel.hasAllData()) {
            // Make API request.
            LedgeLinkUi.createUser(mModel.getUserRequestData());

            // Show loading.
            mView.showLoading(true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mModel.setBirthday(year, monthOfYear, dayOfMonth);
        mView.setBirthday(String.format("%02d/%02d/%02d", monthOfYear + 1, dayOfMonth, year));
    }

    /**
     * Deals with the create user API response.
     * @param response API response.
     */
    public void setCreateUserResponse(CreateUserResponseVo response) {
        mView.showLoading(false);
        mToken = response.token;

        // Show next screen.
        super.nextClickHandler();
    }

    /**
     * Deals with an API error.
     * @param error API error.
     */
    public void setApiError(ApiErrorVo error) {
        mView.showLoading(false);

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}
