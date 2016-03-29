package me.ledge.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.lenders.LenderModel;

/**
 * Loan agreement {@link Model}.
 * @author Wijnand
 */
public class LoanAgreementModel implements ActivityModel, Model {

    private final OfferVo mOffer;
    private final GenericImageLoader mImageLoader;

    private LenderModel mLender;

    /**
     * Creates a new {@link LoanAgreementModel} instance.
     * @param offer TODO
     */
    public LoanAgreementModel(OfferVo offer, GenericImageLoader imageLoader) {
        mOffer = offer;
        mImageLoader = imageLoader;

        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mLender = null;

        if (mOffer.lender != null) {
            mLender = new LenderModel(mOffer.lender);
        }
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
     * @return Whether an {@link GenericImageLoader} has been set.
     */
    public boolean hasImageLoader() {
        return getImageLoader() != null;
    }

    /**
     * @return The current {@link GenericImageLoader}.
     */
    public GenericImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * @return Lender name.
     */
    public String getLenderName() {
        if (mLender == null) {
            return "";
        }

        return mLender.getLenderName();
    }

    /**
     * @return Lender image URL OR null if not found.
     */
    public String getLenderImage() {
        if (mLender == null) {
            return null;
        }

        return mLender.getLenderImage();
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
