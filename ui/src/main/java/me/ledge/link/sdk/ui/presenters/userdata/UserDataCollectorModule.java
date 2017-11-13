package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.datapoints.Birthdate;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.datapoints.VerificationVo;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.LoginUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.vos.responses.verifications.BaseVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.ArmedForcesActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.HomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PaydayLoanActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.userdata.PhoneActivity;
import me.ledge.link.sdk.ui.activities.userdata.TimeAtAddressActivity;
import me.ledge.link.sdk.ui.activities.verification.BirthdateVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.verification.BirthdateVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneDelegate,
        PhoneVerificationDelegate, EmailVerificationDelegate, IdentityVerificationDelegate,
        AddressDelegate, AnnualIncomeDelegate, MonthlyIncomeDelegate, CreditScoreDelegate,
        PersonalInformationDelegate, HomeDelegate, PaydayLoanDelegate, ArmedForcesDelegate,
        TimeAtAddressDelegate, BirthdateVerificationDelegate {

    private static UserDataCollectorModule instance;
    public Command onUserDoesNotHaveAllRequiredData;
    public Command onUserHasAllRequiredData;
    public LinkedList<RequiredDataPointVo> mRequiredDataPointList;
    public boolean isUpdatingProfile;
    private ArrayList<Class<? extends MvpActivity>> mRequiredActivities;
    private DataPointList mCurrentUserDataCopy;
    private ProgressDialog mProgressDialog;

    private UserDataCollectorModule(Activity activity) {
        super(activity);
        mRequiredDataPointList = new LinkedList<>();
        mRequiredActivities = new ArrayList<>();
    }

    public static synchronized UserDataCollectorModule getInstance(Activity activity) {
        if (instance == null) {
            instance = new UserDataCollectorModule(activity);
        }
        return instance;
    }

    @Override
    public void initialModuleSetup() {
        mRequiredActivities.clear();
        mCurrentUserDataCopy = new DataPointList(UserStorage.getInstance().getUserData());
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        CompletableFuture
                .supplyAsync(() -> ConfigStorage.getInstance().getRequiredUserData())
                .exceptionally(ex -> {
                    stopModule();
                    return null;
                })
                .thenAccept(this::storeRequiredData);
    }

    /**
     * Called when the get current user info response has been received.
     * @param userInfo API response.
     */
    @Subscribe
    public void handleResponse(DataPointList userInfo) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        UserStorage.getInstance().setUserData(userInfo);
        compareRequiredDataPointsWithCurrent(userInfo);
    }

    /**
     * Called when the login response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(LoginUserResponseVo response) {
        if (response != null) {
            storeToken(response.user_token);
            removeSecondaryCredentialFromRequiredList();
            mRequiredActivities.clear();
            // Identity Verification screen has to be added to show disclaimers
            addRequiredActivity(IdentityVerificationActivity.class);
            getCurrentUserOrContinue(ConfigStorage.getInstance().getPOSMode());
        }
    }

    /**
     * Called when the create user response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(CreateUserResponseVo response) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        if (response != null) {
            storeToken(response.user_token);
        }
        stopModule();
    }

    /**
     * Deals with the update user API response.
     * @param response API response.
     */
    @Subscribe
    public void handleUserDetails(UserResponseVo response) {
        stopModule();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        if(error.request_path.equals(LinkApiWrapper.GET_CURRENT_USER_PATH) && error.statusCode == 401) {
            LedgeLinkUi.clearUserToken(getActivity());
        }
        stopModule();
    }

    @Override
    public void startActivity(Class activity) {
        if(activity == null) {
            stopModule();
        } else {
            super.startActivity(activity);
        }
    }

    public void phoneStored() {
        if(isPhoneVerificationRequired() && !isCurrentPhoneVerified()) {
            startActivity(PhoneVerificationActivity.class);
        } else {
            startActivity(getActivityAtPosition(PhoneActivity.class, 1));
        }
    }

    @Override
    public void phoneOnBackPressed() {
        onBack.execute();
    }

    @Override
    public void phoneVerificationSucceeded(VerificationResponseVo verification) {
        if(verification.secondary_credential == null || isUpdatingProfile) {
            startActivity(getActivityAtPosition(PhoneActivity.class, 1));
            return;
        }
        BaseVerificationResponseVo secondaryCredential = verification.secondary_credential;
        DataPointList userData = UserStorage.getInstance().getUserData();
        switch(DataPointVo.DataPointType.fromString(secondaryCredential.verification_type)) {
            // TODO: make this more generic
            case Email:
                DataPointVo baseEmail = userData.getUniqueDataPoint(DataPointVo.DataPointType.Email, new Email());
                baseEmail.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                UserStorage.getInstance().setUserData(userData);
                startActivity(EmailVerificationActivity.class);
                break;
            case BirthDate:
                DataPointVo baseBirthdate = userData.getUniqueDataPoint(DataPointVo.DataPointType.BirthDate, new Birthdate());
                baseBirthdate.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                UserStorage.getInstance().setUserData(userData);
                startActivity(BirthdateVerificationActivity.class);
                break;
            default:
                startActivity(getActivityAtPosition(PhoneActivity.class, 1));
        }
    }

    @Override
    public void phoneVerificationOnBackPressed() {
        startActivity(PhoneActivity.class);
    }

    @Override
    public void emailVerificationSucceeded() {
        if(isCurrentPhoneVerified()) {
            LedgeLinkSdk.getResponseHandler().unsubscribe(this);
            LedgeLinkSdk.getResponseHandler().subscribe(this);
            LedgeLinkUi.loginUser(getLoginData());
        } else {
            Class nextActivity = getActivityAtPosition(EmailVerificationActivity.class, 1);
            if(nextActivity != null) {
                startActivity(nextActivity);
            } else {
                stopModule();
            }
        }
    }

    @Override
    public void emailOnBackPressed() {
        startActivity(mRequiredActivities.get(0));
    }

    @Override
    public void identityVerificationSucceeded() {
        showLoading(true);
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        if (!UserStorage.getInstance().hasBearerToken()) {
            LedgeLinkUi.createUser(UserStorage.getInstance().getUserData());
        } else {
            if (isUpdatingProfile && !mCurrentUserDataCopy.equals(UserStorage.getInstance().getUserData())) {
                HashMap<DataPointVo.DataPointType, List<DataPointVo>> baseDataPoints = mCurrentUserDataCopy.getDataPoints();
                HashMap<DataPointVo.DataPointType, List<DataPointVo>> updatedDataPoints = UserStorage.getInstance().getUserData().getDataPoints();
                DataPointList request = new DataPointList();

                for (DataPointVo.DataPointType type : baseDataPoints.keySet()) {
                    // TO DO: for now assuming only 1 DataPoint is present, will be refactored once DataPoint ID is available
                    DataPointVo baseDataPoint = baseDataPoints.get(type).get(0);
                    DataPointVo updatedDataPoint = updatedDataPoints.get(type).get(0);
                    if (!baseDataPoint.equals(updatedDataPoint)) {
                        request.add(updatedDataPoint);
                    }
                }

                if(!request.getDataPoints().isEmpty()) {
                    LedgeLinkUi.updateUser(request);
                } else {
                    stopModule();
                }
            }
            else {
                stopModule();
            }
        }
    }

    @Override
    public void identityVerificationOnBackPressed() {
        startActivity(getActivityAtPosition(IdentityVerificationActivity.class, -1));
    }

    @Override
    public void addressStored() {
        startActivity(getActivityAtPosition(AddressActivity.class, 1));
    }

    @Override
    public void addressOnBackPressed() {
        startActivity(getActivityAtPosition(AddressActivity.class, -1));
    }

    @Override
    public void timeAtAddressStored() {
        startActivity(getActivityAtPosition(TimeAtAddressActivity.class, 1));
    }

    @Override
    public void timeAtAddressOnBackPressed() {
        startActivity(getActivityAtPosition(TimeAtAddressActivity.class, -1));
    }

    @Override
    public void annualIncomeStored() {
        startActivity(getActivityAtPosition(AnnualIncomeActivity.class, 1));
    }

    @Override
    public void annualIncomeOnBackPressed() {
        startActivity(getActivityAtPosition(AnnualIncomeActivity.class, -1));
    }

    @Override
    public void monthlyIncomeStored() {
        startActivity(getActivityAtPosition(MonthlyIncomeActivity.class, 1));
    }

    @Override
    public void monthlyIncomeOnBackPressed() {
        startActivity(getActivityAtPosition(MonthlyIncomeActivity.class, -1));
    }

    @Override
    public void creditScoreStored() {
        startActivity(getActivityAtPosition(CreditScoreActivity.class, 1));
    }

    @Override
    public void creditScoreOnBackPressed() {
        startActivity(getActivityAtPosition(CreditScoreActivity.class, -1));
    }

    @Override
    public void paydayLoanStored() {
        startActivity(getActivityAtPosition(PaydayLoanActivity.class, 1));
    }

    @Override
    public void paydayLoanOnBackPressed() {
        startActivity(getActivityAtPosition(PaydayLoanActivity.class, -1));
    }

    @Override
    public void armedForcesStored() {
        startActivity(getActivityAtPosition(ArmedForcesActivity.class, 1));
    }

    @Override
    public void armedForcesOnBackPressed() {
        startActivity(getActivityAtPosition(ArmedForcesActivity.class, -1));
    }

    @Override
    public void personalInformationStored() {
        startActivity(getActivityAtPosition(PersonalInformationActivity.class, 1));
    }

    @Override
    public void personalInformationOnBackPressed() {
        startActivity(getActivityAtPosition(PersonalInformationActivity.class, -1));
    }

    @Override
    public void zipCodeAndHousingTypeStored() {
        startActivity(AddressActivity.class);
    }

    @Override
    public void homeOnBackPressed() {
        startActivity(PersonalInformationActivity.class);
    }

    @Override
    public void birthdateSucceeded() {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.loginUser(getLoginData());
    }

    @Override
    public void birthdateOnBackPressed() {
        startActivity(mRequiredActivities.get(0));
    }

    private void startModule() {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);

        if(mRequiredActivities.isEmpty()) {
            onUserHasAllRequiredData.execute();
        } else {
            if (onUserHasAllRequiredData != null) {
                onUserDoesNotHaveAllRequiredData.execute();
            } else {
                startActivity(mRequiredActivities.get(0));
            }
        }
    }

    private void stopModule() {
        showLoading(false);
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        if(isUpdatingProfile) {
            onBack.execute();
        } else {
            onFinish.execute();
        }
    }

    private void storeToken(String token) {
        UserStorage.getInstance().setBearerToken(token);
        SharedPreferencesStorage.storeUserToken(getActivity(), token);
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        SharedPreferencesStorage.storeCredentials(getActivity(), config.primaryAuthCredential, config.secondaryAuthCredential);
    }

    private void storeRequiredData(RequiredDataPointsListResponseVo requiredDataPointsList) {
        UserStorage.getInstance().setRequiredData(requiredDataPointsList);
        mRequiredDataPointList = new LinkedList<>(Arrays.asList(requiredDataPointsList.data));
        if(!isUpdatingProfile) {
            removeSecondaryCredentialFromRequiredList();
        }

        CompletableFuture
                .supplyAsync(() -> ConfigStorage.getInstance().getPOSMode())
                .exceptionally(ex -> {
                    stopModule();
                    return null;
                })
                .thenAccept(this::getCurrentUserOrContinue);
    }

    private void getCurrentUserOrContinue(boolean isPOSMode) {
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), isPOSMode);
        if (isPOSMode || userToken == null || isUpdatingProfile) {
            compareRequiredDataPointsWithCurrent(new DataPointList());
        } else {
            LedgeLinkUi.getApiWrapper().setBearerToken(userToken);
            LedgeLinkUi.getCurrentUser();
        }
    }

    private void compareRequiredDataPointsWithCurrent(DataPointList currentDataPointList) {
        if(!isUpdatingProfile) {
            HashMap<DataPointVo.DataPointType, List<DataPointVo>> currentDataPointMap = currentDataPointList.getDataPoints();

            for(DataPointVo.DataPointType currentType : currentDataPointMap.keySet()) {
                for(Iterator<RequiredDataPointVo> listIterator = mRequiredDataPointList.iterator(); listIterator.hasNext();) {
                    RequiredDataPointVo requiredDataPointVo = listIterator.next();
                    if(requiredDataPointVo.type.equals(currentType)) {
                        if(!requiredDataPointVo.verificationRequired || currentDataPointMap.get(currentType).get(0).isVerified()) {
                            listIterator.remove();
                        }
                    }
                }
            }
        }

        fillRequiredActivitiesList();
        startModule();
    }

    private void removeSecondaryCredentialFromRequiredList() {
        if(!UserStorage.getInstance().hasBearerToken()) {
            return;
        }
        String secondaryCredential = UIStorage.getInstance().getContextConfig().secondaryAuthCredential;
        DataPointVo.DataPointType credentialType = DataPointVo.DataPointType.fromString(secondaryCredential);
        for(Iterator<RequiredDataPointVo> listIterator = mRequiredDataPointList.iterator(); listIterator.hasNext();) {
            RequiredDataPointVo requiredDataPointVo = listIterator.next();
            if(requiredDataPointVo.type.equals(credentialType)) {
                listIterator.remove();
            }
        }
    }

    private void fillRequiredActivitiesList() {
        if(!mRequiredDataPointList.isEmpty()) {
            for (RequiredDataPointVo requiredDataPointVo : mRequiredDataPointList) {
                switch(requiredDataPointVo.type) {
                    case PersonalName:
                        addRequiredActivity(PersonalInformationActivity.class);
                        break;
                    case Phone:
                        if(!mRequiredActivities.contains(PhoneActivity.class)) {
                            mRequiredActivities.add(0, PhoneActivity.class);
                        }
                        break;
                    case Email:
                        addRequiredActivity(PersonalInformationActivity.class);
                        if(requiredDataPointVo.verificationRequired) {
                            addRequiredActivity(EmailVerificationActivity.class);
                        }
                        break;
                    case Address:
                    case Housing:
                        addRequiredActivity(HomeActivity.class);
                        addRequiredActivity(AddressActivity.class);
                        break;
                    case TimeAtAddress:
                        addRequiredActivityAfterGivenActivity(TimeAtAddressActivity.class,
                                AddressActivity.class);
                        break;
                    case IncomeSource:
                        addRequiredActivity(AnnualIncomeActivity.class);
                        break;
                    case Income:
                        addRequiredActivity(AnnualIncomeActivity.class);
                        addRequiredActivity(MonthlyIncomeActivity.class);
                        break;
                    case CreditScore:
                        addRequiredActivity(CreditScoreActivity.class);
                        break;
                    case PayDayLoan:
                        addRequiredActivity(PaydayLoanActivity.class);
                        break;
                    case MemberOfArmedForces:
                        addRequiredActivity(ArmedForcesActivity.class);
                        break;
                    case SSN:
                    case BirthDate:
                        addRequiredActivity(IdentityVerificationActivity.class);
                        break;
                }
            }
        }
        UserDataPresenter.TOTAL_STEPS = mRequiredActivities.size();
    }

    private void addRequiredActivity(Class requiredActivity) {
        if(!mRequiredActivities.contains(requiredActivity)) {
            mRequiredActivities.add(requiredActivity);
        }
    }

    private void addRequiredActivityAfterGivenActivity(Class requiredActivity, Class givenActivity) {
        if(mRequiredActivities.contains(requiredActivity)) {
            return;
        }
        if(mRequiredActivities.contains(givenActivity)) {
            mRequiredActivities.add(mRequiredActivities.indexOf(givenActivity)+1, requiredActivity);
        }
        else {
            addRequiredActivity(requiredActivity);
        }
    }

    public int getRequiredActivityPosition(Class<? extends AppCompatActivity> activity) {
        return mRequiredActivities.indexOf(activity);
    }

    private Class getActivityAtPosition(Class currentActivity, int positionOffset) {
        Class result = null;
        int currentIndex = mRequiredActivities.indexOf(currentActivity);
        if(currentIndex != -1) {
            int targetIndex = currentIndex + positionOffset;
            if (targetIndex >= 0 && targetIndex < mRequiredActivities.size()) {
                result = mRequiredActivities.get(targetIndex);
            }
        }

        return result;
    }

    private boolean isPhoneVerificationRequired() {
        String primaryCredential = UIStorage.getInstance().getContextConfig().primaryAuthCredential;
        String secondaryCredential = UIStorage.getInstance().getContextConfig().secondaryAuthCredential;
        return DataPointVo.DataPointType.fromString(primaryCredential).equals(DataPointVo.DataPointType.Phone)
                || DataPointVo.DataPointType.fromString(secondaryCredential).equals(DataPointVo.DataPointType.Phone);
    }

    private boolean isCurrentPhoneVerified() {
        DataPointList currentData = UserStorage.getInstance().getUserData();
        PhoneNumberVo currentPhone = (PhoneNumberVo) currentData.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
        if(isUpdatingProfile) {
            PhoneNumberVo basePhone = (PhoneNumberVo) mCurrentUserDataCopy.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
            return basePhone.getPhone().equals(currentPhone.getPhone());
        }
        return currentPhone.isVerified();
    }

    private boolean isEmailVerificationRequired() {
        boolean isVerificationRequired = false;
        for (RequiredDataPointVo requiredDataPointVo : mRequiredDataPointList) {
            if (requiredDataPointVo.type.equals(DataPointVo.DataPointType.Email) && requiredDataPointVo.verificationRequired) {
                isVerificationRequired = true;
            }
        }
        return isVerificationRequired;
    }

    private boolean isCurrentEmailVerified() {
        DataPointList currentData = UserStorage.getInstance().getUserData();
        DataPointVo currentEmail = currentData.getUniqueDataPoint(DataPointVo.DataPointType.Email, null);
        if(currentEmail == null) {
            return false;
        }
        if(isUpdatingProfile) {
            DataPointVo baseEmail = mCurrentUserDataCopy.getUniqueDataPoint(DataPointVo.DataPointType.Email, null);
            return baseEmail.equals(currentEmail);
        }
        return currentEmail.isVerified();
    }

    private LoginRequestVo getLoginData() {
        DataPointList base = UserStorage.getInstance().getUserData();
        String primaryCredential = UIStorage.getInstance().getContextConfig().primaryAuthCredential;
        DataPointVo primaryDataPoint = base.getUniqueDataPoint(DataPointVo.DataPointType.fromString(primaryCredential), null);

        String secondaryCredential = UIStorage.getInstance().getContextConfig().secondaryAuthCredential;
        DataPointVo secondaryDataPoint = base.getUniqueDataPoint(DataPointVo.DataPointType.fromString(secondaryCredential), null);

        VerificationVo[] verificationList = new VerificationVo[2];
        verificationList[0] = primaryDataPoint.getVerification();
        verificationList[1] = secondaryDataPoint.getVerification();

        ListRequestVo<VerificationVo[]> verifications = new ListRequestVo<>();
        verifications.data = verificationList;

        return new LoginRequestVo(verifications);
    }

    private void showLoading(boolean show) {
        if(mProgressDialog == null) {
            return;
        }
        if (show) {
            mProgressDialog = ProgressDialog.show(getActivity(),null,null);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.include_rl_loading_transparent);
        } else {
            mProgressDialog.dismiss();
        }
    }
}