package me.ledge.link.sdk.ui.presenters.loanapplication;

import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;

/**
 * Delegation interface for the the loan summary screen.
 *
 * @author Adrian
 */
public interface LoanApplicationSummaryDelegate {

    void loanApplicationSummaryShowPrevious(LoanApplicationSummaryModel model);
    void onApplicationReceived();
}
