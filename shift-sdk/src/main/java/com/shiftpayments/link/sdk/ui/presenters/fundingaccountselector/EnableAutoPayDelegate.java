package com.shiftpayments.link.sdk.ui.presenters.fundingaccountselector;

/**
 * Delegation interface to enable auto-pay.
 *
 * @author Adrian
 */
public interface EnableAutoPayDelegate {

    void primaryButtonPressed();

    void secondaryButtonPressed();

    void autoPayOnBackPressed();

    String getFinancialAccountId();
}
