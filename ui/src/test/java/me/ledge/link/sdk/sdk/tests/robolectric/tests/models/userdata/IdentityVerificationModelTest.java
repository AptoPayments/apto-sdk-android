package me.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import com.google.i18n.phonenumbers.Phonenumber;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.vos.UserDataVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;

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
    private static final int EXPECTED_COUNTRY_CODE = 1;
    private static final long EXPECTED_NATIONAL_NUMBER = 9495860722L;

    private static final String EXPECTED_FORMATTED_BIRTHDAY = "03-13-1996";
    private static final String EXPECTED_FORMATTED_PHONE = "+19495860722";

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
        mModel.setSocialSecurityNumber(EXPECTED_SSN);

        Assert.assertTrue("All data should be set.", mModel.hasAllData());
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
                equalTo(R.string.id_verification_label));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid birthday.<br />
     * Then the birthday should be stored.
     */
    @Test
    public void validBirthdayIsStored() {
        Calendar birthday = getValidBirthday();
        mModel.setBirthday(birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DATE));

        Assert.assertTrue("Birthday should be stored.", mModel.hasValidBirthday());
        Assert.assertFalse("There should be missing data.", mModel.hasAllData());
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
        mModel.setSocialSecurityNumber(EXPECTED_SSN);
        Assert.assertTrue("SSN should be stored.", mModel.hasValidSsn());
        Assert.assertFalse("There should be missing data.", mModel.hasAllData());
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

    /**
     * Given a Model with data set.<br />
     * When generating the API request data.<br />
     * Then the correct data should be created.
     */
    @Test
    public void apiDataIsCreated() {
        UserDataVo base = new UserDataVo();
        base.phoneNumber = new Phonenumber.PhoneNumber()
                .setCountryCode(EXPECTED_COUNTRY_CODE)
                .setNationalNumber(EXPECTED_NATIONAL_NUMBER);

        mModel.setBaseData(base);

        Calendar birthday = getValidBirthday();
        mModel.setBirthday(birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DATE));
        mModel.setSocialSecurityNumber(EXPECTED_SSN);

        CreateUserRequestVo apiData = mModel.getUserRequestData();
        Assert.assertThat("Incorrect phone number.", apiData.phone_number, equalTo(EXPECTED_FORMATTED_PHONE));
        Assert.assertThat("Incorrect birthday.", apiData.birthdate, equalTo(EXPECTED_FORMATTED_BIRTHDAY));
    }

}
