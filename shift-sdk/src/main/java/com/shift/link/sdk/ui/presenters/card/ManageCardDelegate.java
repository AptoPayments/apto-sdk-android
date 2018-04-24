package com.shift.link.sdk.ui.presenters.card;

import com.shift.link.sdk.api.vos.responses.SessionExpiredErrorVo;

/**
 * Created by Adrian on 23/04/2018.
 */

public interface ManageCardDelegate {

    void onSessionExpired(SessionExpiredErrorVo error);

}
