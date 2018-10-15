package com.shiftpayments.link.sdk.ui.tests.robolectric.tests.models.userdata;

import android.text.TextUtils;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import com.shiftpayments.link.sdk.ui.models.userdata.ApartmentModel;

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
 * Tests the {@link ApartmentModel} class.
 * @author Adrian
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class ApartmentModelTest {
    
    private static final String EXPECTED_APARTMENT = "100";

    private ApartmentModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());

        mModel = new ApartmentModel();
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
                equalTo(R.string.apartment_title));
    }

    /**
     * Given an empty Model.<br />
     * When setting base data with valid first name, last name, email and phone number.<br />
     * Then the Model should contain the same values as the base data.
     */
    @Test
    public void allDataIsSetFromBaseData() {
        DataPointList base = new DataPointList();
        Address baseAddress = new Address();
        baseAddress.streetTwo = EXPECTED_APARTMENT;
        base.add(baseAddress);
        mModel.setBaseData(base);

        Assert.assertThat("Incorrect apartment number.", mModel.getApartmentNumber(), equalTo(baseAddress.streetTwo));
        Assert.assertTrue("All data should be set.", mModel.hasValidData());
    }

    /**
     * Given a Model with all data set.<br />
     * When fetching the base data.<br />
     * Then the base data should contain the same values as the Model.
     */
    @Test
    public void baseDataIsUpdated() {
        mModel.setBaseData(new DataPointList());
        mModel.setApartmentNumber(EXPECTED_APARTMENT);

        DataPointList base = mModel.getBaseData();
        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());

        Assert.assertThat("Incorrect apartment number.", baseAddress.streetTwo, equalTo(mModel.getApartmentNumber()));
    }

    /**
     * Given an empty Model.<br />
     * When trying to store a valid apartment number.<br />
     * Then the apartment number should be stored.
     */
    @Test
    public void validApartmentIsStored() {
        mModel.setApartmentNumber(EXPECTED_APARTMENT);
        Assert.assertTrue("Apartment number should be stored.", mModel.hasValidData());
        Assert.assertThat("Incorrect apartment number.", mModel.getApartmentNumber(), equalTo(EXPECTED_APARTMENT));
    }
    /**
     * Given an empty Model.<br />
     * When trying to store an empty apartment number.<br />
     * Then the user should be allowed to continue.
     */
    @Test
    public void emptyApartmentNumberIsAllowed() {
        mModel.setApartmentNumber("");
        Assert.assertTrue("Apartment number can be empty.", mModel.hasValidData());
    }
}
