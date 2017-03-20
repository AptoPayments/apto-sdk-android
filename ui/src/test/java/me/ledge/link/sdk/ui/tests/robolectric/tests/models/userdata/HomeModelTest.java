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
import me.ledge.link.api.vos.datapoints.Housing;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.mocks.answers.textutils.IsEmptyAnswer;
import me.ledge.link.sdk.ui.models.userdata.HomeModel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Tests the {@link HomeModel} class.
 * @author Adrian
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TextUtils.class })
public class HomeModelTest {
    
    private static final String EXPECTED_ZIP = "92679";
    private static final int EXPECTED_HOUSING_TYPE_ID = 777;

    private HomeModel mModel;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new IsEmptyAnswer());

        mModel = new HomeModel();
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
        Address baseAddress = new Address();
        baseAddress.zip = EXPECTED_ZIP;
        base.add(baseAddress);
        Housing baseHousing = new Housing(EXPECTED_HOUSING_TYPE_ID, false);
        base.add(baseHousing);
        mModel.setBaseData(base);

        Assert.assertThat("Incorrect zip.", mModel.getZip(), equalTo(baseAddress.zip));
        Assert.assertThat("Incorrect housing type.", mModel.getHousingType().getKey(), equalTo(baseHousing.housingType.getKey()));
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
        mModel.setZip(EXPECTED_ZIP);
        mModel.setHousingType(new IdDescriptionPairDisplayVo(EXPECTED_HOUSING_TYPE_ID, null));

        DataPointList base = mModel.getBaseData();
        Address baseAddress = (Address) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Address, new Address());

        Housing baseHousing = (Housing) base.getUniqueDataPoint(
                DataPointVo.DataPointType.Housing, new Housing());

        Assert.assertThat("Incorrect zip.", baseAddress.zip, equalTo(mModel.getZip()));
        Assert.assertThat("Incorrect housing type.", baseHousing.housingType.getKey(), equalTo(mModel.getHousingType().getKey()));
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
     * When trying to store an invalid ZIP code.<br />
     * Then the ZIP code should not be stored.
     */
    @Test
    public void invalidZipIsNotStored() {
        mModel.setZip("abc");
        Assert.assertFalse("ZIP code should NOT be stored.", mModel.hasValidZip());
    }
}
