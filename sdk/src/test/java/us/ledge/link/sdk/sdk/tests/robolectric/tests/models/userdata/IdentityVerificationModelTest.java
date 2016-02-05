package us.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.ledge.link.sdk.sdk.models.userdata.IdentityVerificationModel;

/**
 * Tests the {@link IdentityVerificationModel} class.
 * @author Wijnand
 */
public class IdentityVerificationModelTest {

    private IdentityVerificationModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new IdentityVerificationModel();
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid birthday.<br />
     * Then the birthday should be stored.
     */
    @Test
    public void validBirthdayIsStored() {
        mModel.setBirthday(2002, 3, 22);
        Assert.assertTrue("Birthday should be stored.", mModel.hasValidBirthday());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid SSN.<br />
     * Then the SSN should be stored.
     */
    @Test
    public void validSsnIsStored() {
        mModel.setSocialSecurityNumber("123456789");
        Assert.assertTrue("SSN should be stored.", mModel.hasValidSsn());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid SSN.<br />
     * Then the SSN should not be stored.
     */
    @Test
    public void invalidSsnIsNotStored() {
        mModel.setSocialSecurityNumber("123");
        Assert.assertFalse("SSN should NOT be stored.", mModel.hasValidSsn());
    }
}
