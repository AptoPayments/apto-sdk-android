package me.ledge.link.sdk.ui.models.loanapplication.documents;

import android.app.Activity;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for a loan application that the user is adding documents to.
 * @author Wijnand
 */
public class AddDocumentsListModel implements ActivityModel, Model {

    private final LoanApplicationDetailsResponseVo mLoanApplication;

    /**
     * Creates a new {@link AddDocumentsListModel} instance.
     * @param application Loan application details.
     */
    public AddDocumentsListModel(LoanApplicationDetailsResponseVo application) {
        mLoanApplication = application;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.add_documents_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return IntermediateLoanApplicationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return LedgeLinkUi.getHandlerConfiguration().getApplicationsListActivity();
    }

    /**
     * @return The list of required actions.
     */
    public LoanApplicationActionVo[] getRequiredActions() {
        LoanApplicationActionVo[] actions = null;

        if (mLoanApplication != null && mLoanApplication.required_actions != null) {
            actions = mLoanApplication.required_actions.data;
        }

        return actions;
    }
}