package com.shift.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;

import com.shift.link.sdk.ui.images.GenericImageLoader;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.loanapplication.BigButtonModel;

/**
 * Concrete {@link LoanApplicationDetailsModel} for a loan application in the {@code PENDING_BORROWER_ACTION} state with
 * the {@code FINISH_APPLICATION_EXTERNAL} action.
 * @author Wijnand
 */
public class FinishExternalLoanApplicationDetailsModel extends LoanApplicationDetailsModel {

    /**
     * Creates a new {@link FinishExternalLoanApplicationDetailsModel} instance.
     * @param application Loan application details.
     * @param resources Android application resources.
     * @param loader Image loader.
     */
    public FinishExternalLoanApplicationDetailsModel(LoanApplicationDetailsResponseVo application, Resources resources,
            GenericImageLoader loader) {

        super(application, resources, loader);
    }

    /** {@inheritDoc} */
    @Override
    public String getStatusText() {
        return mResources.getString(R.string.loan_application_details_status_pending_borrower_action_finish_external);
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
                R.string.loan_application_button_finish_external,
                BigButtonModel.Action.FINISH_EXTERNAL
        );
    }
}
