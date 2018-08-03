package com.shiftpayments.link.sdk.ui.models.loanapplication;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that requires the user to finish the
 * application through an external process.
 * @author Wijnand
 */
public class FinishExternalLoanApplicationModel
        extends AbstractLoanApplicationModel
        implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link FinishExternalLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public FinishExternalLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_cog;
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(
                true,
                R.string.loan_application_button_finish_external,
                BigButtonModel.Action.FINISH_EXTERNAL
        );
    }
}
