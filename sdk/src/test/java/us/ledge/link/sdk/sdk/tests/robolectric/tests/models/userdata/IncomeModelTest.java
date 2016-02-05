package us.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.ledge.link.sdk.sdk.models.userdata.IncomeModel;

/**
 * Tests the {@link IncomeModel} class.
 * @author Wijnand
 */
public class IncomeModelTest {

    private static final int MIN_INCOME = 1000;
    private static final int MAX_INCOME = 50000;

    private static final int EXPECTED_VALID_INCOME = 30000;
    private static final int TOO_SMALL_INCOME = -1000;
    private static final int TOO_LARGE_INCOME = MAX_INCOME * 2;

    private IncomeModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new IncomeModel()
                .setMinIncome(MIN_INCOME)
                .setMaxIncome(MAX_INCOME);
    }

    /**
     * Given an empty Model.<br />
     * When trying to set a valid income.<br />
     * Then the income should be properly stored.
     */
    @Test
    public void validIncomeIsStored() {
        mModel.setIncome(EXPECTED_VALID_INCOME);
        Assert.assertThat("Income should be stored.", mModel.getIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }

    /**
     * Given a Model with a valid income set.<br />
     * When trying to store a too low income.<br />
     * Then the income should be ignored.
     */
    @Test
    public void tooLowIncomeIsIgnored() {
        mModel.setIncome(EXPECTED_VALID_INCOME);
        mModel.setIncome(TOO_SMALL_INCOME);

        Assert.assertThat("Too low income should be ignored.",
                mModel.getIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }

    /**
     * Given a Model with a valid income set.<br />
     * When trying to store a too large income.<br />
     * Then the income should be ignored.
     */
    @Test
    public void tooLargeIncomeIsIgnored() {
        mModel.setIncome(EXPECTED_VALID_INCOME);
        mModel.setIncome(TOO_LARGE_INCOME);

        Assert.assertThat("Too large income should be ignored.",
                mModel.getIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }
}
