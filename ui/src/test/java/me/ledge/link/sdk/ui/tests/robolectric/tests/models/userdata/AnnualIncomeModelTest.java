package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.AnnualIncomeModel;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests the {@link AnnualIncomeModel} class.
 * @author Wijnand
 */
public class AnnualIncomeModelTest {

    private static final int MIN_INCOME = 1000;
    private static final int MAX_INCOME = 50000;

    private static final long EXPECTED_VALID_INCOME = 30000;
    private static final int TOO_SMALL_INCOME = -1000;
    private static final int TOO_LARGE_INCOME = MAX_INCOME * 2;

    private AnnualIncomeModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new AnnualIncomeModel()
                .setMinIncome(MIN_INCOME)
                .setMaxIncome(MAX_INCOME);
    }

    /**
     * Given an empty Model.<br />
     * When setting min AND max values.<br />
     * Then min and max should be properly stored.
     */
    @Test
    public void minMaxAreSet() {
        Assert.assertThat("Incorrect minimum income.", mModel.getMinIncome(), equalTo(MIN_INCOME));
        Assert.assertThat("Incorrect maximum income.", mModel.getMaxIncome(), equalTo(MAX_INCOME));
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
                equalTo(R.string.annual_income_label));
    }

    /**
     * Given an empty Model.<br />
     * When checking if all data has been set.<br />
     * Then the result should be false.
     */
    @Test
    public void noDataSet() {
        Assert.assertFalse("There should be missing data.", mModel.hasAllData());
    }

    /**
     * Given an empty Model.<br />
     * When setting base data with a valid income.<br />
     * Then the income in the Model should be the same as in the base data.
     */
    @Test
    public void settingBaseDataUpdatesIncome() {
        DataPointList base = new DataPointList();
        Income baseIncome = new Income(0, EXPECTED_VALID_INCOME, false);
        base.add(baseIncome);

        mModel.setBaseData(base);

        Assert.assertFalse("Data should still be incomplete.", mModel.hasAllData());
        Assert.assertThat("Incorrect income.", mModel.getAnnualIncome(), equalTo(baseIncome.annualGrossIncome));
    }

    /**
     * Given a Model with the income set.<br />
     * When fetching the base data.<br />
     * Then the base data should contain the same income as the Model.
     */
    @Test
    public void baseDataIsUpdated() {
        mModel.setBaseData(new DataPointList());

        mModel.setAnnualIncome(EXPECTED_VALID_INCOME);

        DataPointList base = mModel.getBaseData();
        Income baseIncome = (Income) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Income, new Income());
        Assert.assertThat("Incorrect income.", baseIncome.annualGrossIncome, equalTo(mModel.getAnnualIncome()));
    }

    /**
     * Given an empty Model.<br />
     * When trying to set a valid income.<br />
     * Then the income should be properly stored.
     */
    @Test
    public void validIncomeIsStored() {
        mModel.setAnnualIncome(EXPECTED_VALID_INCOME);
        Assert.assertThat("Income should be stored.", mModel.getAnnualIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }

    /**
     * Given a Model with a valid income set.<br />
     * When trying to store a too low income.<br />
     * Then the income should be ignored.
     */
    @Test
    public void tooLowIncomeIsIgnored() {
        mModel.setAnnualIncome(EXPECTED_VALID_INCOME);
        mModel.setAnnualIncome(TOO_SMALL_INCOME);

        Assert.assertThat("Too low income should be ignored.",
                mModel.getAnnualIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }

    /**
     * Given a Model with a valid income set.<br />
     * When trying to store a too large income.<br />
     * Then the income should be ignored.
     */
    @Test
    public void tooLargeIncomeIsIgnored() {
        mModel.setAnnualIncome(EXPECTED_VALID_INCOME);
        mModel.setAnnualIncome(TOO_LARGE_INCOME);

        Assert.assertThat("Too large income should be ignored.",
                mModel.getAnnualIncome(), IsEqual.equalTo(EXPECTED_VALID_INCOME));
    }
}
