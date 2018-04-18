package com.shift.link.sdk.api.vos.requests.offers;

import com.shift.link.sdk.api.utils.offers.LoanCategory;
import com.shift.link.sdk.api.vos.requests.base.ListRequestVo;

/**
 * Request data for the first loan offers API call.
 * @author wijnand
 */
public class InitialOffersRequestVo extends ListRequestVo {

    /**
     * Loan amount.
     */
    public Integer loan_amount;

    /**
     * Loan purpose ID, based on the "config/loanPurposes" API response.
     */
    public Integer loan_purpose_id;

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
