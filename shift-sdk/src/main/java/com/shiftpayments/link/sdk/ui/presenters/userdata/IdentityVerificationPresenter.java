package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.IdDocument;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.IdentityVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.ResourceUtil;
import com.shiftpayments.link.sdk.ui.views.userdata.IdentityVerificationView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Concrete {@link Presenter} for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationPresenter
        extends UserDataPresenter<IdentityVerificationModel, IdentityVerificationView>
        implements Presenter<IdentityVerificationModel, IdentityVerificationView>,
        IdentityVerificationView.ViewListener {

    private IdentityVerificationDelegate mDelegate;
    private boolean mIsSSNRequired;
    private boolean mIsSSNNotAvailableAllowed;
    private boolean mIsBirthdayRequired;
    private UserDataCollectorConfigurationVo mCallToActionConfig;
    private HashMap<String, List<IdDocument.IdDocumentType>> mAllowedDocumentTypes;
    private HashMap<String, String> mCountryNameToCountryCodeMap;

    /**
     * Creates a new {@link IdentityVerificationPresenter} instance.
     */
    public IdentityVerificationPresenter(AppCompatActivity activity, IdentityVerificationDelegate delegate, HashMap<String, List<IdDocument.IdDocumentType>> allowedDocumentTypes) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();

        mIsSSNRequired = false;
        mIsSSNNotAvailableAllowed = false;
        for (RequiredDataPointVo requiredDataPointVo : module.mRequiredDataPointList) {
            if(requiredDataPointVo.type.equals(DataPointVo.DataPointType.IdDocument)) {
                mIsSSNRequired = true;
                mIsSSNNotAvailableAllowed = requiredDataPointVo.notSpecifiedAllowed;
            }
        }

        mIsBirthdayRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.BirthDate));
        mCallToActionConfig = module.getCallToActionConfig();
        mAllowedDocumentTypes = allowedDocumentTypes;
        mCountryNameToCountryCodeMap = new HashMap<>();
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
    public IdentityVerificationModel createModel() {
        return new IdentityVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        mModel.setMinimumAge(mActivity.getResources().getInteger(R.integer.min_age));
        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
        super.attachView(view);
        mView.setListener(this);

        mView.showBirthday(mIsBirthdayRequired);
        if(mIsBirthdayRequired && mModel.hasValidBirthday()) {
            String month = mActivity.getResources().getStringArray(R.array.months_of_year)[mModel.getBirthdateMonth()];
            mView.setBirthdayMonth(month);
            mView.setBirthdayDay(mModel.getBirthdateDay());
            mView.setBirthdayYear(mModel.getBirthdateYear());
        }

        if(mAllowedDocumentTypes == null || mAllowedDocumentTypes.isEmpty()) {
            ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.error_something_went_wrong), mActivity);
            return;
        }
        Set<String> countryCodeSet = mAllowedDocumentTypes.keySet();
        if(countryCodeSet.size() > 1) {
            mView.showCitizenshipSpinner(true);
            List<String> countryNames = getCountryListFromCountryCodeSet(countryCodeSet);
            ArrayAdapter<String> countryListAdapter = new ArrayAdapter<>(mActivity,
                    android.R.layout.simple_spinner_item, countryNames);
            countryListAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            mView.setCitizenshipSpinnerAdapter(countryListAdapter);
        }
        else {
            mView.showCitizenshipSpinner(false);
            String countryCode = countryCodeSet.iterator().next();
            mModel.setCountry(countryCode);
            setDocumentTypesAdapter(countryCode);
        }

        mView.showSSN(mIsSSNRequired);
        mView.showSSNNotAvailableCheckbox(mIsSSNNotAvailableAllowed);
        if(mIsSSNRequired && mModel.hasValidDocument()) {
            mView.setDocumentNumber(mModel.getDocumentNumber());
        }

        int progressColor = getProgressBarColor(mActivity);
        if (progressColor != 0) {
            mView.setProgressColor(progressColor);
        }

        if(mCallToActionConfig != null) {
            mView.setButtonText(mCallToActionConfig.callToAction.title.toUpperCase());
            mActivity.getSupportActionBar().setTitle(mCallToActionConfig.title);
        }
    }

    @Override
    public void onBack() {
        mDelegate.identityVerificationOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        mView.setListener(null);
        super.detachView();
    }

    @Override
    public void ssnCheckBoxClickHandler() {
        mView.enableSpinners(!mView.isSSNCheckboxChecked());
        mView.enableIdDocumentField(!mView.isSSNCheckboxChecked());
        mView.updateDocumentNumberError(false, 0);
    }

    @Override
    public void monthClickHandler() {
        showMonthPicker();
    }

    @Override
    public void citizenshipClickHandler(String country) {
        String countryCode = mCountryNameToCountryCodeMap.get(country);
        mModel.setCountry(countryCode);
        setDocumentTypesAdapter(countryCode);
    }

    @Override
    public void documentTypeClickHandler(IdDocument.IdDocumentType documentType) {
        mModel.setDocumentType(documentType);
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Validate input.
        if(mView.isSSNCheckboxChecked()) {
            mIsSSNRequired = false;
            mModel.setSocialSecurityNotAvailable(true);
        }

        if(mIsBirthdayRequired) {
            int day = mView.getBirthdayDay().isEmpty() ? 0 : Integer.valueOf(mView.getBirthdayDay());
            int year = mView.getBirthdayYear().isEmpty() ? 0 : Integer.valueOf(mView.getBirthdayYear());
            String[] months = mActivity.getResources().getStringArray(R.array.months_of_year);
            int month = Arrays.asList(months).indexOf(mView.getBirthdayMonth());
            mModel.setBirthday(year, month, day);
            mView.updateBirthdayError(!mModel.hasValidBirthday(), mModel.getBirthdayErrorString());
        }
        if(mIsSSNRequired && userHasUpdatedSSN()) {
            mModel.setDocumentNumber(mView.getDocumentNumber());
            mView.updateDocumentNumberError(!mModel.hasValidDocument(), mModel.getSsnErrorString());
        }

        if(mIsSSNRequired && mIsBirthdayRequired) {
            if(mModel.hasValidData() || ((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
                    && !userHasUpdatedSSN() && mModel.hasValidBirthday()) {
                saveDataAndExit();
            }
        }
        else if(mIsBirthdayRequired) {
            if(mModel.hasValidBirthday()) {
                saveDataAndExit();
            }
        }
        else if(mIsSSNRequired){
            if(mModel.hasValidDocument() || ((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
                    && !userHasUpdatedSSN()) {
                saveDataAndExit();
            }
        }
        else {
            saveDataAndExit();
        }
    }

    private boolean userHasUpdatedSSN() {
        return !mView.getDocumentNumber().equals(mModel.getDocumentNumber()) ||
                (!((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
                        && mView.getDocumentNumber() != null);
    }

    private void saveDataAndExit() {
        super.saveData();
        mDelegate.identityVerificationSucceeded();
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

    private List<String> getCountryListFromCountryCodeSet(Set<String> countryCodesSet) {
        List<String> countryList = new ArrayList<>();
        for(String countryCode : countryCodesSet) {
            countryList.add(getCountryNameFromCountryCode(countryCode));
        }
        return countryList;
    }

    private String getCountryNameFromCountryCode(String countryCode) {
        Locale locale = new Locale("",countryCode);
        String countryName = locale.getDisplayCountry();
        mCountryNameToCountryCodeMap.put(countryName, countryCode);
        return countryName;
    }

    private void setDocumentTypesAdapter(String countryCode) {
        List<IdDocument.IdDocumentType> documentTypes = mAllowedDocumentTypes.get(countryCode);
        if(documentTypes.size() == 0) {
            ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.error_something_went_wrong), mActivity);
            return;
        }
        ArrayAdapter<IdDocument.IdDocumentType> documentTypesAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, documentTypes);
        documentTypesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mView.setDocumentTypeSpinnerAdapter(documentTypesAdapter);
    }


}
