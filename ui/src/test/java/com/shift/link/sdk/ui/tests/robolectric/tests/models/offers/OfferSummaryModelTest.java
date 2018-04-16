package com.shift.link.sdk.ui.tests.robolectric.tests.models.offers;

import android.content.res.Resources;

import com.shift.link.sdk.api.vos.responses.offers.OfferVo;
import com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shift.link.sdk.ui.models.offers.OfferSummaryModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowContentProvider;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link OfferSummaryModel} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class OfferSummaryModelTest {

    private static final String EXPECTED_AMOUNT_TEXT = "Amount financed: $5555";
    private static final String EXPECTED_INTEREST_TEXT = "Interest rate: 19.90%";
    private static final String EXPECTED_PAYMENT_TEXT = "Monthly payment: $123.45";

    private OfferSummaryModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new OfferSummaryModel(getOffer(0), getResources(), null);
    }

    /**
     * @param index Index of the offer in the list.
     * @return An offer to use with testing.
     */
    private OfferVo getOffer(int index) {
        return new MockApiWrapper().getInitialOffers(null).offers.data[index];
    }

    /**
     * @return Android resources.
     */
    private Resources getResources() {
        return new ShadowContentProvider().getContext().getResources();
    }

    /**
     * Given an empty Model.<br />
     * When no image loader has been passed.<br />
     * Then no image loader should be available.
     */
    @Test
    public void noImageLoaderSet() {
        Assert.assertFalse("Image loader should not be available.", mModel.hasImageLoader());
    }

    /**
     * Given a populated Model.<br />
     * When no lender name has been set.<br />
     * Then the lender name should be an empty String.
     */
    @Test
    public void noLenderNameIsSet() {
        mModel = new OfferSummaryModel(getOffer(1), getResources(), null);
        Assert.assertThat("No lender name should be set.", mModel.getLenderName(), equalTo(""));
    }

    /**
     * Given a populated Model.<br />
     * When the lender name has been set.<br />
     * Then the lender name should be match.
     */
    @Test
    public void lenderNameIsSet() {
        Assert.assertThat("Incorrect lender name.", mModel.getLenderName(), equalTo(getOffer(0).lender.lender_name));
    }

    /**
     * Given a populated Model.<br />
     * When no lender image has been set.<br />
     * Then no lender image should be available.
     */
    @Test
    public void noLenderImageIsSet() {
        mModel = new OfferSummaryModel(getOffer(1), getResources(), null);
        Assert.assertThat("No lender image should be set.", mModel.getLenderImage(), is(nullValue()));
    }

    /**
     * Given a populated Model.<br />
     * When either a large or small lender image has been set.<br />
     * Then the lender image should be available.
     */
    @Test
    public void lenderImageIsSet() {
        Assert.assertThat("Image should be available.", mModel.getLenderImage(), not(nullValue()));
        Assert.assertThat("Incorrect image URL.", mModel.getLenderImage(), equalTo(getOffer(0).lender.large_image));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the interest rate text for display.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctInterestText() {
        Assert.assertThat("Incorrect interest text.", mModel.getInterestText(), equalTo(EXPECTED_INTEREST_TEXT));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the loan amount text for display.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctAmountText() {
        Assert.assertThat("Incorrect amount text.", mModel.getAmountText(), equalTo(EXPECTED_AMOUNT_TEXT));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the monthly payment text for display.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctPaymentText() {
        Assert.assertThat("Incorrect monthly payment text.",
                mModel.getMonthlyPaymentText(), equalTo(EXPECTED_PAYMENT_TEXT));
    }

    /**
     * Given a populated Model.<br />
     * When the loan offer can be applied to via the API.<br />
     * Then no loan application URL should be set.
     */
    @Test
    public void apiLoanApplication() {
        mModel = new OfferSummaryModel(getOffer(1), getResources(), null);

        Assert.assertFalse("Loan application should work via our API.", mModel.requiresWebApplication());
        Assert.assertThat("Loan application website URL should not be set.",
                mModel.getOfferApplicationUrl(), equalTo(""));
    }

    /**
     * Given a populated Model.<br />
     * When the loan offer needs to be applied to via a website.<br />
     * Then the loan application URL should be set.
     */
    @Test
    public void webLoanApplication() {
        Assert.assertTrue("Loan application should be done via external website.", mModel.requiresWebApplication());
        Assert.assertThat("Incorrect loan application website URL.",
                mModel.getOfferApplicationUrl(), equalTo(getOffer(0).application_url));
    }

}
