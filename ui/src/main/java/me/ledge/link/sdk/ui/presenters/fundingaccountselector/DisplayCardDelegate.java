package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

/**
 * Delegation interface for displaying a card.
 *
 * @author Adrian
 */
public interface DisplayCardDelegate {

    void displayCardOnBackPressed();

    String getFinancialAccountId();

    void displayCardPrimaryButtonPressed();

    void displayCardSecondaryButtonPressed();
}
