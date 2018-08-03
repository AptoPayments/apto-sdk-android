package com.shiftpayments.link.sdk.ui.models.loanapplication.documents;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Concrete {@link AddDocumentModel} for adding some other document.
 * @author Wijnand
 */
public class AddOtherDocumentModel extends AbstractAddDocumentModel implements AddDocumentModel {

    /**
     * Creates a new {@link AddOtherDocumentModel} instance.
     * @param action Loan application action.
     */
    public AddOtherDocumentModel(LoanApplicationActionVo action) {
        super(action);
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_generic;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitleResourceId(R.string.add_documents_title_unchecked_other,
                R.string.add_documents_title_checked_other);
    }
}
