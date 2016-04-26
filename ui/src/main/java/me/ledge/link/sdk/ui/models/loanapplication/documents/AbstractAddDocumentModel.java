package me.ledge.link.sdk.ui.models.loanapplication.documents;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;

/**
 * Partial implementation of the {@link AddDocumentModel}.
 * @author Wijnand
 */
public abstract class AbstractAddDocumentModel implements AddDocumentModel {

    private final LoanApplicationActionVo mAction;

    /**
     * Creates a new {@link AbstractAddDocumentModel} instance.
     * @param action Loan application action.
     */
    public AbstractAddDocumentModel(LoanApplicationActionVo action) {
        mAction = action;
    }

    /**
     * @param unchecked Unchecked state resource ID.
     * @param checked Checked state resource ID.
     * @return Title resource ID based on whether a file has been attached.
     */
    int getTitleResourceId(int unchecked, int checked) {
        int id = unchecked;

        if (hasDocument()) {
            id = checked;
        }

        return id;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        String description = "";

        if (mAction != null) {
            description = mAction.message;
        }

        return description;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDocument() {
        return false;
    }
}
