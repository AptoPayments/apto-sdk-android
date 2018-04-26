package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.offers;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.offers.OffersListModel;
import com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.userdata.LoanAmountModelTest;
import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;
import com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.userdata.LoanAmountModelTest;

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

    private static final String EXPECTED_CURRENCY = "USD";
    private static final int EXPECTED_PURPOSE_ID = 4;

    private OffersListModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new OffersListModel();
    }

    /**
     * @return Populated base data.
     */
    private LoanDataVo getBaseData() {
        LoanDataVo base = new LoanDataVo();
        base.loanAmount = LoanAmountModelTest.EXPECTED_VALID_AMOUNT;
        base.loanPurpose = new IdDescriptionPairDisplayVo(EXPECTED_PURPOSE_ID, null);

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
        LoanDataVo base = getBaseData();
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
        LoanDataVo base = getBaseData();
        mModel.setBaseData(base);

        InitialOffersRequestVo request = mModel.getInitialOffersRequest();

        Assert.assertThat("Request data should not be null.", request, not(nullValue()));
        Assert.assertThat("Incorrect currency.", request.currency, equalTo(EXPECTED_CURRENCY));
        Assert.assertThat("Incorrect amount.", request.loan_amount, equalTo(LoanAmountModelTest.EXPECTED_VALID_AMOUNT));
        Assert.assertThat("Incorrect loan purpose.", request.loan_purpose_id, equalTo(EXPECTED_PURPOSE_ID));
    }
}
