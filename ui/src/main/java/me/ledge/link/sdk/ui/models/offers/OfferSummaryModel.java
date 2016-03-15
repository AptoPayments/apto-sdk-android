package me.ledge.link.sdk.ui.models.offers;

import android.content.res.Resources;
import android.text.TextUtils;
import me.ledge.link.api.utils.LoanApplicationMethod;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the offer summary.
 * @author wijnand
 */
public class OfferSummaryModel implements Model {

    private final OfferVo mRawOffer;
    private final Resources mResources;
    private final GenericImageLoader mImageLoader;

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
    private void init() {
        mInterestText = "";
        mAmountText = "";
        mMonthlyPaymentText = "";

        createFormattedText();
    }

    /**
     * Creates the formatted texts.
     */
    private void createFormattedText() {
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
        return other instanceof OfferSummaryModel && ((OfferSummaryModel) other).getOfferId() == getOfferId();
    }

    /**
     * @return Whether an {@link GenericImageLoader} has been set.
     */
    public boolean hasImageLoader() {
        return mImageLoader != null;
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
        String name = "";

        if (mRawOffer != null && mRawOffer.lender != null) {
            name = mRawOffer.lender.lender_name;
        }

        return name;
    }

    /**
     * @return Small lender image URL. If not found, will fall back to the large image URL.
     */
    public String getLenderImage() {
        String imageUrl = null;

        if (mRawOffer != null && mRawOffer.lender != null) {
            if (!TextUtils.isEmpty(mRawOffer.lender.small_image)) {
                imageUrl = mRawOffer.lender.small_image;
            } else if (!TextUtils.isEmpty(mRawOffer.lender.large_image)) {
                imageUrl = mRawOffer.lender.large_image;
            }
        }

        return imageUrl;
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
     * @return Offer ID OR -1 if not found.
     */
    public long getOfferId() {
        long id = -1;

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

        if (mRawOffer != null) {
            url = mRawOffer.application_url;
        }

        return url;
    }
}
