package com.shift.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;

import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.loanapplication.BigButtonModel;

/**
 * Concrete {@link LoanApplicationDetailsModel} for a loan application in the {@code PENDING_LENDER_ACTION} state
 * OR the {@code APPLICATION_RECEIVED} state.
 * @author Wijnand
 */
public class PendingLenderActionLoanApplicationDetailsModel extends LoanApplicationDetailsModel {

    /**
     * Creates a new {@link PendingLenderActionLoanApplicationDetailsModel} instance.
     * @param application Loan application details.
     * @param resources Android application resources.
     * @param loader Image loader.
     */
    public PendingLenderActionLoanApplicationDetailsModel(LoanApplicationDetailsResponseVo application,
            Resources resources, GenericImageLoader loader) {

        super(application, resources, loader);
    }

    /** {@inheritDoc} */
    @Override
    public String getStatusText() {
        return mResources.getString(R.string.loan_application_details_status_pending_lender_action);
    }

    /** {@inheritDoc} */
    @Override
    public int getStatusColor() {
        return mResources.getColor(R.color.llsdk_application_pending_lender_action_color);
    }

    /** {@inheritDoc} */
    @Override
    public BigButtonModel getBigButtonModel() {
        return new BigButtonModel(false, -1, -1);
    }
}
