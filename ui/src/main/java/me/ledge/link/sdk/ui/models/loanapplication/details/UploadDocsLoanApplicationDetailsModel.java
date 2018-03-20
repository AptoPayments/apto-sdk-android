package me.ledge.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;

/**
 * Concrete {@link LoanApplicationDetailsModel} for a loan application in the {@code PENDING_BORROWER_ACTION} state with
 * the {@code UPLOAD_PHOTO_ID} action.
 * @author Wijnand
 */
public class UploadDocsLoanApplicationDetailsModel extends LoanApplicationDetailsModel {

    /**
     * Creates a new {@link UploadDocsLoanApplicationDetailsModel} instance.
     * @param application Loan application details.
     * @param resources Android application resources.
     * @param loader Image loader.
     */
    public UploadDocsLoanApplicationDetailsModel(LoanApplicationDetailsResponseVo application, Resources resources,
            GenericImageLoader loader) {

        super(application, resources, loader);
    }

    /** {@inheritDoc} */
    @Override
    public String getStatusText() {
        return mResources.getString(R.string.loan_application_details_status_pending_borrower_action_upload);
    }

    /** {@inheritDoc} */
    @Override
    public int getStatusColor() {
        return mResources.getColor(R.color.llsdk_application_pending_borrower_action_color);
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
}
