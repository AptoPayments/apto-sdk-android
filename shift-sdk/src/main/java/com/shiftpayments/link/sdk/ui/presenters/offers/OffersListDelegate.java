package com.shiftpayments.link.sdk.ui.presenters.offers;

import com.shiftpayments.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;

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
