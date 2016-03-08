package me.ledge.link.sdk.ui.tests.robolectric.tests.models.offers;

import me.ledge.link.api.utils.CreditScoreRange;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata.LoanAmountModelTest;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.vos.LoanPurposeDisplayVo;
import me.ledge.link.sdk.ui.vos.UserDataVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Tests the {@link OffersListModel} class.
 * @author wijnand
 */
public class OffersListModelTest {

    private static final int EXPECTED_CREDIT_SCORE_RANGE = CreditScoreRange.GOOD;
    private static final String EXPECTED_CURRENCY = "USD";
    private static final int EXPECTED_PURPOSE_ID = 4;

    private OffersListModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new OffersListModel(null);
    }

    /**
     * @return Populated base data.
     */
    private UserDataVo getBaseData() {
        UserDataVo base = new UserDataVo();
        base.creditScoreRange = EXPECTED_CREDIT_SCORE_RANGE;
        base.loanAmount = LoanAmountModelTest.EXPECTED_VALID_AMOUNT;
        base.loanPurpose = new LoanPurposeDisplayVo();
        base.loanPurpose.loan_purpose_id = EXPECTED_PURPOSE_ID;

        return base;
    }

    /**
     * Given an empty Model.<br />
     * When fetching the title resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectTitleResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getActivityTitleResource(),
                equalTo(R.string.offers_list_label));
    }

    /**
     * Given an empty Model.<br />
     * When storing the base data.<br />
     * Then the data should actually be stored.
     */
    @Test
    public void baseDataIsStored() {
        UserDataVo base = getBaseData();
        mModel.setBaseData(base);
        Assert.assertThat("Incorrect base data.", mModel.getBaseData(), equalTo(base));
    }

    /**
     * Given a user having been created.<br />
     * When fetching the initial loan offers request data.<br />
     * Then the correct request data should be generated.
     */
    @Test
    public void initialOffersRequestIsGenerated() {
        UserDataVo base = getBaseData();
        mModel.setBaseData(base);

        InitialOffersRequestVo request = mModel.getInitialOffersRequest();

        Assert.assertThat("Request data should not be null.", request, not(nullValue()));
        Assert.assertThat("Incorrect currency.", request.currency, equalTo(EXPECTED_CURRENCY));
        Assert.assertThat("Incorrect amount.", request.loan_amount, equalTo(LoanAmountModelTest.EXPECTED_VALID_AMOUNT));
        Assert.assertThat("Incorrect loan purpose.", request.loan_purpose_id, equalTo(EXPECTED_PURPOSE_ID));
    }

    /**
     * Given the initial loan offers are being requested.<br />
     * When storing the API response. <br />
     * Then the whole list of the response should be stored.
     */
    @Test
    public void offersAreStored() {
        InitialOffersResponseVo offersResponse = new MockApiWrapper().getInitialOffers(null);
        mModel.addOffers(null, offersResponse.offers.data, !offersResponse.offers.has_more);
        mModel.setOfferRequestId(offersResponse.offer_request_id);

        Assert.assertThat("Offer list should not be null.", mModel.getOffers(), not(nullValue()));
        Assert.assertThat("Incorrect number of offers.",
                mModel.getOffers().getList().size(), equalTo(offersResponse.offers.data.length));
        Assert.assertTrue("Offer list should be complete.", mModel.getOffers().isComplete());
    }
}
