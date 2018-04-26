package com.shiftpayments.link.sdk.ui.presenters.loanapplication;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;

/**
 * Delegation interface for selecting loan applications.
 *
 * @author Adrian
 */
public interface SelectLoanApplicationListDelegate {
    void selectLoanApplicationListOnBackPressed();

    void newApplicationPressed();

    void applicationSelected(SelectLoanApplicationModel model);

    LoanApplicationsSummaryListResponseVo getLoanApplicationsSummaryList();
}
