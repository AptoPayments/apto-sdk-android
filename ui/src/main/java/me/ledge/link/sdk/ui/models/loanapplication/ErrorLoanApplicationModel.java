package me.ledge.link.sdk.ui.models.loanapplication;

import android.content.res.Resources;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that resulted in an API error.
 * @author Wijnand
 */
public class ErrorLoanApplicationModel
        extends AbstractLoanApplicationModel
        implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link ErrorLoanApplicationModel} instance.
     * @param loanApplication Loan application details.
     */
    public ErrorLoanApplicationModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_exclamation;
    }

    /** {@inheritDoc} */
    @Override
    public int getExplanationTextResource() {
        return R.string.loan_application_explanation_error;
    }

    /** {@inheritDoc} */
    @Override
    public String getExplanationText(Resources resources) {
        return resources.getString(getExplanationTextResource());
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(
                true,
                R.string.loan_application_button_retry,
                BigButtonModel.Action.RETRY_LOAN_APPLICATION
        );
    }
}
