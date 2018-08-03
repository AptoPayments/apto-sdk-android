package com.shiftpayments.link.sdk.ui.models.loanapplication;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that is pending action by the lender.
 * @author Wijnand
 */
public class PendingLenderActionModel extends AbstractLoanApplicationModel implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link PendingLenderActionModel} instance.
     * @param loanApplication Loan application details.
     */
    public PendingLenderActionModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_cog;
    }
}
