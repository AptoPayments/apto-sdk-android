package com.shift.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;

import com.shift.link.sdk.ui.images.GenericImageLoader;

import com.shift.link.sdk.api.utils.TermUnit;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.models.loanapplication.BigButtonModel;
import com.shift.link.sdk.ui.models.offers.OfferSummaryModel;

/**
 * Abstract {@link Model} for loan application details.
 * @author Wijnand
 */
public abstract class LoanApplicationDetailsModel extends OfferSummaryModel implements Model {

    private String mLoanDurationText;

    /**
     * Creates a new {@link LoanApplicationDetailsModel} instance.
     * @param application Loan application details.
     * @param resources Android application resources.
     * @param loader Image loader.
     */
    public LoanApplicationDetailsModel(LoanApplicationDetailsResponseVo application, Resources resources,
            GenericImageLoader loader) {

        super(application == null ? null : application.offer, resources, loader);
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        mLoanDurationText = "";
        super.init();
    }

    /** {@inheritDoc} */
    @Override
    protected void createFormattedText() {
        super.createFormattedText();

        if (mResources != null && mRawOffer != null) {
            String duration = "";

            if (mRawOffer.term != null) {
                if (mRawOffer.term.unit == TermUnit.WEEK) {
                    duration = mResources.getQuantityString(R.plurals.loan_agreement_terms_duration_week,
                            mRawOffer.term.duration, mRawOffer.term.duration);
                } else {
                    duration = mResources.getQuantityString(R.plurals.loan_agreement_terms_duration_month,
                            mRawOffer.term.duration, mRawOffer.term.duration);
                }
            }

            mLoanDurationText = mResources.getString(R.string.loan_application_details_duration, duration);
        }
    }

    /**
     * @return Application status text.
     */
    public abstract String getStatusText();

    /**
     * @return Application status color.
     */
    public abstract int getStatusColor();

    /**
     * @return Loan duration text.
     */
    public String getDurationText() {
        return mLoanDurationText;
    }

    /**
     * @return Big button configuration.
     */
    public abstract BigButtonModel getBigButtonModel();
}
