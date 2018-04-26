package com.shiftpayments.link.sdk.ui.vos;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;

/**
 * Loan related data.<br />
 *
 * @author Adrian
 */
public class LoanDataVo {

    public int loanAmount;
    public IdDescriptionPairDisplayVo loanPurpose;

    /**
     * Creates a new {@link LoanDataVo} instance.
     */
    public LoanDataVo() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        loanAmount = -1;
        loanPurpose = null;
    }
}
