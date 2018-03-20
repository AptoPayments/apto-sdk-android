package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.api.vos.datapoints.CreditScore;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.CreditScoreModel;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link CreditScoreModel} class.
 * @author wijnand
 */
public class CreditScoreModelTest {

    private CreditScoreModel mModel;
    private final int VALID_CREDIT_SCORE = 1;

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
        DataPointList baseData = new DataPointList();
        CreditScore baseCredit = new CreditScore(VALID_CREDIT_SCORE, false, false);
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
        mModel.setBaseData(new DataPointList());
        mModel.setCreditScoreRange(VALID_CREDIT_SCORE);

        DataPointList base = mModel.getBaseData();
        CreditScore baseCredit = (CreditScore) base.getUniqueDataPoint(
                DataPointVo.DataPointType.CreditScore, new CreditScore());

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
        mModel.setCreditScoreRange(-1);

        Assert.assertThat("An invalid credit score should be set.", mModel.getCreditScoreRange(), equalTo(-1));
        Assert.assertFalse("Credit score should not be valid.", mModel.hasAllData());
    }
}
