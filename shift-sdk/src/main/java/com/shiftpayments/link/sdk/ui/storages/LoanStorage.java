package com.shiftpayments.link.sdk.ui.storages;

import android.content.res.Resources;

import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.OfferVo;
import com.shiftpayments.link.sdk.ui.images.GenericImageLoader;
import com.shiftpayments.link.sdk.ui.models.offers.OfferSummaryModel;

import java.util.ArrayList;

import me.ledge.common.utils.PagedList;

/**
 * Stores loan offer and application related data.
 * @author Wijnand
 */
public class LoanStorage {

    private String mOfferRequestId;
    private PagedList<OfferSummaryModel> mOffers;

    private LoanApplicationDetailsResponseVo mCurrentLoanApplication;

    private static LoanStorage mInstance;
    private OfferVo mSelectedOffer;

    /**
     * Creates a new {@link LoanStorage} instance.
     */
    private LoanStorage() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mOfferRequestId = null;
        mOffers = new PagedList<>();
    }

    /**
     * @return The single instance of this class.
     */
    public static synchronized LoanStorage getInstance() {
        if (mInstance == null) {
            mInstance = new LoanStorage();
        }

        return mInstance;
    }

    /**
     * Stores a new loan offers request ID.<br />
     * Clears the offers list if the ID is different from the currently stored list.
     *
     * @param id Loan offers request ID.
     */
    public void setOfferRequestId(String id) {
        if (id!=null && !id.equals(mOfferRequestId)) {
            mOffers = new PagedList<>();
        }

        mOfferRequestId = id;
    }

    /**
     * Adds a list of loan offers.
     * @param offers List of offers.
     */
    public void addOffers(Resources resources, OfferVo[] offers, boolean complete, GenericImageLoader imageLoader) {
        ArrayList<OfferSummaryModel> newOffers = new ArrayList<>(offers.length);
        for (OfferVo offer : offers) {
            newOffers.add(new OfferSummaryModel(offer, resources, imageLoader));
        }

        mOffers.addAll(newOffers);
        mOffers.setComplete(complete);
    }

    /**
     * @return A {@link PagedList} of offers.
     */
    public PagedList<OfferSummaryModel> getOffers() {
        return mOffers;
    }

    /**
     * @return The current loan application.
     */
    public LoanApplicationDetailsResponseVo getCurrentLoanApplication() {
        return mCurrentLoanApplication;
    }

    /**
     * Stores a new current loan application.
     * @param application New loan application.
     */
    public void setCurrentLoanApplication(LoanApplicationDetailsResponseVo application) {
        this.mCurrentLoanApplication = application;
    }

    public void clearOffers() {
        init();
    }

    public void setSelectedOffer(OfferVo offer) {
        mSelectedOffer = offer;
    }

    public OfferVo getSelectedOffer() {
        return mSelectedOffer;
    }
}
