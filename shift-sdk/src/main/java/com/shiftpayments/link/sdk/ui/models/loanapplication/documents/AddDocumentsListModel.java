package com.shiftpayments.link.sdk.ui.models.loanapplication.documents;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;

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
        // TODO: remove this when add documents action is supported
        return null;
    }

    /**
     * @return The list of required actions.
     */
    public LoanApplicationActionVo[] getRequiredActions() {
        LoanApplicationActionVo[] actions = null;

        // TODO: implement workflow add documents action
        /*if (mLoanApplication != null && mLoanApplication.required_actions != null) {
            actions = mLoanApplication.required_actions.data;
        }*/

        return actions;
    }
}
