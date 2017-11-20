package me.ledge.link.sdk.ui.presenters.loanapplication;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import me.ledge.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;

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
