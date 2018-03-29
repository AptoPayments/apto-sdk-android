package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.DatePicker;

import java.util.ArrayList;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.sdk.api.vos.datapoints.Address;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.responses.config.ContentVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
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

    private String mDisclaimersText = "";
    private ArrayList<ContentVo> mFullScreenDisclaimers = new ArrayList<>();
    private IdentityVerificationDelegate mDelegate;
    private boolean mIsSSNRequired;
    private boolean mIsSSNNotAvailableAllowed;
    private boolean mIsBirthdayRequired;
    private LoadingSpinnerManager mLoadingSpinnerManager;
    public int mDisclaimersShownCounter = 0;

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

        int progressColor = getProgressBarColor(mActivity);
        if (progressColor != 0) {
            mView.setProgressColor(progressColor);
        }

        if(((UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule()).isUpdatingProfile) {
            if(mIsSSNRequired && mView.getSocialSecurityNumber().isEmpty()) {
                mView.setMaskedSSN();
            }
            mView.setButtonText(mActivity.getResources().getString(R.string.id_verification_update_profile_button));
            mActivity.getSupportActionBar().setTitle(mActivity.getResources().getString(R.string.id_verification_update_profile_title));
            mView.showDisclaimers(false);
            mLoadingSpinnerManager.showLoading(false);
        }
        else {
            if (mDisclaimersText.isEmpty()) {
                mLoadingSpinnerManager.showLoading(true);
                CompletableFuture
                        .supplyAsync(()-> ConfigStorage.getInstance().getLoanProducts())
                        .exceptionally(ex -> {
                            errorReceived(ex.getMessage());
                            return null;
                        })
                        .thenAccept(this::partnerDisclaimersListRetrieved);
            } else {
                setTextDisclaimers(mDisclaimersText);
            }
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
            mModel.setBirthday(mView.getBirthday(), "MM-dd-yyyy");
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
        if (mFullScreenDisclaimers.isEmpty()) {
            exit();
        } else {
            showFullScreenDisclaimers();
        }
        mLoadingSpinnerManager.showLoading(false);
    }

    private void exit() {
        mDelegate.identityVerificationSucceeded();
    }

    private void showFullScreenDisclaimers() {
        DisclaimerUtil.showDisclaimer(mActivity, mFullScreenDisclaimers.get(0), this::showDisclaimersCallback);
    }

    private void showDisclaimersCallback() {
        mDisclaimersShownCounter++;
        if(mDisclaimersShownCounter == mFullScreenDisclaimers.size()) {
            exit();
        }
        else {
            DisclaimerUtil.showDisclaimer(mActivity, mFullScreenDisclaimers.get(mDisclaimersShownCounter), this::showDisclaimersCallback);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mModel.setBirthday(year, monthOfYear, dayOfMonth);
        mView.setBirthday(String.format("%02d-%02d-%02d", monthOfYear + 1, dayOfMonth, year));
    }

    private void parseTextDisclaimer(ContentVo textDisclaimer) {
        String lineBreak = "<br />";
        String partnerDivider = "<br /><br />";
        StringBuilder result = new StringBuilder();
        if (!TextUtils.isEmpty(textDisclaimer.value)) {
            result.append(textDisclaimer.value.replaceAll("\\r?\\n", lineBreak));
            result.append(partnerDivider);
        }

        mDisclaimersText += result.substring(0, result.length() - partnerDivider.length());
    }

    private void setTextDisclaimers(String disclaimers) {
        mActivity.runOnUiThread(() -> {
            if(!disclaimers.isEmpty()) {
                mView.setDisclaimers(disclaimers);
            }
            mLoadingSpinnerManager.showLoading(false);
        });
    }

    private void partnerDisclaimersListRetrieved(LoanProductListVo response) {
        for (LoanProductVo loanProduct : response.data) {
            ContentVo disclaimer = loanProduct.preQualificationDisclaimer;
            if(disclaimer != null && !disclaimer.value.isEmpty()) {
                switch(ContentVo.formatValues.valueOf(disclaimer.format)) {
                    case plain_text:
                        parseTextDisclaimer(disclaimer);
                        break;
                    case markdown:
                        mFullScreenDisclaimers.add(disclaimer);
                        break;
                    case external_url:
                        mFullScreenDisclaimers.add(formatExternalUrlDisclaimer(disclaimer));
                        break;
                }
            }
        }
        setTextDisclaimers(mDisclaimersText);
    }

    private void errorReceived(String error) {
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
        }

        mView.displayErrorMessage(mActivity.getString(R.string.id_verification_toast_api_error, error));
    }

    private ContentVo formatExternalUrlDisclaimer(ContentVo disclaimer) {
        Address userAddress = (Address) UserStorage.getInstance().getUserData().getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        disclaimer.value = disclaimer.value.replace("[language]", LanguageUtil.getLanguage()).replace("[state]", userAddress.stateCode.toUpperCase());
        return disclaimer;
    }
}
