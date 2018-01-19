package me.ledge.link.sdk.ui.presenters.verification;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.verifications.FinishVerificationResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.fragments.DatePickerFragment;
import me.ledge.link.sdk.ui.models.verification.BirthdateVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.utils.ResourceUtil;
import me.ledge.link.sdk.ui.views.verification.BirthdateVerificationView;

/**
 * Concrete {@link Presenter} for the birthdate screen.
 * @author Adrian
 */
public class BirthdateVerificationPresenter
        extends UserDataPresenter<BirthdateVerificationModel, BirthdateVerificationView>
        implements Presenter<BirthdateVerificationModel, BirthdateVerificationView>,
        BirthdateVerificationView.ViewListener, DatePickerDialog.OnDateSetListener {

    private BirthdateVerificationDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link BirthdateVerificationPresenter} instance.
     */
    public BirthdateVerificationPresenter(AppCompatActivity activity, BirthdateVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /**
     * @param activity The {@link Activity} that will be hosting the date picker.
     * @return Resource ID of the theme to use with for the birthday date picker.
     */
    private int getBirthdateDialogThemeId(Activity activity) {
        return new ResourceUtil().getResourceIdForAttribute(activity, R.attr.llsdk_userData_datePickerTheme);
    }

    /** {@inheritDoc} */
    @Override
    public BirthdateVerificationModel createModel() {
        return new BirthdateVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        mModel.setMinimumAge(mActivity.getResources().getInteger(R.integer.min_age));
        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(BirthdateVerificationView view) {
        super.attachView(view);
        mView.setListener(this);
        mResponseHandler.subscribe(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);

        if(mModel.hasValidBirthdate()) {
            mView.setBirthdate(mModel.getFormattedBirthdate());
        }
    }

    @Override
    public void onBack() {
        mDelegate.birthdateOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void birthdayClickHandler() {
        DatePickerFragment fragment = new DatePickerFragment();

        fragment.setDate(mModel.getBirthdate());
        fragment.setThemeId(getBirthdateDialogThemeId(mActivity));
        fragment.setListener(this);
        fragment.show(mActivity.getFragmentManager(), DatePickerFragment.TAG);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Validate input.
        mModel.setBirthdate(mView.getBirthdate(), "MM-dd-yyyy");
        mView.updateBirthdateError(!mModel.hasValidBirthdate(), mModel.getBirthdateErrorString());

        if (mModel.hasValidBirthdate()) {
            mLoadingSpinnerManager.showLoading(true);
            super.saveData();
            LedgeLinkUi.completeVerification(mModel.getVerificationRequest(), mModel.getVerificationId());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mModel.setBirthdate(year, monthOfYear, dayOfMonth);
        mView.setBirthdate(String.format("%02d-%02d-%02d", monthOfYear + 1, dayOfMonth, year));
        mView.updateBirthdateError(!mModel.hasValidBirthdate(), mModel.getBirthdateErrorString());
    }

    /**
     * Called when the finish phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(FinishVerificationResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            mModel.getVerification().setVerificationStatus(response.status);
            if(!mModel.getVerification().isVerified()) {
                mView.displayErrorMessage(mActivity.getString(R.string.birthdate_verification_error));
            }
            else {
                mDelegate.birthdateSucceeded();
            }
        }
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
