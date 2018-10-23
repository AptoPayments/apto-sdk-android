package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.CardApplicationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.DisclaimerConfiguration;
import com.shiftpayments.link.sdk.api.vos.responses.config.DataPointGroupVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CollectUserDataActionConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.BalanceStoreModule;
import com.shiftpayments.link.sdk.ui.presenters.showdisclaimer.showgenericmessage.ShowDisclaimerModule;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;
import com.shiftpayments.link.sdk.ui.workflow.ActionNotSupportedModule;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowModule;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObjectStatusInterface;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.COLLECT_USER_DATA;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.ISSUE_CARD;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.SELECT_BALANCE_STORE;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.SHOW_DISCLAIMER;

public class NewCardModule extends WorkflowModule {

    public NewCardModule(Activity activity,
                         WorkflowObjectStatusInterface getWorkflowObjectStatus, Command onFinish,
                         Command onBack, Command onError) {
        super(activity, getWorkflowObjectStatus, onFinish, onBack, onError);
    }

    @Override
    public void initialModuleSetup() {
        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.createCardApplication(ConfigStorage.getInstance().getCardConfig().cardProduct.id);
    }

    @Override
    public void startNextModule() {
        mWorkFlowObject = getWorkflowObjectStatus.getApplication(mWorkFlowObject);
        startAppropriateModule();
    }

    /**
     * Called when create card application has been received.
     * @param application Card Application.
     */
    @Subscribe
    public void handleApplication(CardApplicationResponseVo application) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        ApplicationVo cardApplication = new ApplicationVo(application.id, application.nextAction, application.workflowObjectId);
        CardStorage.getInstance().setApplication(cardApplication);
        mWorkFlowObject = cardApplication;
        startAppropriateModule();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        super.showError(error);
    }

    private void startAppropriateModule() {
        switch (mWorkFlowObject.nextAction.actionType) {
            case COLLECT_USER_DATA:
                startUserDataCollector();
                break;
            case SELECT_BALANCE_STORE:
                startBalanceStoreModule();
                break;
            case ISSUE_CARD:
                issueVirtualCard();
                break;
            case SHOW_DISCLAIMER:
                showDisclaimer();
                break;
            default:
                new ActionNotSupportedModule(getActivity(), this.onFinish, this.onBack).initialModuleSetup();
        }
    }

    private void startUserDataCollector() {
        CollectUserDataActionConfigurationVo configuration = (CollectUserDataActionConfigurationVo) mWorkFlowObject.nextAction.configuration;
        List<RequiredDataPointVo> requiredDataPointList = new ArrayList<>();
        for(DataPointGroupVo dataPointGroup : configuration.requiredDataPointsList.data) {
            requiredDataPointList.addAll(Arrays.asList(dataPointGroup.datapoints.data));
        }
        ConfigStorage.getInstance().setRequiredUserData(requiredDataPointList.toArray(new RequiredDataPointVo[0]));
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity(), this::startNextModule, super.onBack);
        UserDataCollectorConfigurationVo config = new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_title_issue_card), new CallToActionVo(getActivity().getString(R.string.id_verification_next_button_issue_card)));
        ConfigStorage.getInstance().setUserDataCollectorConfig(config);
        startModule(userDataCollectorModule);
    }

    private void startBalanceStoreModule() {
        BalanceStoreModule balanceStoreModule = BalanceStoreModule.getInstance(this.getActivity(), this::startNextModule, super.onBack);
        startModule(balanceStoreModule);
    }

    private void issueVirtualCard() {
        setCurrentModule();
        IssueVirtualCardModule issueVirtualCardModule = new IssueVirtualCardModule(getActivity(), this.onFinish, this::onIssueVirtualCardBackPressed, this.onError);
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

    private void showDisclaimer() {
        Command onFinishCallback = ()->{
            setCurrentModule();
            this.startNextModule();
        };

        Command onCanceledCallback = ()->{
            final String applicationId = CardStorage.getInstance().getApplication().applicationId;
            ShiftSdk.cancelCardApplication(applicationId);
            onBack.execute();
        };

        ShowDisclaimerModule disclaimerModule = ShowDisclaimerModule.getInstance(getActivity(),
                onFinishCallback, onCanceledCallback, (DisclaimerConfiguration) mWorkFlowObject.nextAction.configuration,
                mWorkFlowObject.workflowObjectId, mWorkFlowObject.nextAction.actionId);
        disclaimerModule.initialModuleSetup();
    }
}
