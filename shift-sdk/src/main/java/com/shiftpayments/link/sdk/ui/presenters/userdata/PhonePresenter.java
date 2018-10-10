package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.PhoneModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.views.userdata.PhoneView;

import java.util.ArrayList;

/**
 * Concrete {@link Presenter} for the phone screen.
 * @author Adrian
 */
public class PhonePresenter
        extends UserDataPresenter<PhoneModel, PhoneView>
        implements PhoneView.ViewListener {

    private PhoneDelegate mDelegate;
    private ArrayList<String> mAllowedCountries;

    /**
     * Creates a new {@link PhonePresenter} instance.
     * @param activity Activity.
     * @param allowedCountries List of allowed countries
     */
    public PhonePresenter(AppCompatActivity activity, PhoneDelegate delegate, ArrayList<String> allowedCountries) {
        super(activity);
        mDelegate = delegate;
        mAllowedCountries = allowedCountries;
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

        if(isVerificationRequired()) {
            mView.setDescription(mActivity.getResources().getString(R.string.phone_label_sms));
        }
        else {
            mView.setDescription(mActivity.getResources().getString(R.string.phone_label));
        }

        mView.setListener(this);
        if(mAllowedCountries!=null && !mAllowedCountries.isEmpty()) {
            mView.setPickerDefaultCountry(mAllowedCountries.get(0));
            mView.setPickerCountryList(TextUtils.join(",", mAllowedCountries));
            mView.disableCountryPicker(mAllowedCountries.size()==1);
        }

        if(mModel.hasPhone()) {
            mView.setPhone(Long.toString(mModel.getPhone().getNationalNumber()));
        }
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
        String country = mView.getCountryCode();
        CardStorage.getInstance().setSelectedCountry(country);
        mModel.setPhone(country, mView.getPhone());
        mView.updatePhoneError(!mModel.hasPhone(), R.string.phone_error);

        if(mModel.hasValidData()) {
            saveData();
            mDelegate.phoneStored();
        }
    }
}
