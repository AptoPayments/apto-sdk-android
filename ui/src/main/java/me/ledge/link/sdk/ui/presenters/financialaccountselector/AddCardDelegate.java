package me.ledge.link.sdk.ui.presenters.financialaccountselector;

/**
 * Delegation interface for adding a card.
 *
 * @author Adrian
 */
public interface AddCardDelegate {

    void cardAdded(me.ledge.link.api.vos.Card card);

    void addCardOnBackPressed();
}
