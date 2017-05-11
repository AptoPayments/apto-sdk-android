package me.ledge.link.sdk.ui.tests.robolectric.tests.models.userdata;

import android.text.TextUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import me.ledge.link.sdk.ui.models.userdata.AddressModel;

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
    private static final String EXPECTED_COUNTRY = "US";

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
     * When fetching the title resource ID.<br />
     * Then the correct ID should be returned.
     */
    @Test
    public void hasCorrectTitleResource() {
        Assert.assertThat("Incorrect resource ID.",
                mModel.getActivityTitleResource(),
                equalTo(R.string.address_label));
    }

    /**
     * Given an empty Model.<br />
     * When setting base data with valid first name, last name, email and phone number.<br />
     * Then the Model should contain the same values as the base data.
     */
    @Test
    public void allDataIsSetFromBaseData() {
        DataPointList base = new DataPointList();
        Address baseAddress = new Address(EXPECTED_ADDRESS,
                EXPECTED_APARTMENT_NUMBER, EXPECTED_COUNTRY, EXPECTED_CITY, EXPECTED_STATE,
                EXPECTED_ZIP,false, false);
        base.add(baseAddress);
        mModel.setBaseData(base);

        Assert.assertThat("Incorrect address.", mModel.getStreetAddress(), equalTo(baseAddress.address));
        Assert.assertThat("Incorrect apartment number.", mModel.getApartmentNumber(), equalTo(baseAddress.apUnit));
        Assert.assertThat("Incorrect city.", mModel.getCity(), equalTo(baseAddress.city));
        Assert.assertThat("Incorrect state.", mModel.getState(), equalTo(baseAddress.stateCode));
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

        mModel.setStreetAddress(EXPECTED_ADDRESS);
        mModel.setApartmentNumber(EXPECTED_APARTMENT_NUMBER);
        mModel.setCity(EXPECTED_CITY);
        mModel.setState(EXPECTED_STATE);

        DataPointList base = mModel.getBaseData();
        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());

        Assert.assertThat("Incorrect address.", baseAddress.address, equalTo(mModel.getStreetAddress()));
        Assert.assertThat("Incorrect apartment number.", baseAddress.apUnit, equalTo(mModel.getApartmentNumber()));
        Assert.assertThat("Incorrect city.", baseAddress.city, equalTo(mModel.getCity()));
        Assert.assertThat("Incorrect state.", baseAddress.stateCode, equalTo(mModel.getState()));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid address.<br />
     * Then the address should be stored.
     */
    @Test
    public void validAddressIsStored() {
        mModel.setStreetAddress(EXPECTED_ADDRESS);
        Assert.assertTrue("Address should be stored.", mModel.hasValidAddress());
        Assert.assertThat("Incorrect address.", mModel.getStreetAddress(), equalTo(EXPECTED_ADDRESS));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store an invalid address.<br />
     * Then the address should not be stored.
     */
    @Test
    public void invalidAddressIsNotStored() {
        mModel.setStreetAddress("");
        Assert.assertFalse("Address should NOT be stored.", mModel.hasValidAddress());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store an invalid address and strict validation is enabled.<br />
     * Then the address should not be valid.
     */
    @Test
    public void invalidAddressWithStrictValidationIsNotStored() {
        mModel.setStreetAddress("");
        Assert.assertFalse("Address should NOT be stored.", mModel.hasVerifiedAddress());
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
     * When trying to store an invalid city.<br />
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
     * When trying to store an invalid state.<br />
     * Then the state should not be stored.
     */
    @Test
    public void invalidStateIsNotStored() {
        mModel.setState("1234");
        Assert.assertFalse("State should NOT be stored.", mModel.hasValidState());
    }
}
