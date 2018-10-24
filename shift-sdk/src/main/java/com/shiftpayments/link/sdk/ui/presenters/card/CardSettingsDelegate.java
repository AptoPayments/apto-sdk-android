package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.workflow.Command;

/**
 * Created by Adrian on 07/08/2018.
 */

public interface CardSettingsDelegate {

    void addFundingSource(Command onFinishCallback);
    void onSessionExpired(SessionExpiredErrorVo error);
    void onGetPinClickHandler();

}
