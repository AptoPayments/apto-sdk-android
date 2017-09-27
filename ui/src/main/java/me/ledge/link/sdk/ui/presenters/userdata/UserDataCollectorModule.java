package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
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
import me.ledge.link.sdk.ui.activities.userdata.TimeAtAddressActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneVerificationDelegate,
        EmailVerificationDelegate, IdentityVerificationDelegate, AddressDelegate,
        AnnualIncomeDelegate, MonthlyIncomeDelegate, CreditScoreDelegate,
        PersonalInformationDelegate, HomeDelegate, PaydayLoanDelegate, ArmedForcesDelegate,
        TimeAtAddressDelegate {

    private static UserDataCollectorModule instance;
    public Command onFinish;
    public Command onBack;
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
    public void handleToken(CreateUserResponseVo response) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        if (response != null) {
            UserStorage.getInstance().setBearerToken(response.user_token);
            SharedPreferencesStorage.storeUserToken(getActivity(), response.user_token);
            stopModule();
        }
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

    @Override
    public void phoneVerificationSucceeded(VerificationResponseVo secondaryCredential) {
        if(isEmailVerificationRequired() && !isCurrentEmailVerified()) {
            startActivity(EmailVerificationActivity.class);
        } else {
            startActivity(getActivityAtPosition(PersonalInformationActivity.class, 1));
        }
    }

    @Override
    public void phoneVerificationOnBackPressed() {
        startActivity(PersonalInformationActivity.class);
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
        if (TextUtils.isEmpty(UserStorage.getInstance().getBearerToken())) {
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
        if(isPhoneVerificationRequired() && !isCurrentPhoneVerified()) {
            startActivity(PhoneVerificationActivity.class);
        } else {
            startActivity(getActivityAtPosition(PersonalInformationActivity.class, 1));
        }
    }

    @Override
    public void personalInformationOnBackPressed() {
        onBack.execute();
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
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);

        if(mRequiredActivities.isEmpty()) {
            onUserDoesNotHaveAllRequiredData.execute();
        } else {
            if (onUserHasAllRequiredData != null) {
                onUserHasAllRequiredData.execute();
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

    private void storeRequiredData(RequiredDataPointsListResponseVo requiredDataPointsList) {
        UserStorage.getInstance().setRequiredData(requiredDataPointsList);
        mRequiredDataPointList = new LinkedList<>(Arrays.asList(requiredDataPointsList.data));

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

    private void fillRequiredActivitiesList() {
        if(!mRequiredDataPointList.isEmpty()) {
            for (RequiredDataPointVo requiredDataPointVo : mRequiredDataPointList) {
                switch(requiredDataPointVo.type) {
                    case PersonalName:
                        addRequiredActivity(PersonalInformationActivity.class);
                        break;
                    case Phone:
                        addRequiredActivity(PersonalInformationActivity.class);
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
                }
            }
            addRequiredActivity(IdentityVerificationActivity.class);
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
        boolean isPhoneVerificationRequired = false;
        for (RequiredDataPointVo requiredDataPointVo : mRequiredDataPointList) {
            if (requiredDataPointVo.type.equals(DataPointVo.DataPointType.Phone) && requiredDataPointVo.verificationRequired) {
                isPhoneVerificationRequired = true;
            }
        }
        return isPhoneVerificationRequired;
    }

    private boolean isCurrentPhoneVerified() {
        DataPointList currentData = UserStorage.getInstance().getUserData();
        DataPointVo currentPhone = currentData.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
        if(isUpdatingProfile) {
            DataPointVo basePhone = mCurrentUserDataCopy.getUniqueDataPoint(DataPointVo.DataPointType.Phone, null);
            return basePhone.equals(currentPhone);
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
        if(isUpdatingProfile) {
            DataPointVo baseEmail = mCurrentUserDataCopy.getUniqueDataPoint(DataPointVo.DataPointType.Email, null);
            return baseEmail.equals(currentEmail);
        }
        return currentEmail.isVerified();
    }

    private DataPointList getLoginData() {
        DataPointList base = UserStorage.getInstance().getUserData();
        Email emailAddress = (Email) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Email, new Email());
        PhoneNumberVo phoneNumber = (PhoneNumberVo) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Phone, new PhoneNumberVo());

        DataPointList data = new DataPointList();
        data.add(emailAddress);
        data.add(phoneNumber);
        return data;
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