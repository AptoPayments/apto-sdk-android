package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.link.TermsModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Tests the {@link TermsModel} class.
 * @author wijnand
 */
public class TermsModelTest {

    private TermsModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new TermsModel();
    }

    /**
     * Given an empty Model.<br />
     * When verifying that all data has been set.<br />
     * Then the result should always be true.
     */
    @Test
    public void dataIsAlwaysAllThere() {
        Assert.assertTrue("No input data required.", mModel.hasAllData());
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
                equalTo(R.string.terms_label));
    }

}
