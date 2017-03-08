package me.ledge.link.api.vos.requests.offers;

import me.ledge.link.api.utils.offers.LoanCategory;
import me.ledge.link.api.vos.requests.base.ListRequestVo;

/**
 * Request data for the first loan offers API call.
 * @author wijnand
 */
public class InitialOffersRequestVo extends ListRequestVo {

    /**
     * Loan amount.
     */
    public int loan_amount;

    /**
     * Loan purpose ID, based on the "config/loanPurposes" API response.
     */
    public int loan_purpose_id;

    /**
     * Three letter <a href="https://en.wikipedia.org/wiki/ISO_4217#Active_codes">ISO 4217</a> currency code.
     */
    public String currency;

    /**
     * Loan type.
     * @see LoanCategory
     */
    public int loan_category_id = LoanCategory.CONSUMER;

    /**
     * Desired loan length in months.
     */
    public int desired_months = 12;

}
