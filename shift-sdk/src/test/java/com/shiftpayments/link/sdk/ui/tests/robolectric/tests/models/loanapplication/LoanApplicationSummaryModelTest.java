package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.loanapplication;

import android.content.res.Resources;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.shadows.ShadowContentProvider;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link LoanApplicationSummaryModel} class.
 * @author Adrian
 */
@RunWith(RobolectricGradleTestRunner.class)
public class LoanApplicationSummaryModelTest {

    private LoanApplicationSummaryModel mModel;
    private Resources mResources;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new LoanApplicationSummaryModel(null, null);
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
                equalTo(R.string.loan_confirmation_label));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the address label.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctAddressLabel() {
        Assert.assertThat("Incorrect address label.", mModel.getAddressLabel(mResources), equalTo("Address"));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the annual income label.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctAnnualIncomeLabel() {
        Assert.assertThat("Incorrect annual income label.", mModel.getAnnualIncomeLabel(mResources), equalTo("Annual Pretax Income"));
    }

    /**
     * Given a populated Model.<br />
     * When fetching the monthly income label.<br />
     * Then the correct text should be returned.
     */
    @Test
    public void correctMonthlyIncomeLabel() {
        Assert.assertThat("Incorrect monthly income label.", mModel.getMonthlyIncomeLabel(mResources), equalTo("Monthly Net Income"));
    }
}
