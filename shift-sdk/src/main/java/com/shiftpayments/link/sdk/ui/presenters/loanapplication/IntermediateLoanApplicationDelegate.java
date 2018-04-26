package com.shiftpayments.link.sdk.ui.presenters.loanapplication;

import com.shiftpayments.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;

/**
 * Delegation interface for the intermediate loan application screen.
 *
 * @author Adrian
 */
public interface IntermediateLoanApplicationDelegate {

    void intermediateLoanApplicationShowNext(IntermediateLoanApplicationModel model);
    void intermediateLoanApplicationShowPrevious(IntermediateLoanApplicationModel model);
}
