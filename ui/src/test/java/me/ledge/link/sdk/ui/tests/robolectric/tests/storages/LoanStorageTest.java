package me.ledge.link.sdk.ui.tests.robolectric.tests.storages;

import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link LoanStorage} class.
 * @author Wijnand
 */
public class LoanStorageTest {

    private LoanStorage mStorage;

    /**
     * Sets up each test before it is run.
     */
    @Before
    public void setUp() {
        mStorage = LoanStorage.getInstance();
    }

    /**
     * Tears down each test after it has run.
     */
    @After
    public void tearDown() {
        mStorage.setCurrentLoanApplication(null);
        mStorage.setOfferRequestId(-1);
    }

    /**
     * Given the initial loan offers are being requested.<br />
     * When storing the API response. <br />
     * Then the whole list of the response should be stored.
     */
    @Test
    public void offersAreStored() {
        InitialOffersResponseVo offersResponse = new MockApiWrapper().getInitialOffers(null);
        mStorage.setOfferRequestId(offersResponse.offer_request_id);
        mStorage.addOffers(null, offersResponse.offers.data, !offersResponse.offers.has_more, null);

        Assert.assertThat("Offer list should not be null.", mStorage.getOffers(), not(nullValue()));
        Assert.assertThat("Incorrect number of offers.",
                mStorage.getOffers().getList().size(), equalTo(offersResponse.offers.data.length));
        Assert.assertTrue("Offer list should be complete.", mStorage.getOffers().isComplete());
    }
}
