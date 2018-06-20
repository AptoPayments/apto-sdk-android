package com.shiftpayments.link.sdk.ui.presenters.link;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.link.LoanAmountActivity;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanInfoModule extends ShiftBaseModule implements LoanDataDelegate {

    private static LoanInfoModule mInstance;

    public static synchronized  LoanInfoModule getInstance(Activity activity, Command onFinish, Command onBack) {
        if (mInstance == null) {
            mInstance = new LoanInfoModule(activity, onFinish, onBack);
        }
        return mInstance;
    }

    private LoanInfoModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(LoanAmountActivity.class);
    }

    @Override
    public void loanDataPresented() {
        onFinish.execute();
    }

    @Override
    public void loanDataOnBackPressed() {
        onBack.execute();
    }

    @Override
    public void onUpdateUserProfile() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity(), this.onBack, this.onBack);
        UserDataCollectorConfigurationVo config = new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_update_profile_title), new CallToActionVo(getActivity().getString(R.string.id_verification_update_profile_button)));
        userDataCollectorModule.setCallToActionConfig(config);
        userDataCollectorModule.isUpdatingProfile = true;
        startModule(userDataCollectorModule);
    }
}
