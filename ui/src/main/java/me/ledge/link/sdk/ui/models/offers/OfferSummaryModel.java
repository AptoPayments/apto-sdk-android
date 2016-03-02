package me.ledge.link.sdk.ui.models.offers;

import android.content.res.Resources;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the offer summary.
 * @author wijnand
 */
public class OfferSummaryModel implements Model {

    private OfferVo mRawOffer;
    private Resources mResources;

    private String mInterestText;
    private String mAmountText;
    private String mMonthlyPaymentText;

    /**
     * Creates a new {@link OfferSummaryModel} instance.
     * @param offer Raw offer data.
     * @param resources {@link Resources} used to fetch Strings.
     */
    public OfferSummaryModel(OfferVo offer, Resources resources) {
        mRawOffer = offer;
        mResources = resources;

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
}
