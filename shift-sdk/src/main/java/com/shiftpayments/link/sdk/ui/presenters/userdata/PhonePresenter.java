package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.PhoneModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.userdata.PhoneView;

import java.util.List;

/**
 * Concrete {@link Presenter} for the phone screen.
 * @author Adrian
 */
public class PhonePresenter
        extends UserDataPresenter<PhoneModel, PhoneView>
        implements PhoneView.ViewListener {

    private PhoneDelegate mDelegate;

    /**
     * Creates a new {@link PhonePresenter} instance.
     * @param activity Activity.
     */
    public PhonePresenter(AppCompatActivity activity, PhoneDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public PhoneModel createModel() {
        return new PhoneModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PhoneView view) {
        super.attachView(view);

        if(mModel.hasPhone()) {
            mView.setPhone(Long.toString(mModel.getPhone().getNationalNumber()));
        }
        if(isVerificationRequired()) {
            mView.setDescription(mActivity.getResources().getString(R.string.phone_label_sms));
        }
        else {
            mView.setDescription(mActivity.getResources().getString(R.string.phone_label));
        }

        mView.setListener(this);
        List<String> allowedCountries = UIStorage.getInstance().getContextConfig().allowedCountries;
        mView.setPickerCountryList(TextUtils.join(",", allowedCountries));
    }

    private boolean isVerificationRequired() {
        String primaryCredential = SharedPreferencesStorage.getPrimaryCredential(mActivity);
        return primaryCredential != null && primaryCredential.equalsIgnoreCase("phone");

    }

    @Override
    public void onBack() {
        mDelegate.phoneOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        mModel.setPhone(mView.getCountryCode(), mView.getPhone());
        mView.updatePhoneError(!mModel.hasPhone(), R.string.phone_error);

        if(mModel.hasValidData()) {
            saveData();
            mDelegate.phoneStored();
        }
    }
}
