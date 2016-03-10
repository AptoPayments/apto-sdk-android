package me.ledge.link.sdk.ui.models.loanapplication;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Base {@link Model} implementation for a loan application.
 * @author Wijnand
 */
public abstract class AbstractLoanApplicationModel implements ActivityModel, Model {

    protected final LoanApplicationDetailsResponseVo mLoanApplication;

    /**
     * Creates a new {@link AbstractLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public AbstractLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        mLoanApplication = loanApplication;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_application_label;
    }
}
