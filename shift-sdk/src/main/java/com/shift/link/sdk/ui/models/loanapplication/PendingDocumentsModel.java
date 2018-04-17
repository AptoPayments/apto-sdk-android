package com.shift.link.sdk.ui.models.loanapplication;

import android.app.Activity;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.loanapplication.AddDocumentsListActivity;

/**
 * Concrete {@link IntermediateLoanApplicationModel} for a loan application that requires more documents from the user.
 * @author Wijnand
 */
public class PendingDocumentsModel extends AbstractLoanApplicationModel implements IntermediateLoanApplicationModel {

    /**
     * Creates a new {@link PendingDocumentsModel} instance.
     * @param loanApplication Loan application details.
     */
    public PendingDocumentsModel(LoanApplicationDetailsResponseVo loanApplication) {
        super(loanApplication);
    }

    /** {@inheritDoc} */
    @Override
    public int getCloudImageResource() {
        return R.drawable.icon_cloud_arrow_up;
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(
                true,
                R.string.loan_application_button_documents_pending,
                BigButtonModel.Action.UPLOAD_DOCUMENTS
        );
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return AddDocumentsListActivity.class;
    }
}
