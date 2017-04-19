package me.ledge.link.sdk.sdk.tests.robolectric.tests.storages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link ConfigStorage} class.
 * @author Adrian
 */
public class ConfigStorageTest {

    private ConfigStorage mStorage;

    /**
     * Sets up each test before it is run.
     */
    @Before
    public void setUp() {
        mStorage = ConfigStorage.getInstance();
        LinkConfigResponseVo configResponse = new MockApiWrapper().getLinkConfig(new UnauthorizedRequestVo());
        mStorage.setLinkConfig(configResponse);
    }

    /**
     * Given the config storage.<br />
     * When loan purposes are being requested. <br />
     * Then the whole list of the response should be returned.
     */
    @Test
    public void loanPurposesAreRetrieved() {
        Assert.assertThat("Loan purposes should not be null.", mStorage.getLoanPurposes(), not(nullValue()));
    }

    /**
     * Given the config storage.<br />
     * When required datapoints are being requested. <br />
     * Then the whole list of the response should be returned.
     */
    @Test
    public void requiredDataPointsAreRetrieved() {
        Assert.assertThat("Required user data should not be null.", mStorage.getRequiredUserData(), not(nullValue()));
    }
}