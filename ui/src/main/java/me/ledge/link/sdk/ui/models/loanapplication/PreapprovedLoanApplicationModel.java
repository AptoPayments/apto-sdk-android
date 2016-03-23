package me.ledge.link.sdk.ui.models.loanapplication;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that has been pre-approved.
 * @author Wijnand
 */
public class PreApprovedLoanApplicationModel
        extends AbstractLoanApplicationModel
        implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link PreApprovedLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public PreApprovedLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_ckeck;
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(
                true,
                R.string.loan_application_button_pre_approved_loan,
                BigButtonModel.Action.CONFIRM_LOAN
        );
    }
}
