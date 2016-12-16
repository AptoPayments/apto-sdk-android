package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.api.utils.CreditScoreRange;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.CreditScoreModel;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link CreditScoreModel} class.
 * @author wijnand
 */
public class CreditScoreModelTest {

    private CreditScoreModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new CreditScoreModel();
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
                equalTo(R.string.credit_score_label));
    }

    /**
     * Given an empty Model.<br />
     * When setting base data with valid credit score.<br />
     * Then the Model should contain the same value as the base data.
     */
    @Test
    public void allDataIsSetFromBaseData() {
        DataPointVo.DataPointList baseData = new DataPointVo.DataPointList();
        DataPointVo.CreditScore baseCredit = new DataPointVo.CreditScore(CreditScoreRange.EXCELLENT, false);
        baseData.add(baseCredit);

        mModel.setBaseData(baseData);

        Assert.assertThat("Incorrect credit score range.",
                mModel.getCreditScoreRange(), equalTo(baseCredit.creditScoreRange));
        Assert.assertTrue("All data should be set.", mModel.hasAllData());
    }

    /**
     * Given a Model with all data set.<br />
     * When fetching the base data.<br />
     * Then the base data should contain the same values as the Model.
     */
    @Test
    public void baseDataIsUpdated() {
        mModel.setBaseData(new DataPointVo.DataPointList());
        mModel.setCreditScoreRange(CreditScoreRange.POOR);

        DataPointVo.DataPointList base = mModel.getBaseData();
        DataPointVo.CreditScore baseCredit = (DataPointVo.CreditScore) base.getUniqueDataPoint(
                DataPointVo.DataPointType.CreditScore, new DataPointVo.CreditScore());

        Assert.assertThat("Incorrect credit score range.",
                baseCredit.creditScoreRange, equalTo(mModel.getCreditScoreRange()));
    }

    /**
     * Given an empty Model.<br />
     * When setting an invalid credit score range.<br />
     * Then the credit score range should be ignored.
     */
    @Test
    public void invalidCreditScoreIsNotStored() {
        mModel.setCreditScoreRange(8);

        Assert.assertThat("An invalid credit score should be set.", mModel.getCreditScoreRange(), equalTo(0));
        Assert.assertFalse("Credit score should not be valid.", mModel.hasAllData());
    }
}
