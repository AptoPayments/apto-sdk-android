package us.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import android.support.test.runner.AndroidJUnit4;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import us.ledge.link.sdk.sdk.models.userdata.LoanAmountModel;

/**
 * Tests the {@link } class.
 * @author Wijnand
 */
@RunWith(AndroidJUnit4.class)
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

    @Test
    public void validAmountIsStored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        Assert.assertThat("Amount should be stored.", mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

    @Test
    public void tooSmallAmountIsIgnored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        mModel.setAmount(TOO_SMALL_AMOUNT);

        Assert.assertThat("Too small amount should be ignored.",
                mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

    @Test
    public void tooLargeAmountIsIgnored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        mModel.setAmount(TOO_LARGE_AMOUNT);

        Assert.assertThat("Too large amount should be ignored.",
                mModel.getAmount(), IsEqual.equalTo(EXPECTED_VALID_AMOUNT));
    }

}
