package com.shiftpayments.link.sdk.ui.models.loanapplication.documents;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Concrete {@link AddDocumentModel} for adding a photo ID.
 * @author Wijnand
 */
public class AddPhotoIdModel extends AbstractAddDocumentModel implements AddDocumentModel {

    /**
     * Creates a new {@link AddPhotoIdModel} instance.
     * @param action Loan application action.
     */
    public AddPhotoIdModel(LoanApplicationActionVo action) {
        super(action);
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_photo_id;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitleResourceId(R.string.add_documents_title_unchecked_photo_id,
                R.string.add_documents_title_checked_photo_id);
    }
}
