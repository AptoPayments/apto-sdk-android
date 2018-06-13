package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CustodianSelectorModule;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.workflow.ActionNotSupportedModule;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowModule;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObject;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObjectStatusInterface;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.COLLECT_USER_DATA;
import static com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo.DataPointType.PersonalName;

public class NewCardModule extends WorkflowModule {

    private RequiredDataPointsListResponseVo mFinalRequiredUserData;

    public NewCardModule(Activity activity, WorkflowObject workflowObject, WorkflowObjectStatusInterface getWorkflowObjectStatus) {
        super(activity, workflowObject, getWorkflowObjectStatus);
    }

    @Override
    public void startNextModule() {
        switch (mWorkFlowObject.nextAction.actionType) {
            case COLLECT_USER_DATA:
                collectInitialUserData();
                return;
            default:
                new ActionNotSupportedModule(getActivity()).initialModuleSetup();
        }
    }

    private void collectInitialUserData() {
        storeFinalRequiredDataPoints();
        setInitialRequiredDataPoints();
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity());
        // TODO: read next action
        userDataCollectorModule.onFinish = this::startCustodianModule;
        userDataCollectorModule.onBack = super.onBack;
        userDataCollectorModule.isUpdatingProfile = false;
        userDataCollectorModule.onTokenRetrieved = null;
        startModule(userDataCollectorModule);
    }

    private void collectFinalUserData() {
        ConfigStorage.getInstance().setRequiredUserData(mFinalRequiredUserData);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity());
        UserDataCollectorConfigurationVo config = new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_title_issue_card), new CallToActionVo(getActivity().getString(R.string.id_verification_next_button_issue_card)));
        userDataCollectorModule.setCallToActionConfig(config);
        userDataCollectorModule.onFinish = this::issueVirtualCard;
        userDataCollectorModule.onBack = super.onBack;
        // TODO
        userDataCollectorModule.isUpdatingProfile = false;
        //userDataCollectorModule.isUpdatingProfile = mIsExistingUser;
        userDataCollectorModule.onTokenRetrieved = null;
        startModule(userDataCollectorModule);
    }

    private void setInitialRequiredDataPoints() {
        // TODO: we need to split the user data collection in two
        // 1) initial user data: first name + last name
        // 2) final user data: rest of the required datapoints
        RequiredDataPointsListResponseVo initialRequiredUserData = new RequiredDataPointsListResponseVo();
        RequiredDataPointVo[] requiredDataPointList = new RequiredDataPointVo[1];
        requiredDataPointList[0] = new RequiredDataPointVo(PersonalName);
        initialRequiredUserData.data = requiredDataPointList;
        ConfigStorage.getInstance().setRequiredUserData(initialRequiredUserData);
    }

    private void storeFinalRequiredDataPoints() {
        mFinalRequiredUserData = ConfigStorage.getInstance().getRequiredUserData();
        RequiredDataPointVo[] requiredDataPointArray = mFinalRequiredUserData.data;
        List<RequiredDataPointVo> requiredDataPointsList = new ArrayList<>(Arrays.asList(requiredDataPointArray));
        for (Iterator<RequiredDataPointVo> iter = requiredDataPointsList.listIterator(); iter.hasNext(); ) {
            RequiredDataPointVo dataPoint = iter.next();
            if (dataPoint.type.equals(PersonalName)) {
                iter.remove();
            }
        }

        mFinalRequiredUserData.data = requiredDataPointsList.toArray(new RequiredDataPointVo[requiredDataPointsList.size()]);
    }

    private void startCustodianModule() {
        startCustodianModule(super.onBack, this::collectFinalUserData);
    }

    private void startCustodianModule(Command onBackCallback, Command onFinishCallback) {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(this.getActivity());
        custodianSelectorModule.onBack = onBackCallback;
        custodianSelectorModule.onFinish = ()->{
            setCurrentModule();
            onFinishCallback.execute();
        };
        startModule(custodianSelectorModule);
    }

    private void issueVirtualCard() {
        setCurrentModule();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        IssueVirtualCardModule issueVirtualCardModule = new IssueVirtualCardModule(getActivity());
        issueVirtualCardModule.onFinish = super.onFinish;
        issueVirtualCardModule.onBack = () -> {
            SoftReference<ShiftBaseModule> userDataCollectorModule = new SoftReference<>(UserDataCollectorModule.getInstance(getActivity()));
            ModuleManager.getInstance().setModule(userDataCollectorModule);
        };
        startModule(issueVirtualCardModule);
    }

    private void setCurrentModule() {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
    }
}
