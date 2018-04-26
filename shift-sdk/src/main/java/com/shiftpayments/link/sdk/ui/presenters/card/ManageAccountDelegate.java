package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.workflow.Command;

/**
 * Created by Adrian on 23/04/2018.
 */

public interface ManageAccountDelegate {

    void onSignOut();
    void addFundingSource(Command onFinishCallback);
    void onSessionExpired(SessionExpiredErrorVo error);

}
