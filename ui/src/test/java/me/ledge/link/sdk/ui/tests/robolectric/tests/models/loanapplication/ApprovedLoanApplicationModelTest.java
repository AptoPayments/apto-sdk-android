package me.ledge.link.sdk.ui.tests.robolectric.tests.models.loanapplication;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.ApprovedLoanApplicationModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link ApprovedLoanApplicationModel} class.
 * @author Wijnand
 */
public class ApprovedLoanApplicationModelTest {

    private ApprovedLoanApplicationModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new ApprovedLoanApplicationModel(null);
    }

    /**
     * Given a populated Model.<br />
     * When determining what cloud image should be used.<br />
     * Then the happy cloud has to be the result.
     */
    @Test
    public void correctCloudImage() {
        Assert.assertThat("Incorrect image.", mModel.getCloudImageResource(), equalTo(R.drawable.icon_cloud_happy));
    }
}
