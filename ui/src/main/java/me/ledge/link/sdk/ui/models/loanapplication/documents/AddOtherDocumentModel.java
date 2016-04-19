package me.ledge.link.sdk.ui.models.loanapplication.documents;

import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationActionVo;
import me.ledge.link.sdk.ui.R;

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
        return R.drawable.icon_cloud_dollar;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return R.string.add_documents_title_unchecked_other;
    }
}
