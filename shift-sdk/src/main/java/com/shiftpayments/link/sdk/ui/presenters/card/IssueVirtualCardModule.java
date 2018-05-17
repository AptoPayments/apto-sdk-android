package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.ui.activities.card.IssueVirtualCardActivity;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

public class IssueVirtualCardModule extends ShiftBaseModule implements IssueVirtualCardDelegate {
    public Command onKycNotPassed;

    public IssueVirtualCardModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        getActivity().startActivity(new Intent(getActivity(), IssueVirtualCardActivity.class));
    }

    @Override
    public void onCardIssued() {
        this.onFinish.execute();
    }

    @Override
    public void onBack() {
        this.onBack.execute();
    }
}
