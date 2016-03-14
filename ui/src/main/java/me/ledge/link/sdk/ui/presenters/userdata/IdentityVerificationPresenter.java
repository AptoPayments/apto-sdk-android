package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.DatePicker;
import android.widget.Toast;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.fragments.DatePickerFragment;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;

/**
 * Concrete {@link Presenter} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationPresenter
        extends UserDataPresenter<IdentityVerificationModel, IdentityVerificationView>
        implements Presenter<IdentityVerificationModel, IdentityVerificationView>,
        IdentityVerificationView.ViewListener, DatePickerDialog.OnDateSetListener {

    /**
     * Creates a new {@link IdentityVerificationPresenter} instance.
     */
    public IdentityVerificationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /**
     * @param activity The {@link Activity} that will be hosting the date picker.
     * @return Resource ID of the theme to use with for the birthday date picker.
     */
    private int getBirthdayDialogThemeId(Activity activity) {
        return getResourceIdForAttribute(activity, R.attr.llsdk_userData_datePickerTheme);
    }

    /**
     * @param activity The {@link Activity} containing the attribute values.
     * @return Resource ID of the color to use for the progress bar.
     */
    private int getProgressBarColor(Activity activity) {
        return getResourceIdForAttribute(activity, R.attr.llsdk_userData_progressColor);
    }

    /**
     * @param activity The {@link Activity} containing the attribute values.
     * @param attribute The attribute ID to look up the value for.
     * @return Attribute value.
     */
    private int getResourceIdForAttribute(Activity activity, int attribute) {
        int id;

        int[] attributesList = new int[] { attribute };
        try {
            TypedArray a = activity.obtainStyledAttributes(new TypedValue().data, attributesList);
            id = a.getResourceId(0, 0);
            a.recycle();
        } catch (RuntimeException rte) {
            id = 0;
        }

        return id;
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
        mView.setListener(this);

        int progressColor = getProgressBarColor(mActivity);
        if (progressColor != 0) {
            mView.setProgressColor(progressColor);
        }
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
