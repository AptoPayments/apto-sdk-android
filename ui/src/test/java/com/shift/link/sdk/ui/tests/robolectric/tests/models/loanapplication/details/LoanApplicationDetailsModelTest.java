package com.shift.link.sdk.ui.tests.robolectric.tests.models.loanapplication.details;

import android.content.res.Resources;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowContentProvider;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.utils.TermUnit;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shift.link.sdk.ui.models.loanapplication.details.ApprovedLoanApplicationDetailsModel;
import com.shift.link.sdk.ui.models.loanapplication.details.LoanApplicationDetailsModel;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link LoanApplicationDetailsModel} class.
 * @author Wijnand
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoanApplicationDetailsModelTest {

    private Resources mResources;
    private LinkApiWrapper mApiWrapper;
    private LoanApplicationDetailsModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mResources = new ShadowContentProvider().getContext().getResources();
        mApiWrapper = new MockApiWrapper();
    }

    /**
     * Given a loan application with a three week term.<br />
     * When generating the loan duration text.<br />
     * Then the correct copy should be created.
     */
    @Test
    public void correctDurationTextWeek() {
        LoanApplicationDetailsResponseVo application = null;

        try {
            application = mApiWrapper.createLoanApplication(null);
            application.offer = mApiWrapper.getInitialOffers(null).offers.data[0];
        } catch (ApiException ae) {
            Assert.fail("API error.");
        }

        mModel = new ApprovedLoanApplicationDetailsModel(application, mResources, null);

        Assert.assertThat("Incorrect text.", mModel.getDurationText(), equalTo("Loan term: 3 weeks"));
    }

    /**
     * Given a loan application with a three month term.<br />
     * When generating the loan duration text.<br />
     * Then the correct copy should be created.
     */
    @Test
    public void correctDurationTextMonth() {
        LoanApplicationDetailsResponseVo application = null;

        try {
            application = mApiWrapper.createLoanApplication(null);
            application.offer = mApiWrapper.getInitialOffers(null).offers.data[0];
            application.offer.term.unit = TermUnit.MONTH;
        } catch (ApiException ae) {
            Assert.fail("API error.");
        }

        mModel = new ApprovedLoanApplicationDetailsModel(application, mResources, null);

        Assert.assertThat("Incorrect text.", mModel.getDurationText(), equalTo("Loan term: 3 months"));
    }
}
