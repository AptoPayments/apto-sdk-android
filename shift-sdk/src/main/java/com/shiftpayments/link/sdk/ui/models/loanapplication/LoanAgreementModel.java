package com.shiftpayments.link.sdk.ui.models.loanapplication;

import android.app.Activity;
import android.content.res.Resources;

import com.shiftpayments.link.sdk.api.utils.TermUnit;
import com.shiftpayments.link.sdk.api.vos.responses.offers.OfferVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shiftpayments.link.sdk.ui.images.GenericImageLoader;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.models.lenders.LenderModel;
import com.shiftpayments.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;

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
     * @param offer The loan offer.
     * @param imageLoader Image loader.
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

        if (mOffer != null && mOffer.lender != null) {
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
        return IntermediateLoanApplicationActivity.class;
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
    public String getInterestRate(Resources resources) {
        return resources.getString(R.string.loan_agreement_terms_interest_format, mOffer.interest_rate);
    }

    /**
     * @return Total loan amount text.
     */
    public String getTotalAmount(Resources resources) {
        return resources.getString(R.string.loan_agreement_terms_amount_format, mOffer.loan_amount);
    }

    /**
     * @return Loan term text.
     */
    public String getTerm(Resources resources) {
        if (mOffer == null || mOffer.term == null) {
            return "";
        }

        int resourceId;

        if (mOffer.term.unit == TermUnit.WEEK) {
            resourceId = R.plurals.loan_agreement_terms_duration_week;
        } else {
            resourceId = R.plurals.loan_agreement_terms_duration_month;
        }
        return resources.getQuantityString(resourceId, mOffer.term.duration, mOffer.term.duration);
    }

    /**
     * @return Single payment amount text.
     */
    public String getPaymentAmount(Resources resources) {
        return resources.getString(R.string.loan_agreement_terms_payment_format, mOffer.payment_amount);
    }
}
