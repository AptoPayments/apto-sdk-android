package me.ledge.link.sdk.ui.presenters.loanapplication;

import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;

/**
 * Delegation interface for the intermediate loan application screen.
 *
 * @author Adrian
 */
public interface IntermediateLoanApplicationDelegate {

    void showNext(IntermediateLoanApplicationModel model);
    void showPrevious(IntermediateLoanApplicationModel model);
}
