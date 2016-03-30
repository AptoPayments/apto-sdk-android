package me.ledge.link.sdk.ui.models.loanapplication;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that has been approved by the lender.
 * @author Wijnand
 */
public class ApprovedLoanApplicationModel
        extends AbstractLoanApplicationModel
        implements IntermediateLoanApplicationModel, Model {

    /**
     * Creates a new {@link ApprovedLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public ApprovedLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_happy;
    }
}
