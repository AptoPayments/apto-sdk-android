package me.ledge.link.sdk.sdk.tests.robolectric.tests.models.userdata;

import android.text.TextUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import me.ledge.link.sdk.sdk.mocks.answers.textutils.IsEmptyAnswer;
import me.ledge.link.sdk.sdk.models.userdata.AddressModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the {@link AddressModel} class.
 * @author Wijnand
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class AddressModelTest {

    private static final String EXPECTED_ADDRESS = "31881 Stoney Creek Rd";
    private static final String EXPECTED_APARTMENT_NUMBER = "4d";
    private static final String EXPECTED_CITY = "Trabuco Canyon";
    private static final String EXPECTED_STATE = "CA";
    private static final String EXPECTED_ZIP = "92679";

    private AddressModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());

        mModel = new AddressModel();
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid address.<br />
     * Then the address should be stored.
     */
    @Test
    public void validAddressIsStored() {
        mModel.setAddress(EXPECTED_ADDRESS);
        Assert.assertTrue("Address should be stored.", mModel.hasValidAddress());
        Assert.assertThat("Incorrect address.", mModel.getAddress(), equalTo(EXPECTED_ADDRESS));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid address.<br />
     * Then the address should not be stored.
     */
    @Test
    public void invalidAddressIsNotStored() {
        mModel.setAddress("Stoney Creek Rd");
        Assert.assertFalse("Address should NOT be stored.", mModel.hasValidAddress());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store any apartment number.<br />
     * Then the apartment number should be stored.
     */
    @Test
    public void apartmentNumberIsAlwaysStored() {
        mModel.setApartmentNumber(EXPECTED_APARTMENT_NUMBER);
        Assert.assertThat("Incorrect apartment number.",
                mModel.getApartmentNumber(), equalTo(EXPECTED_APARTMENT_NUMBER));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid city.<br />
     * Then the city should be stored.
     */
    @Test
    public void validCityIsStored() {
        mModel.setCity(EXPECTED_CITY);
        Assert.assertTrue("City should be stored.", mModel.hasValidCity());
        Assert.assertThat("Incorrect city.", mModel.getCity(), equalTo(EXPECTED_CITY));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid city.<br />
     * Then the city should not be stored.
     */
    @Test
    public void invalidCityIsNotStored() {
        mModel.setCity("");
        Assert.assertFalse("City should NOT be stored.", mModel.hasValidCity());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid state.<br />
     * Then the state should be stored.
     */
    @Test
    public void validStateIsStored() {
        mModel.setState(EXPECTED_STATE);
        Assert.assertTrue("State should be stored.", mModel.hasValidState());
        Assert.assertThat("Incorrect state.", mModel.getState(), equalTo(EXPECTED_STATE));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid state.<br />
     * Then the state should not be stored.
     */
    @Test
    public void invalidStateIsNotStored() {
        mModel.setState("1234");
        Assert.assertFalse("State should NOT be stored.", mModel.hasValidState());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid ZIP code.<br />
     * Then the ZIP code should be stored.
     */
    @Test
    public void validZipIsStored() {
        mModel.setZip(EXPECTED_ZIP);
        Assert.assertTrue("ZIP code should be stored.", mModel.hasValidZip());
        Assert.assertThat("Incorrect ZIP code.", mModel.getZip(), equalTo(EXPECTED_ZIP));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a invalid ZIP code.<br />
     * Then the ZIP code should not be stored.
     */
    @Test
    public void invalidZipIsNotStored() {
        mModel.setZip("abc");
        Assert.assertFalse("ZIP code should NOT be stored.", mModel.hasValidZip());
    }
}
