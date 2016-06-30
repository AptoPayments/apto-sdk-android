package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks;

import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiErrorTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class LedgeLinkApiTaskTest {

    private RoboLinkApiTaskWrapper<Object, Object> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new RoboLinkApiErrorTask(null, new MockApiWrapper(), new MockResponseHandler())
        );
    }

    @Test
    public void failedTaskHasNoResult() {
        Assert.assertThat("Result should be empty.", mTask.execute(), is(nullValue()));
    }
}
