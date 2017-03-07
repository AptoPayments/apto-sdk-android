package me.ledge.link.sdk.ui.presenters.offers;

import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;

/**
 * Delegation interface for offers list.
 *
 * @author Adrian
 */
public interface OffersListDelegate {

    void onUpdateUserProfile();
    void onOffersListBackPressed();
    void onApplicationReceived();
    void onConfirmationRequired(OfferSummaryModel offer);
}
