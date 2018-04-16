package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.sdk.api.vos.Card;

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
