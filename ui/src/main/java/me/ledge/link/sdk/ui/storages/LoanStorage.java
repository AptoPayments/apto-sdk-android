package me.ledge.link.sdk.ui.storages;

import android.content.res.Resources;
import me.ledge.common.utils.PagedList;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;

import java.util.ArrayList;

/**
 * Stores loan offer and application related data.
 * @author Wijnand
 */
public class LoanStorage {

    private int mOfferRequestId;
    private PagedList<OfferSummaryModel> mOffers;

    private LoanApplicationDetailsResponseVo mCurrentLoanApplication;

    private static LoanStorage mInstance;

    /**
     * Creates a new {@link UserStorage} instance.
     */
    private LoanStorage() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mOfferRequestId = -1;
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
    public void setOfferRequestId(int id) {
        if (id != mOfferRequestId) {
            mOffers = new PagedList<>();
        }

        mOfferRequestId = id;
    }

    /**
     * Adds a list of loan offers.
     * @param offers List of offers.
     */
    public void addOffers(Resources resources, OfferVo[] offers, boolean complete, GenericImageLoader imageLoader) {
        ArrayList<OfferSummaryModel> newOffers = new ArrayList<OfferSummaryModel>(offers.length);
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
}
