package com.shiftpayments.link.sdk.ui.models.loanapplication.documents;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Concrete {@link AddDocumentModel} for adding a bank statement.
 * @author Wijnand
 */
public class AddBankStatementModel extends AbstractAddDocumentModel implements AddDocumentModel {

    /**
     * Creates a new {@link AddBankStatementModel} instance.
     * @param action Loan application action.
     */
    public AddBankStatementModel(LoanApplicationActionVo action) {
        super(action);
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_bank_statement;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitleResourceId(R.string.add_documents_title_unchecked_bank_statement,
                R.string.add_documents_title_checked_bank_statement);
    }
}
