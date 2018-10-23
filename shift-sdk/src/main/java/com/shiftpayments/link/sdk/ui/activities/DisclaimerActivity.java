package com.shiftpayments.link.sdk.ui.activities;

import android.content.Context;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.requests.users.AcceptDisclaimerRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.DisclaimerUtil;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

/**
 * Activity that loads a view with the given disclaimer.
 * @author Adrian
 */

public class DisclaimerActivity extends DisplayContentActivity {

    @Override
    protected void onStart() {
        super.onStart();
        mView.showButtons(true);
        mView.showToolbar(false);
        ShiftBaseModule currentModule = ModuleManager.getInstance().getCurrentModule();
        if(currentModule != null) {
            currentModule.setActivity(this);
        }
    }

    @Override
    public void acceptClickHandler() {
        AcceptDisclaimerRequestVo request = new AcceptDisclaimerRequestVo(DisclaimerUtil.workflowId, DisclaimerUtil.actionId);
        ShiftSdk.acceptDisclaimer(request);
    }

    @Override
    public void cancelClickHandler() {
        DisclaimerUtil.onCancel.execute();
        this.finish();
    }


    /**
     * Called when the accept disclaimer response has been received
     * @param response None
     */
    @Subscribe
    public void handleResponse(ApiEmptyResponseVo response) {
        DisclaimerUtil.onAccept.execute();
        this.finish();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        ApiErrorUtil.showErrorMessage(error, this);
    }

    public static Intent getDisclaimerIntent(Context context, ContentVo contentVo) {
        Intent intent = new Intent(context, DisclaimerActivity.class);
        intent.putExtra(EXTRA_CONTENT, contentVo);
        return intent;
    }

}
