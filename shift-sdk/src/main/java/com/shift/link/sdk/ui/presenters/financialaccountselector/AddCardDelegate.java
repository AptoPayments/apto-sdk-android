package com.shift.link.sdk.ui.presenters.financialaccountselector;

import com.shift.link.sdk.api.vos.Card;

/**
 * Delegation interface for adding a card.
 *
 * @author Adrian
 */
public interface AddCardDelegate {

    void cardAdded(Card card);

    void addCardOnBackPressed();
}
