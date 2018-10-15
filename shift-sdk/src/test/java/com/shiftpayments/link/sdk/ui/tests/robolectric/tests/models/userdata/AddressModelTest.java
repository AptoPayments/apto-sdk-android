package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.userdata;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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

    private static final String EXPECTED_STREET_NUMBER = "31881";
    private static final String EXPECTED_STREET = "Stoney Creek Rd";
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
                equalTo(R.string.address_title));
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

        Assert.assertThat("Incorrect address.", mModel.getFullAddress(), equalTo(baseAddress.toString()));
        Assert.assertTrue("All data should be set.", mModel.hasValidAddress());
    }

    /**
     * Given a Model with all data set.<br />
     * When fetching the base data.<br />
     * Then the base data should contain the same values as the Model.
     */
    @Test
    public void baseDataIsUpdated() {
        mModel.setBaseData(new DataPointList());

        mModel.setStreet(EXPECTED_STREET);
        mModel.setStreetNumber(EXPECTED_STREET_NUMBER);
        mModel.setCity(EXPECTED_CITY);
        mModel.setRegion(EXPECTED_STATE);
        mModel.setCountry(EXPECTED_COUNTRY);

        DataPointList base = mModel.getBaseData();
        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());

        Assert.assertThat("Incorrect address.", baseAddress.streetOne, equalTo(mModel.getAddress().streetOne));
        Assert.assertThat("Incorrect city.", baseAddress.locality, equalTo(mModel.getAddress().locality));
        Assert.assertThat("Incorrect state.", baseAddress.region, equalTo(mModel.getAddress().region));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid address.<br />
     * Then the address should be stored.
     */
    @Test
    public void validAddressIsStored() {
        mModel.setStreet(EXPECTED_STREET);
        mModel.setStreetNumber(EXPECTED_STREET_NUMBER);
        Assert.assertTrue("Address should be stored.", mModel.hasValidStreet());
        Assert.assertThat("Incorrect address.", mModel.getAddress().streetOne, equalTo(EXPECTED_ADDRESS));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store an invalid address.<br />
     * Then the address should not be stored.
     */
    @Test
    public void invalidAddressIsNotStored() {
        mModel.setStreet("");
        Assert.assertFalse("Address should NOT be stored.", mModel.hasValidAddress());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid city.<br />
     * Then the city should be stored.
     */
    @Test
    public void validCityIsStored() {
        mModel.setCity(EXPECTED_CITY);
        Assert.assertTrue("City should be stored.", mModel.hasValidLocality());
        Assert.assertThat("Incorrect city.", mModel.getAddress().locality, equalTo(EXPECTED_CITY));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store an invalid city.<br />
     * Then the city should not be stored.
     */
    @Test
    public void invalidCityIsNotStored() {
        mModel.setCity("");
        Assert.assertFalse("City should NOT be stored.", mModel.hasValidLocality());
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid state.<br />
     * Then the state should be stored.
     */
    @Test
    public void validStateIsStored() {
        mModel.setRegion(EXPECTED_STATE);
        Assert.assertTrue("State should be stored.", mModel.hasValidRegion());
        Assert.assertThat("Incorrect state.", mModel.getAddress().region, equalTo(EXPECTED_STATE));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store an invalid state.<br />
     * Then the state should not be stored.
     */
    @Test
    public void invalidStateIsNotStored() {
        mModel.setRegion("");
        Assert.assertFalse("State should NOT be stored.", mModel.hasValidRegion());
    }
}
