package com.shift.link.sdk.ui.models.loanapplication.documents;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationActionVo;
import com.shift.link.sdk.ui.R;

/**
 * Concrete {@link AddDocumentModel} for adding proof of address.
 * @author Wijnand
 */
public class AddProofOfAddressModel extends AbstractAddDocumentModel implements AddDocumentModel {

    /**
     * Creates a new {@link AddProofOfAddressModel} instance.
     * @param action Loan application action.
     */
    public AddProofOfAddressModel(LoanApplicationActionVo action) {
        super(action);
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_address;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return getTitleResourceId(R.string.add_documents_title_unchecked_proof_of_address,
                R.string.add_documents_title_checked_proof_of_address);
    }
}
