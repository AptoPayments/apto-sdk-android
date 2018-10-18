package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.workflow.Command;

/**
 * Created by Adrian on 23/04/2018.
 */

public interface ManageCardDelegate {

    void onSessionExpired(SessionExpiredErrorVo error);
    void addFundingSource(Command onFinishCallback);
    void onManageCardClosed();
}
