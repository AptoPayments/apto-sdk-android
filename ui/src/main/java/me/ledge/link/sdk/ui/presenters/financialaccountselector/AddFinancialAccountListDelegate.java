package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.api.vos.datapoints.VirtualCard;

/**
 * Delegation interface for adding financial accounts.
 *
 * @author Adrian
 */
public interface AddFinancialAccountListDelegate {
    void addFinancialAccountListOnBackPressed();

    void addCard();

    void addBankAccount();

    void virtualCardIssued(VirtualCard virtualCard);
}
