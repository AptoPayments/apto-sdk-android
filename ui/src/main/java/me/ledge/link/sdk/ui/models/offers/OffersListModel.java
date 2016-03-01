package me.ledge.link.sdk.ui.models.offers;

import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.offers.OfferWrapperVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.vos.UserDataVo;

import java.util.Currency;
import java.util.Locale;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class OffersListModel extends AbstractActivityModel implements ActivityModel, Model {

    private UserDataVo mBaseData;
    private int mOfferRequestId;
    private OfferWrapperVo[] mOffers;

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.offers_list_label;
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
    public void addOffers(OfferWrapperVo[] offers) {
        mOffers = offers;
    }

    /**
     * Stores a new loan offers request ID.
     * @param id Loan offers request ID.
     */
    public void setOfferRequestId(int id) {
        mOfferRequestId = id;
    }
}
