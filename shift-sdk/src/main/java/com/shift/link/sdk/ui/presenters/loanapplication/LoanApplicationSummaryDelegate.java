package com.shift.link.sdk.ui.presenters.loanapplication;

import com.shift.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import com.shift.link.sdk.ui.vos.ApplicationVo;

/**
 * Delegation interface for the the loan summary screen.
 *
 * @author Adrian
 */
public interface LoanApplicationSummaryDelegate {

    void loanApplicationSummaryShowPrevious(LoanApplicationSummaryModel model);
    void onApplicationReceived(ApplicationVo application);
}
