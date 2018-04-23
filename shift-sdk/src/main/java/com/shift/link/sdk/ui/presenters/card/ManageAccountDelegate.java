package com.shift.link.sdk.ui.presenters.card;

import com.shift.link.sdk.ui.workflow.Command;

/**
 * Created by Adrian on 23/04/2018.
 */

public interface ManageAccountDelegate {

    void onSignOut();
    void addFundingSource(Command onFinishCallback);

}
