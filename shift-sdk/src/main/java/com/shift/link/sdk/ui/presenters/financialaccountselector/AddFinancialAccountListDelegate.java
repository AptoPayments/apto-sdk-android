package com.shift.link.sdk.ui.presenters.financialaccountselector;

import com.shift.link.sdk.api.vos.Card;

/**
 * Delegation interface for adding financial accounts.
 *
 * @author Adrian
 */
public interface AddFinancialAccountListDelegate {
    void addFinancialAccountListOnBackPressed();

    void addCard();

    void addBankAccount();

    void virtualCardIssued(Card virtualCard);
}
