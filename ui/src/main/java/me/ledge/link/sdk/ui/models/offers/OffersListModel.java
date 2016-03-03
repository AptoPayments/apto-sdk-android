package me.ledge.link.sdk.ui.models.offers;

import android.content.res.Resources;
import me.ledge.common.utils.PagedList;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.images.GenericImageLoader;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.UserDataVo;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class OffersListModel extends AbstractActivityModel implements ActivityModel, Model {

    private final GenericImageLoader mImageLoader;
    private UserDataVo mBaseData;
    private int mOfferRequestId;

    private OfferVo[] mRawOffers;
    private PagedList<OfferSummaryModel> mOffers;

    /**
     * Creates a new {@link OffersListModel} instance.
     */
    public OffersListModel(GenericImageLoader loader) {
        mImageLoader = loader;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mOffers = new PagedList<OfferSummaryModel>();
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.offers_list_label;
    }

    /**
     * @return Basic user data.
     */
    public UserDataVo getBaseData() {
        return mBaseData;
    }

    /**
     * Stores new basic user data.
     * @param base User data.
     */
    public void setBaseData(UserDataVo base) {
        this.mBaseData = base;
    }

    /**
     * @return Populated initial offers request data object.
     */
    public InitialOffersRequestVo getInitialOffersRequest() {
        InitialOffersRequestVo request = new InitialOffersRequestVo();
        request.credit_range = mBaseData.creditScoreRange;
        request.currency = Currency.getInstance(Locale.US).getCurrencyCode();
        request.loan_amount = mBaseData.loanAmount;
        request.loan_purpose_id = mBaseData.loanPurpose.loan_purpose_id;

        return request;
    }

    /**
     * Adds a list of loan offers.
     * @param offers List of offers.
     */
    public void addOffers(Resources resources, OfferVo[] offers, boolean complete) {
        mRawOffers = offers;

        ArrayList<OfferSummaryModel> newOffers = new ArrayList<OfferSummaryModel>(offers.length);
        for (OfferVo offer : offers) {
            newOffers.add(new OfferSummaryModel(offer, resources, mImageLoader));
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
     * Stores a new loan offers request ID.
     * @param id Loan offers request ID.
     */
    public void setOfferRequestId(int id) {
        mOfferRequestId = id;
    }
}
