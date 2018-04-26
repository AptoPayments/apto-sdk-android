package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.loanapplication;

import android.content.res.Resources;

import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shiftpayments.link.sdk.ui.models.loanapplication.LoanAgreementModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowContentProvider;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link LoanAgreementModel} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoanAgreementModelTest {

    private LoanAgreementModel mModel;
    private Resources mResources;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new LoanAgreementModel(new MockApiWrapper().getInitialOffers(null).offers.data[0], null);
        mResources = new ShadowContentProvider().getContext().getResources();
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
                equalTo(R.string.loan_agreement_label));
    }

    /**
     * Given a populated Model.<br />
     * When determining the previous Activity.<br />
     * Then the correct class should be referenced.
     */
    @Test
    public void hasCorrectPreviousActivity() {
        Assert.assertThat("Incorrect previous Activity.",
                (Class<IntermediateLoanApplicationActivity>) mModel.getPreviousActivity(null),
                equalTo(IntermediateLoanApplicationActivity.class));
    }

    /**
     * Given a populated Model.<br />
     * When determining the next Activity.<br />
     * Then the correct class should be referenced.
     */
    @Test
    public void hasCorrectNextActivity() {
        Assert.assertThat("Incorrect next Activity.",
                (Class<IntermediateLoanApplicationActivity>) mModel.getNextActivity(null),
                equalTo(IntermediateLoanApplicationActivity.class));
    }

    /**
     * Given a populated Model.<br />
     * When no image loader has been passed in.<br />
     * Then the Model should be flagged as not having an image loader.
     */
    @Test
    public void noImageLoader() {
        Assert.assertFalse("No image loader should be available.", mModel.hasImageLoader());
    }

    /**
     * Given a populated Model.<br />
     * When fetching the lender name.<br />
     * Then the correct name should be returned.
     */
    @Test
    public void lenderNameIsSet() {
        Assert.assertThat("Incorrect lender name.", mModel.getLenderName(), equalTo(MockApiWrapper.LENDER_ONE_NAME));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the lender image.<br />
     * Then the correct image should be returned.
     */
    @Test
    public void hasLenderImage() {
        Assert.assertThat(
                "Incorrect lender image.",
                mModel.getLenderImage(),
                equalTo(MockApiWrapper.LENDER_ONE_LARGE_IMAGE)
        );
    }

    /**
     * Given a populated Model.<br />
     * When fetching the interest rate text.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctInterestText() {
        Assert.assertThat("Incorrect interest text.", mModel.getInterestRate(mResources), equalTo("19.90%"));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the total amount text.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctTotalAmountText() {
        Assert.assertThat("Incorrect total amount text.", mModel.getTotalAmount(mResources), equalTo("$5555"));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the term text.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctTermText() {
        Assert.assertThat("Incorrect term text.", mModel.getTerm(mResources), equalTo("3 weeks"));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the payment text.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctPaymentText() {
        Assert.assertThat("Incorrect payment text.", mModel.getPaymentAmount(mResources), equalTo("$123.45"));
    }
}
