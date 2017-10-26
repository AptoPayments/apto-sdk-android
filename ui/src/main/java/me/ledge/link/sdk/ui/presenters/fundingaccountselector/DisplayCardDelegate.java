package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import me.ledge.link.api.vos.datapoints.Card;

/**
 * Delegation interface for displaying a card.
 *
 * @author Adrian
 */
public interface DisplayCardDelegate {

    void displayCardOnBackPressed();

    Card getCard();
}
