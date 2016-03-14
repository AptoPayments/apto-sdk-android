package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import android.content.res.Resources;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;

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

    /**
     * @return Lender name.
     */
    private String getLenderName() {
        String name = "";

        if (mLoanApplication != null && mLoanApplication.offer != null && mLoanApplication.offer.lender != null) {
            name = mLoanApplication.offer.lender.lender_name;
        }

        return name;
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_sad;
    }

    /** {@inheritDoc} */
    @Override
    public int getExplanationTextResource() {
        return R.string.loan_application_explanation_rejected;
    }

    /** {@inheritDoc} */
    @Override
    public String getExplanationText(Resources resources) {
        return resources.getString(getExplanationTextResource(), getLenderName());
    }

    /** {@inheritDoc} */
    @Override
    public boolean showOffersButton() {
        return true;
    }
}
