package me.ledge.link.sdk.ui.models.loanapplication.details;

import android.content.res.Resources;
import me.ledge.link.api.utils.TermUnit;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;

/**
 * Concrete {@link Model} for loan application details.
 * @author Wijnand
 */
public class LoanApplicationDetailsModel extends OfferSummaryModel implements Model {

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
        super.init();
        mLoanDurationText = "";
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
    public String getStatusText() {
        return "Status";
    }

    /**
     * @return Application status color.
     */
    public int getStatusColor() {
        return -1;
    }

    /**
     * @return Loan duration text.
     */
    public String getDurationText() {
        return mLoanDurationText;
    }
}
