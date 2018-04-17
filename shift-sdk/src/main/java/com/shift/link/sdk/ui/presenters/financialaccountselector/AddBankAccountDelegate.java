package com.shift.link.sdk.ui.presenters.financialaccountselector;

/**
 * Delegation interface for adding a card.
 *
 * @author Adrian
 */
public interface AddBankAccountDelegate {

    void addBankAccountOnBackPressed();

    void bankAccountLinked(String token);
}
