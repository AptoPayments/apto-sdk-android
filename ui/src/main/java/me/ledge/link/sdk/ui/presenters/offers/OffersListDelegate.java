package me.ledge.link.sdk.ui.presenters.offers;

import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.vos.ApplicationVo;

/**
 * Delegation interface for offers list.
 *
 * @author Adrian
 */
public interface OffersListDelegate {

    void onUpdateUserProfile();
    void onOffersListBackPressed();
    void onApplicationReceived(ApplicationVo application);
    void onConfirmationRequired(OfferSummaryModel offer);
    void onUpdateLoan();
}
