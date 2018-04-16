package com.shift.link.sdk.ui.models.offers;

import android.content.res.Resources;
import android.text.TextUtils;

import com.shift.link.sdk.api.utils.loanapplication.LoanApplicationMethod;
import com.shift.link.sdk.api.vos.responses.offers.OfferVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.models.lenders.LenderModel;

/**
 * Concrete {@link Model} for the offer summary.
 * @author wijnand
 */
public class OfferSummaryModel implements Model {

    protected final OfferVo mRawOffer;
    protected final Resources mResources;
    private final GenericImageLoader mImageLoader;

    private LenderModel mLender;
    private String mInterestText;
    private String mAmountText;
    private String mMonthlyPaymentText;

    /**
     * Creates a new {@link OfferSummaryModel} instance.
     * @param offer Raw offer data.
     * @param resources {@link Resources} used to fetch Strings.
     */
    public OfferSummaryModel(OfferVo offer, Resources resources, GenericImageLoader loader) {
        mRawOffer = offer;
        mResources = resources;
        mImageLoader = loader;

        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mLender = null;
        if (mRawOffer != null) {
            mLender = new LenderModel(mRawOffer.lender);
        }

        mInterestText = "";
        mAmountText = "";
        mMonthlyPaymentText = "";

        createFormattedText();
    }

    /**
     * Creates the formatted texts.
     */
    protected void createFormattedText() {
        if (mResources == null || mRawOffer == null) {
            return;
        }

        mInterestText = mResources.getString(getInterestFormatId(), mRawOffer.interest_rate);
        mAmountText = mResources.getString(getAmountFormatId(), mRawOffer.loan_amount);
        mMonthlyPaymentText = mResources.getString(getPaymentFormatId(), mRawOffer.payment_amount);
    }

    /**
     * @return Resource ID of the interest rate text.
     */
    private int getInterestFormatId() {
        return R.string.offers_list_summary_interest_rate;
    }

    /**
     * @return Resource ID of the loan amount text.
     */
    private int getAmountFormatId() {
        return R.string.offers_list_summary_loan_amount;
    }

    /**
     * @return Resource ID of the monthly payment text.
     */
    private int getPaymentFormatId() {
        return R.string.offers_list_summary_monthly_payment;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof OfferSummaryModel && ((OfferSummaryModel) other).getOfferId().equals(getOfferId());
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
    public String getInterestText() {
        return mInterestText;
    }

    /**
     * @return Loan amount text.
     */
    public String getAmountText() {
        return mAmountText;
    }

    /**
     * @return Monthly payment text.
     */
    public String getMonthlyPaymentText() {
        return mMonthlyPaymentText;
    }

    /**
     * @return Whether the user needs to apply for this loan offer on a website.
     */
    public boolean requiresWebApplication() {
        return mRawOffer != null && LoanApplicationMethod.WEB.equals(mRawOffer.application_method);
    }

    /**
     * @return Offer ID OR null if not found.
     */
    public String getOfferId() {
        String id = null;

        if (mRawOffer != null) {
            id = mRawOffer.id;
        }

        return id;
    }

    /**
     * @return Loan offer application website URL.
     */
    public String getOfferApplicationUrl() {
        String url = "";

        if (mRawOffer != null && mRawOffer.application_url != null) {
            url = mRawOffer.application_url;
        }

        return url;
    }

    public boolean hasDisclaimer() {
        return mRawOffer != null && !TextUtils.isEmpty(mRawOffer.offer_details);
    }

    public String getDisclaimer() {
        String disclaimer = "";

        if (hasDisclaimer()) {
            disclaimer = mRawOffer.offer_details;
        }

        return disclaimer;
    }

    public boolean requiresConfirmation() {
        return mRawOffer.showApplicationSummary;
    }

    public OfferVo getOffer() {
        return mRawOffer;
    }

    public boolean isTextOnlyOffer() {
        return mRawOffer.loan_amount == 0 && mRawOffer.interest_rate == 0 &&
                (mRawOffer.term == null || mRawOffer.term.duration == 0);
    }
}
