package me.ledge.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;

/**
 * Concrete {@link LoanApplicationDetailsModel} for a loan application in the {@code PENDING_BORROWER_ACTION} state with
 * the {@code AGREE_TERMS} action.
 * @author Wijnand
 */
public class ApprovedLoanApplicationDetailsModel extends LoanApplicationDetailsModel {

    /**
     * Creates a new {@link ApprovedLoanApplicationDetailsModel} instance.
     * @param application Loan application details.
     * @param resources Android application resources.
     * @param loader Image loader.
     */
    public ApprovedLoanApplicationDetailsModel(LoanApplicationDetailsResponseVo application, Resources resources,
            GenericImageLoader loader) {

        super(application, resources, loader);
    }

    /** {@inheritDoc} */
    @Override
    public String getStatusText() {
        return mResources.getString(R.string.loan_application_details_status_approved);
    }

    /** {@inheritDoc} */
    @Override
    public int getStatusColor() {
        return mResources.getColor(R.color.llsdk_application_approved_color);
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
