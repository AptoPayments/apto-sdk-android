package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.DatePicker;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.api.vos.responses.config.LoanProductVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.fragments.DatePickerFragment;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.DisclaimerUtil;
import me.ledge.link.sdk.ui.utils.LanguageUtil;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.utils.ResourceUtil;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;

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
    private boolean mIsSSNRequired;
    private boolean mIsSSNNotAvailableAllowed;
    private boolean mIsBirthdayRequired;
    private DisclaimerVo mDisclaimer;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link IdentityVerificationPresenter} instance.
     */
    public IdentityVerificationPresenter(AppCompatActivity activity, IdentityVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();

        mIsSSNRequired = false;
        mIsSSNNotAvailableAllowed = false;
        for (RequiredDataPointVo requiredDataPointVo : module.mRequiredDataPointList) {
            if(requiredDataPointVo.type.equals(DataPointVo.DataPointType.SSN)) {
                mIsSSNRequired = true;
                mIsSSNNotAvailableAllowed = requiredDataPointVo.notSpecifiedAllowed;
            }
        }

        mIsBirthdayRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.BirthDate));
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
    public IdentityVerificationModel createModel() {
        return new IdentityVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    protected void populateModelFromStorage() {
        mModel.setExpectedSSNLength(mActivity.getResources().getInteger(R.integer.ssn_length));
        mModel.setMinimumAge(mActivity.getResources().getInteger(R.integer.min_age));
        super.populateModelFromStorage();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(IdentityVerificationView view) {
        super.attachView(view);
        mView.setListener(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);

        mView.showBirthday(mIsBirthdayRequired);
        if(mIsBirthdayRequired && mModel.hasValidBirthday()) {
            mView.setBirthday(mModel.getFormattedBirthday());
        }

        mView.showSSN(mIsSSNRequired);
        mView.showSSNNotAvailableCheckbox(mIsSSNNotAvailableAllowed);
        if(mIsSSNRequired && mModel.hasValidSsn()) {
            mView.setSSN(mModel.getSocialSecurityNumber());
        }

        if(((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile) {
            if(mIsSSNRequired && mView.getSocialSecurityNumber().isEmpty()) {
                mView.setMaskedSSN();
            }
            mView.setButtonText(mActivity.getResources().getString(R.string.id_verification_update_profile_button));
            mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.id_verification_update_profile_title));
            mView.showDisclaimers(false);
        }

        int progressColor = getProgressBarColor(mActivity);
        if (progressColor != 0) {
            mView.setProgressColor(progressColor);
        }

        if (mDisclaimersText == null) {
            mLoadingSpinnerManager.showLoading(true);
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

    @Override
    public void ssnCheckBoxClickHandler() {
        mView.enableSSNField(!mView.isSSNCheckboxChecked());
        mView.updateSocialSecurityError(false, 0);
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
            mView.updateBirthdayError(!mModel.hasValidBirthday(), mModel.getBirthdayErrorString());
        }
        if(mIsSSNRequired && userHasUpdatedSSN()) {
            mModel.setSocialSecurityNumber(mView.getSocialSecurityNumber());
            mView.updateSocialSecurityError(!mModel.hasValidSsn(), mModel.getSsnErrorString());
        }

        if(mIsSSNRequired && mIsBirthdayRequired) {
            if(mModel.hasAllData() || ((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
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
            if(mModel.hasValidSsn() || ((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
                    && !userHasUpdatedSSN()) {
                saveDataAndExit();
            }
        }
        else {
            showDisclaimerOrExit();
        }
    }

    private boolean userHasUpdatedSSN() {
        return (!mView.getSocialSecurityNumber().equals(mModel.getSocialSecurityNumber()) &&
                !mView.isSSNMasked()) ||
                (!((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile
                && mView.getSocialSecurityNumber()!=null);
    }

    private void saveDataAndExit() {
        super.saveData();
        showDisclaimerOrExit();
    }

    private void showDisclaimerOrExit() {
        mLoadingSpinnerManager.showLoading(true);
        if(ConfigStorage.getInstance().showPrequalificationDisclaimer()) {
            showDisclaimer();
        }
        else {
            exit();
        }
        mLoadingSpinnerManager.showLoading(false);
    }

    private void exit() {
        mDelegate.identityVerificationSucceeded();
    }

    private void showDisclaimer() {
        DisclaimerUtil.showDisclaimer(mActivity, mDisclaimer, this::exit);
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
            if (loanProduct.preQualificationDisclaimer!=null &&
                    !TextUtils.isEmpty(loanProduct.preQualificationDisclaimer.value)) {
                result.append(loanProduct.preQualificationDisclaimer.value.replaceAll("\\r?\\n", lineBreak));
            }
            result.append(partnerDivider);
        }

        return result.substring(0, result.length() - partnerDivider.length());
    }

    private void setDisclaimers(String disclaimers) {
        mDisclaimersText = disclaimers;
        mActivity.runOnUiThread(() -> {
            mView.setDisclaimers(disclaimers);
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private void setMarkdownDisclaimers(String disclaimers) {
        mDisclaimersText = disclaimers;
        mActivity.runOnUiThread(() -> {
            mView.setMarkdownDisclaimers(disclaimers);
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private void partnerDisclaimersListRetrieved(LoanProductListVo response) {
        DisclaimerVo disclaimer = response.data[0].preQualificationDisclaimer;
        if(disclaimer.value.isEmpty()) {
            retrieveProjectDisclaimer();
            mLoadingSpinnerManager.showLoading(false);
            return;
        }
        switch(DisclaimerVo.formatValues.valueOf(disclaimer.format)) {
            case plain_text:
                setDisclaimers(parseDisclaimersResponse(response));
                break;
            case markdown:
                setMarkdownDisclaimers(parseDisclaimersResponse(response));
            case external_url:
                setExternalUrlDisclaimer(disclaimer);
                mLoadingSpinnerManager.showLoading(false);
                break;
        }
    }

    private void retrieveProjectDisclaimer() {
        setExternalUrlDisclaimer(ConfigStorage.getInstance().getPrequalificationDisclaimer());
    }

    private void errorReceived(String error) {
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
        }

        mView.displayErrorMessage(mActivity.getString(R.string.id_verification_toast_api_error, error));
    }

    private void setExternalUrlDisclaimer(DisclaimerVo disclaimer) {
        Address userAddress = (Address) UserStorage.getInstance().getUserData().getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        disclaimer.value = disclaimer.value.replace("[language]", LanguageUtil.getLanguage()).replace("[state]", userAddress.stateCode.toUpperCase());
        mDisclaimer = disclaimer;
    }
}
