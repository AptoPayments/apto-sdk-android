package us.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import us.ledge.link.sdk.sdk.models.userdata.IdentityVerificationModel;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tests the {@link IdentityVerificationModel} class.
 * @author Wijnand
 */
public class IdentityVerificationModelTest {

    private static final int MINIMUM_AGE = 18;

    private IdentityVerificationModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mModel = new IdentityVerificationModel();
        mModel.setMinimumAge(MINIMUM_AGE);
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid birthday.<br />
     * Then the birthday should be stored.
     */
    @Test
    public void validBirthdayIsStored() {
        Calendar today = GregorianCalendar.getInstance();
        today.add(Calendar.YEAR, MINIMUM_AGE * -2);

        mModel.setBirthday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        Assert.assertTrue("Birthday should be stored.", mModel.hasValidBirthday());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid birthday.<br />
     * Then the birthday should be stored.
     */
    @Test
    public void invalidBirthdayIsNotStored() {
        Calendar today = GregorianCalendar.getInstance();
        mModel.setBirthday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        Assert.assertFalse("Birthday should NOT be stored.", mModel.hasValidBirthday());
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
