package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import me.ledge.link.sdk.api.vos.datapoints.Card;

/**
 * Delegation interface for adding a card.
 *
 * @author Adrian
 */
public interface AddCardDelegate {

    void cardAdded(Card card);

    void addCardOnBackPressed();
}
