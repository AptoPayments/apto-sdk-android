package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.link.LoanAmountActivity;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a rejected loan application.
 * @author Wijnand
 */
public class RejectedLoanApplicationModel
        extends AbstractLoanApplicationModel
        implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link RejectedLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public RejectedLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return LoanAmountActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_sad;
    }

    /** {@inheritDoc} */
    @Override
    public boolean showOffersButton() {
        return true;
    }
}
