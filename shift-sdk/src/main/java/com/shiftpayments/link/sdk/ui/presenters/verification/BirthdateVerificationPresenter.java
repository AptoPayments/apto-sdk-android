package com.shiftpayments.link.sdk.ui.presenters.verification;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.models.verification.BirthdateVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataPresenter;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.verification.BirthdateVerificationView;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

/**
 * Concrete {@link Presenter} for the birthdate screen.
 * @author Adrian
 */
public class BirthdateVerificationPresenter
        extends UserDataPresenter<BirthdateVerificationModel, BirthdateVerificationView>
        implements Presenter<BirthdateVerificationModel, BirthdateVerificationView>,
        BirthdateVerificationView.ViewListener {

    private BirthdateVerificationDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link BirthdateVerificationPresenter} instance.
     */
    public BirthdateVerificationPresenter(AppCompatActivity activity, BirthdateVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
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
    public void nextClickHandler() {
        // Validate input.
        int day = mView.getBirthdayDay().isEmpty() ? 0 : Integer.valueOf(mView.getBirthdayDay());
        int year = mView.getBirthdayYear().isEmpty() ? 0 : Integer.valueOf(mView.getBirthdayYear());
        String[] months = mActivity.getResources().getStringArray(R.array.months_of_year);
        int month = Arrays.asList(months).indexOf(mView.getBirthdayMonth());
        mModel.setBirthdate(year, month, day);
        mView.updateBirthdateError(!mModel.hasValidBirthdate(), mModel.getBirthdateErrorString());

        if (mModel.hasValidBirthdate()) {
            mLoadingSpinnerManager.showLoading(true);
            super.saveData();
            ShiftPlatform.completeVerification(mModel.getVerificationRequest());
        }
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
                ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.birthdate_verification_error), mActivity);
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
        super.setApiError(error);
    }

    @Override
    public void monthClickHandler() {
        showMonthPicker();
    }

    private void showMonthPicker() {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mActivity, android.R.layout.select_dialog_singlechoice);
        String[] monthsOfYear = mActivity.getResources().getStringArray(R.array.months_of_year);
        arrayAdapter.addAll(monthsOfYear);

        new AlertDialog.Builder(mActivity)
                .setTitle(R.string.birthdate_label_month)
                .setNegativeButton(android.R.string.cancel, null)
                .setAdapter(arrayAdapter, (dialog1, item) -> mView.setBirthdayMonth(arrayAdapter.getItem(item)))
                .show();
    }
}
