package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.UserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.AddressActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.ArmedForcesActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.CreditScoreActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.HomeActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.PaydayLoanActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.PhoneActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.TimeAtAddressActivity;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java8.util.concurrent.CompletableFuture;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends ShiftBaseModule implements PhoneDelegate,
        IdentityVerificationDelegate, AddressDelegate, AnnualIncomeDelegate, MonthlyIncomeDelegate,
        CreditScoreDelegate, PersonalInformationDelegate, HomeDelegate, PaydayLoanDelegate,
        ArmedForcesDelegate, TimeAtAddressDelegate {

    private static UserDataCollectorModule instance;
    public LinkedList<RequiredDataPointVo> mRequiredDataPointList;
    public boolean isUpdatingProfile;
    private ArrayList<Class<? extends MvpActivity>> mRequiredActivities;
    private DataPointList mCurrentUserDataCopy;
    private UserDataCollectorConfigurationVo mCallToAction;

    private UserDataCollectorModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
        mRequiredDataPointList = new LinkedList<>();
        mRequiredActivities = new ArrayList<>();
    }

    public static synchronized UserDataCollectorModule getInstance(Activity activity, Command onFinish, Command onBack) {
        if (instance == null) {
            instance = new UserDataCollectorModule(activity, onFinish, onBack);
        }
        else {
            instance.onFinish = onFinish;
            instance.onBack = onBack;
        }
        return instance;
    }

    @Override
    public void initialModuleSetup() {
        mRequiredActivities.clear();
        mCurrentUserDataCopy = new DataPointList(UserStorage.getInstance().getUserData());
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ShiftLinkSdk.getResponseHandler().subscribe(this);
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
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        UserStorage.getInstance().setUserData(userInfo);
        compareRequiredDataPointsWithCurrent(userInfo);
    }

    /**
     * Deals with the update user API response.
     * @param response API response.
     */
    @Subscribe
    public void handleUserDetails(UserResponseVo response) {
        showLoading(false);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        onFinish.execute();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if(error.request_path.equals(ShiftApiWrapper.GET_CURRENT_USER_PATH) && error.statusCode == 401) {
            ShiftPlatform.clearUserToken(getActivity());
        }
        stopModule();
    }

    /**
     * Called when session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        super.handleSessionExpiredError(error);
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
        startActivity(getActivityAtPosition(PhoneActivity.class, 1));
    }

    @Override
    public void phoneOnBackPressed() {
        onBack.execute();
    }


    @Override
    public void identityVerificationSucceeded() {
        stopModule();
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
        Class previousActivity = getActivityAtPosition(PersonalInformationActivity.class, -1);
        if(previousActivity==null) {
            onBack.execute();
        }
        else {
            startActivity(previousActivity);
        }

    }

    @Override
    public void zipCodeAndHousingTypeStored() {
        startActivity(AddressActivity.class);
    }

    @Override
    public void homeOnBackPressed() {
        startActivity(PersonalInformationActivity.class);
    }

    private void startModule() {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);

        if(mRequiredActivities.isEmpty()) {
            stopModule();
        } else {
            startActivity(mRequiredActivities.get(0));
        }
    }

    private void stopModule() {
        updateUser();
    }

    private void storeToken(String token) {
        UserStorage.getInstance().setBearerToken(token);
        SharedPreferencesStorage.storeUserToken(getActivity(), token);
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        SharedPreferencesStorage.storeCredentials(getActivity(), config.primaryAuthCredential, config.secondaryAuthCredential);
    }

    private void updateUser() {
        showLoading(true);
        HashMap<DataPointVo.DataPointType, List<DataPointVo>> baseDataPoints = mCurrentUserDataCopy.getDataPoints();
        HashMap<DataPointVo.DataPointType, List<DataPointVo>> updatedDataPoints = UserStorage.getInstance().getUserData().getDataPoints();
        DataPointList request = new DataPointList();

        for (DataPointVo.DataPointType type : updatedDataPoints.keySet()) {
            // TO DO: for now assuming only 1 DataPoint is present, will be refactored once DataPoint ID is available
            DataPointVo updatedDataPoint = updatedDataPoints.get(type).get(0);
            if(baseDataPoints.containsKey(type)) {
                DataPointVo baseDataPoint = baseDataPoints.get(type).get(0);
                if (!baseDataPoint.equals(updatedDataPoint)) {
                    request.add(updatedDataPoint);
                }
            }
            else {
                // New DataPoint
                request.add(updatedDataPoint);
            }
        }

        if(!request.getDataPoints().isEmpty()) {
            ShiftLinkSdk.getResponseHandler().subscribe(this);
            ShiftPlatform.updateUser(request);
        }
        else {
            showLoading(false);
            onFinish.execute();
        }
    }

    private void storeRequiredData(RequiredDataPointVo[] requiredDataPointsList) {
        mRequiredDataPointList = new LinkedList<>(Arrays.asList(requiredDataPointsList));
        if(!isUpdatingProfile) {
            removePrimaryCredentialFromRequiredList();
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
            ShiftPlatform.getApiWrapper().setBearerToken(userToken);
            ShiftPlatform.getCurrentUser(false);
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

    private void removePrimaryCredentialFromRequiredList() {
        String primaryCredential = UIStorage.getInstance().getContextConfig().primaryAuthCredential;
        removeCredentialFromRequiredList(primaryCredential);
    }

    private void removeSecondaryCredentialFromRequiredList() {
        if(!UserStorage.getInstance().hasBearerToken()) {
            return;
        }
        String secondaryCredential = UIStorage.getInstance().getContextConfig().secondaryAuthCredential;
        removeCredentialFromRequiredList(secondaryCredential);
    }

    private void removeCredentialFromRequiredList(String credential) {
        DataPointVo.DataPointType credentialType = DataPointVo.DataPointType.fromString(credential);
        for(Iterator<RequiredDataPointVo> listIterator = mRequiredDataPointList.iterator(); listIterator.hasNext();) {
            RequiredDataPointVo requiredDataPointVo = listIterator.next();
            if(requiredDataPointVo.type.equals(credentialType)) {
                listIterator.remove();
                break;
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

    public UserDataCollectorConfigurationVo getCallToActionConfig() {
        return mCallToAction;
    }

    public void setCallToActionConfig(UserDataCollectorConfigurationVo callToActionConfig) {
        mCallToAction = callToActionConfig;
    }
}