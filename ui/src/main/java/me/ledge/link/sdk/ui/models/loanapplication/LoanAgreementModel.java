package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Loan agreement {@link Model}.
 * @author Wijnand
 */
public class LoanAgreementModel implements ActivityModel, Model {

    private final OfferVo mOffer;

    public LoanAgreementModel(OfferVo offer) {
        mOffer = offer;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_agreement_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity(Activity current) {
        return IntermediateLoanApplicationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }

    /**
     * @return Interest rate text.
     */
    public String getInterestRate() {
        return "" + mOffer.interest_rate;
    }

    /**
     * @return Total loan amount text.
     */
    public String getTotalAmount() {
        return "" + mOffer.loan_amount;
    }

    /**
     * @return Loan term text.
     */
    public String getTerm() {
        return "" + mOffer.term.duration;
    }

    /**
     * @return Single payment amount text.
     */
    public String getPaymentAmount() {
        return "" + mOffer.payment_amount;
    }
}
