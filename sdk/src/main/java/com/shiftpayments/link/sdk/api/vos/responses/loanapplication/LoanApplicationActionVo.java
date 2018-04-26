package com.shiftpayments.link.sdk.api.vos.responses.loanapplication;

import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationActionId;

/**
 * Pending action for the loan application.
 * @author Wijnand
 */
public class LoanApplicationActionVo {

    /**
     * Action ID.
     * @see LoanApplicationActionId
     */
    public String action;

    /**
     * Detail message.
     */
    public String message;

}
