package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.userdata;

import com.shiftpayments.link.sdk.api.vos.datapoints.IdDocument;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.IdentityVerificationModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link IdentityVerificationModel} class.
 * @author Wijnand
 */
public class IdentityVerificationModelTest {

    private static final int MINIMUM_AGE = 18;

    private static final String EXPECTED_SSN = "123456789";
    private static final String EXPECTED_COUNTRY_CODE = "US";
    private static final IdDocument.IdDocumentType EXPETED_DOCUMENT_TYPE = IdDocument.IdDocumentType.SSN;

    private IdentityVerificationModel mModel;

    /**
     * @return A valid birthday.
     */
    private Calendar getValidBirthday() {
        Calendar birthday = GregorianCalendar.getInstance();
        birthday.set(Calendar.YEAR, 1996);
        birthday.set(Calendar.MONTH, Calendar.MARCH);
        birthday.set(Calendar.DATE, 13);

        return birthday;
    }

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
     * When setting a valid birthday and SSN.<br />
     * Then all data should be set.
     */
    @Test
    public void allDataIsSet() {
        Calendar birthday = getValidBirthday();
        mModel.setBirthday(birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DATE));
        mModel.setDocumentNumber(EXPECTED_SSN);
        mModel.setDocumentType(EXPETED_DOCUMENT_TYPE);
        mModel.setCountry(EXPECTED_COUNTRY_CODE);

        Assert.assertTrue("All data should be set.", mModel.hasValidData());
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
                equalTo(R.string.id_verification_title_get_offers));
    }

    /**
     * Given an empty Model.<br />
     * When storing a valid birthday.<br />
     * Then the birthday should be flagged as valid.
     */
    @Test
    public void validBirthday() {
        Calendar birthday = getValidBirthday();
        mModel.setBirthday(birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DATE));

        Assert.assertTrue("Birthday should be valid.", mModel.hasValidBirthday());
        Assert.assertFalse("There should be missing data.", mModel.hasValidData());
    }

    /**
     * Given an empty Model.<br />
     * When storing an invalid birthday.<br />
     * Then the birthday should be flagged as invalid.
     */
    @Test
    public void invalidBirthday() {
        Calendar today = GregorianCalendar.getInstance();
        mModel.setBirthday(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        Assert.assertFalse("Birthday should NOT be valid.", mModel.hasValidBirthday());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid SSN.<br />
     * Then the SSN should be stored.
     */
    @Test
    public void validIdDocumentIsStored() {
        mModel.setDocumentNumber(EXPECTED_SSN);
        mModel.setDocumentType(EXPETED_DOCUMENT_TYPE);
        mModel.setCountry(EXPECTED_COUNTRY_CODE);
        Assert.assertTrue("SSN should be stored.", mModel.hasValidDocument());
        Assert.assertFalse("There should be missing data.", mModel.hasValidData());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid SSN.<br />
     * Then the SSN should not be stored.
     */
    @Test
    public void invalidIdDocumentIsNotStored() {
        mModel.setDocumentNumber("");
        Assert.assertFalse("Id Document should NOT be stored.", mModel.hasValidDocument());
    }
}
