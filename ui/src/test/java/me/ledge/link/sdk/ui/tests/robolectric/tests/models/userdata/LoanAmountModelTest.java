package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.vos.UserDataVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import me.ledge.link.sdk.ui.models.userdata.LoanAmountModel;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests the {@link LoanAmountModel} class.
 * @author Wijnand
 */
public class LoanAmountModelTest {

    private static final int MIN_AMOUNT = 1;
    private static final int MAX_AMOUNT = 1000;

    private static final int TOO_SMALL_AMOUNT = -1000;
    private static final int TOO_LARGE_AMOUNT = MAX_AMOUNT * 2;
    public static final int EXPECTED_VALID_AMOUNT = 456;

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
     * When setting min AND max values.<br />
     * Then min and max should be properly stored.
     */
    @Test
    public void minMaxAreSet() {
        Assert.assertThat("Incorrect minimum amount.", mModel.getMinAmount(), equalTo(MIN_AMOUNT));
        Assert.assertThat("Incorrect maximum amount.", mModel.getMaxAmount(), equalTo(MAX_AMOUNT));
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
                equalTo(R.string.loan_amount_label));
    }

    /**
     * Given a fully populated Model.<br />
     * When checking if all data has been set.<br />
     * Then the result should be true.
     */
    @Test
    public void allDataSet() {
        IdDescriptionPairDisplayVo purpose = new IdDescriptionPairDisplayVo(123, "Company investment");

        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        mModel.setLoanPurpose(purpose);

        Assert.assertTrue("All data should be set.", mModel.hasAllData());
    }

    /**
     * Given an empty Model.<br />
     * When setting base data with a valid amount.<br />
     * Then the amount in the Model should be the same as in the base data.
     */
    @Test
    public void settingBaseDataUpdatesAmount() {
        UserDataVo base = new UserDataVo();
        base.loanAmount = EXPECTED_VALID_AMOUNT;

        mModel.setBaseData(base);

        Assert.assertFalse("There should be missing data.", mModel.hasAllData());
        Assert.assertThat("Incorrect amount.", mModel.getAmount(), equalTo(base.loanAmount));
    }

    /**
     * Given a Model with the amount set.<br />
     * When fetching the base data.<br />
     * Then the base data should contain the same amount as the Model.
     */
    @Test
    public void baseDataIsUpdated() {
        mModel.setBaseData(new UserDataVo());

        mModel.setAmount(EXPECTED_VALID_AMOUNT);

        Assert.assertThat("Incorrect amount.", mModel.getBaseData().loanAmount, equalTo(mModel.getAmount()));
    }

    /**
     * Given an empty Model.<br />
     * When trying to set a valid loan amount.<br />
     * Then the amount should be properly stored.
     */
    @Test
    public void validAmountIsStored() {
        mModel.setAmount(EXPECTED_VALID_AMOUNT);
        Assert.assertThat("Amount should be stored.", mModel.getAmount(), equalTo(EXPECTED_VALID_AMOUNT));
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
                mModel.getAmount(), equalTo(EXPECTED_VALID_AMOUNT));
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
                mModel.getAmount(), equalTo(EXPECTED_VALID_AMOUNT));
    }

}
