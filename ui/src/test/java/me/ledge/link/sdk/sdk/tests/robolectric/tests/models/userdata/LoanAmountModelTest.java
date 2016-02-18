package me.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import me.ledge.link.sdk.ui.models.userdata.LoanAmountModel;

/**
 * Tests the {@link LoanAmountModel} class.
 * @author Wijnand
 */
public class LoanAmountModelTest {

    private static final int MIN_AMOUNT = 1;
    private static final int MAX_AMOUNT = 1000;

    private static final int EXPECTED_VALID_AMOUNT = 456;
    private static final int TOO_SMALL_AMOUNT = -1000;
    private static final int TOO_LARGE_AMOUNT = MAX_AMOUNT * 2;

    private LoanAmountModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new LoanAmountModel()
                .setMinAmount(MIN_AMOUNT)
                .setMaxAmount(MAX_AMOUNT);
    }

    /**
     * Given an empty Model.<br />
     * When trying to set a valid loan amount.<br />
     * Then the amount should be properly stored.
     */
    @Test
    public void validAmountIsStored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        Assert.assertThat("Amount should be stored.", mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

    /**
     * Given a Model with a valid amount set.<br />
     * When trying to store a too small amount.<br />
     * Then the amount should be ignored.
     */
    @Test
    public void tooSmallAmountIsIgnored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        mModel.setAmount(TOO_SMALL_AMOUNT);

        Assert.assertThat("Too small amount should be ignored.",
                mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

    /**
     * Given a Model with a valid amount set.<br />
     * When trying to store a too large amount.<br />
     * Then the amount should be ignored.
     */
    @Test
    public void tooLargeAmountIsIgnored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        mModel.setAmount(TOO_LARGE_AMOUNT);

        Assert.assertThat("Too large amount should be ignored.",
                mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

}
