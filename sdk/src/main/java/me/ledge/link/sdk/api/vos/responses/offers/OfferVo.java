package me.ledge.link.sdk.api.vos.responses.offers;

import com.google.gson.annotations.SerializedName;

/**
 * Offer details.
 * @author wijnand
 */
public class OfferVo {

    /**
     * Offer ID.
     */
    public String id;

    /**
     * Three letter <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     */
    public String currency;

    /**
     * Total loan amount.
     */
    public long loan_amount;

    /**
     * Single payment amount.
     */
    public float payment_amount;

    /**
     * Loan interest.
     */
    public float interest_rate;

    /**
     * Total number of payments.
     */
    public int payment_count;

    /**
     * Loan term.
     */
    public TermVo term;

    /**
     * Offer expiration date.<br />
     * Format: "MM-dd-yyyy".
     */
    public String expiration_date;

    /**
     * Loan application method.
     * @see me.ledge.link.api.utils.loanapplication.LoanApplicationMethod
     */
    public String application_method;

    /**
     * Loan application URL if no API integration is available.
     */
    public String application_url;

    /**
     * Lender details.
     */
    public LenderVo lender;

    public String offer_details;

    @SerializedName("show_application_summary")
    public boolean showApplicationSummary;

}
