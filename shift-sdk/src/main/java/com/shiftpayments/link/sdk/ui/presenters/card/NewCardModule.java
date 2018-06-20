package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.config.DataPointGroupVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CollectUserDataActionConfigurationVo;
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

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.COLLECT_USER_DATA;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.ISSUE_CARD;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.SELECT_BALANCE_STORE;

public class NewCardModule extends WorkflowModule {

    private RequiredDataPointsListResponseVo mFinalRequiredUserData;

    public NewCardModule(Activity activity, WorkflowObject workflowObject,
                         WorkflowObjectStatusInterface getWorkflowObjectStatus, Command onFinish,
                         Command onBack) {
        super(activity, workflowObject, getWorkflowObjectStatus, onFinish, onBack);
    }

    @Override
    public void startNextModule() {
        switch (mWorkFlowObject.nextAction.actionType) {
            case COLLECT_USER_DATA:
                startUserDataCollector();
                break;
            case SELECT_BALANCE_STORE:
                startCustodianModule();
                break;
            case ISSUE_CARD:
                issueVirtualCard();
                break;
            default:
                new ActionNotSupportedModule(getActivity(), this.onFinish, this.onBack).initialModuleSetup();
        }
    }

    private void startUserDataCollector() {
        CollectUserDataActionConfigurationVo configuration = (CollectUserDataActionConfigurationVo) mWorkFlowObject.nextAction.configuration;
        DataPointGroupVo dataPointGroup = configuration.requiredDataPointsList.data[0];
        ConfigStorage.getInstance().setRequiredUserData(dataPointGroup.datapoints);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity(), this::startNextModule, super.onBack);
        UserDataCollectorConfigurationVo config = new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_title_issue_card), new CallToActionVo(getActivity().getString(R.string.id_verification_next_button_issue_card)));
        userDataCollectorModule.setCallToActionConfig(config);
        userDataCollectorModule.isUpdatingProfile = false;
        startModule(userDataCollectorModule);
    }

    private void startCustodianModule() {
        startCustodianModule(this::startNextModule, super.onBack);
    }

    private void startCustodianModule(Command onFinishCallback, Command onBackCallback) {
        Command onFinish = ()->{
            setCurrentModule();
            onFinishCallback.execute();
        };
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(this.getActivity(), onFinish, onBackCallback);
        startModule(custodianSelectorModule);
    }

    private void issueVirtualCard() {
        setCurrentModule();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        IssueVirtualCardModule issueVirtualCardModule = new IssueVirtualCardModule(getActivity(), this.onFinish, this::onIssueVirtualCardBackPressed);
        startModule(issueVirtualCardModule);
    }

    private void onIssueVirtualCardBackPressed() {
        SoftReference<ShiftBaseModule> userDataCollectorModule = new SoftReference<>(UserDataCollectorModule.getInstance(getActivity(), this.onFinish, super.onBack));
        ModuleManager.getInstance().setModule(userDataCollectorModule);
    }

    private void setCurrentModule() {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
    }
}
