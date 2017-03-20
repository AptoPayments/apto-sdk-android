package me.ledge.link.sdk.ui.tests.robolectric.tests.storages;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.api.vos.responses.config.ContextConfigResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.ui.storages.UIStorage;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link UIStorage} class.
 * @author Adrian
 */
public class UIStorageTest {

    private UIStorage mStorage;
    private static final String TEST_COLOR = "FFFFFF";
    private static final int EXPECTED_COLOR = 0;

    /**
     * Sets up each test before it is run.
     */
    @Before
    public void setUp() {
        mStorage = UIStorage.getInstance();
    }

    /**
     * Given the {@link ContextConfigResponseVo}.<br />
     * When storing the API response. <br />
     * Then the colors should be parsed and stored.
     */
    @Test
    public void colorsAreStored() {
        ContextConfigResponseVo contextConfig = new MockApiWrapper().getUserConfig(null);
        contextConfig.projectConfiguration.primaryColor = TEST_COLOR;
        contextConfig.projectConfiguration.secondaryColor = TEST_COLOR;
        mStorage.setConfig(contextConfig);

        Assert.assertThat("Primary color is not correct.", mStorage.getPrimaryColor(), equalTo(EXPECTED_COLOR));
        Assert.assertThat("Secondary color is not correct.", mStorage.getSecondaryColor(), equalTo(EXPECTED_COLOR));
        Assert.assertThat("Primary contrast color is not correct.", mStorage.getPrimaryContrastColor(), equalTo(Color.WHITE));
    }
}
