package com.shift.link.sdk.ui.presenters.loanapplication;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shift.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;

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
