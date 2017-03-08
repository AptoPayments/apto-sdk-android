package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.DatePicker;

import org.greenrobot.eventbus.Subscribe;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.api.vos.responses.config.LoanProductVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.fragments.DatePickerFragment;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.ResourceUtil;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationPresenter
        extends UserDataPresenter<IdentityVerificationModel, IdentityVerificationView>
        implements Presenter<IdentityVerificationModel, IdentityVerificationView>,
        IdentityVerificationView.ViewListener, DatePickerDialog.OnDateSetListener {

    private String mDisclaimersText;
    private IdentityVerificationDelegate mDelegate;

    /**
     * Creates a new {@link IdentityVerificationPresenter} instance.
     */
    public IdentityVerificationPresenter(AppCompatActivity activity, IdentityVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /**
     * @param activity The {@link Activity} that will be hosting the date picker.
     * @return Resource ID of the theme to use with for the birthday date picker.
     */
    private int getBirthdayDialogThemeId(Activity activity) {
        return new ResourceUtil().getResourceIdForAttribute(activity, R.attr.llsdk_userData_datePickerTheme);
    }

    /**
     * @param activity The {@link Activity} containing the attribute values.
     * @return Resource ID of the color to use for the progress bar.
     */
    private int getProgressBarColor(Activity activity) {
        return new ResourceUtil().getResourceIdForAttribute(activity, R.attr.llsdk_userData_progressColor);
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 7, true, false);
    }

    /** {@inheritDoc} */
    @Override
    public IdentityVerificationModel createModel() {
        return new IdentityVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        super.populateModelFromStorage();
        mModel.setMinimumAge(mActivity.getResources().getInteger(R.integer.min_age));
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);
        mView.setListener(this);

        int progressColor = getProgressBarColor(mActivity);
        if (progressColor != 0) {
            mView.setProgressColor(progressColor);
        }

        if (mDisclaimersText == null) {
            mView.showLoading(true);
            CompletableFuture
                    .supplyAsync(()-> ConfigStorage.getInstance().getLoanProducts())
                    .exceptionally(ex -> {
                        errorReceived(ex.getMessage());
                        return null;
                    })
                    .thenAccept(this::partnerDisclaimersListRetrieved);
        } else {
            setDisclaimers(mDisclaimersText);
        }
    }

    @Override
    public void onBack() {
        mDelegate.identityVerificationOnBackPressed();
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
    public void birthdayClickHandler() {
        DatePickerFragment fragment = new DatePickerFragment();

        fragment.setDate(mModel.getBirthday());
        fragment.setThemeId(getBirthdayDialogThemeId(mActivity));
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
            if (TextUtils.isEmpty(UserStorage.getInstance().getBearerToken())) {
                LedgeLinkUi.createUser(mModel.getUserData());
            } else {
                DataPointVo phone = UserStorage.getInstance().getUserData().getUniqueDataPoint(DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
                DataPointVo email = UserStorage.getInstance().getUserData().getUniqueDataPoint(DataPointVo.DataPointType.Email, new DataPointVo.Email());

                if(phone.hasVerification() && email.hasVerification()) {
                    mDelegate.identityVerificationSucceeded();
                }
                else {
                    LedgeLinkUi.updateUser(mModel.getUserData());
                }
            }

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

    private String parseDisclaimersResponse(LoanProductListVo productDisclaimerList) {
        if (productDisclaimerList == null) {
            return "";
        }

        String lineBreak = "<br />";
        String partnerDivider = "<br /><br />";
        StringBuilder result = new StringBuilder();

        for(LoanProductVo loanProduct : productDisclaimerList.data) {
            if (!TextUtils.isEmpty(loanProduct.preQualificationDisclaimer)) {
                result.append(loanProduct.preQualificationDisclaimer.replaceAll("\\r?\\n", lineBreak));
            }
            result.append(partnerDivider);
        }

        return result.substring(0, result.length() - partnerDivider.length());
    }

    private void setDisclaimers(String disclaimers) {
        mDisclaimersText = disclaimers;
        mActivity.runOnUiThread(() -> {
            mView.setDisclaimers(disclaimers);
            mView.showLoading(false);
        });
    }

    /**
     * Deals with the create user API response.
     * @param response API response.
     */
    @Subscribe
    public void setCreateUserResponse(CreateUserResponseVo response) {
        mView.showLoading(false);

        if (response != null) {
            UserStorage.getInstance().setBearerToken(response.user_token);
            SharedPreferencesStorage.storeUserToken(mActivity, response.user_token);
        }

        if (mModel.hasAllData()) {
            super.saveData();
            mDelegate.identityVerificationSucceeded();
        }
    }

    /**
     * Called when the user update API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleUserDetails(UserResponseVo response) {
        mView.showLoading(false);
        if (mModel.hasAllData()) {
            super.saveData();
            mDelegate.identityVerificationSucceeded();
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if (mView != null) {
            mView.showLoading(false);
        }

        mView.displayErrorMessage(mActivity.getString(R.string.id_verification_toast_api_error, error.toString()));
    }

    private void partnerDisclaimersListRetrieved(LoanProductListVo response) {
        setDisclaimers(parseDisclaimersResponse(response));
    }

    private void errorReceived(String error) {
        if (mView != null) {
            mView.showLoading(false);
        }

        mView.displayErrorMessage(mActivity.getString(R.string.id_verification_toast_api_error, error));
    }
}
