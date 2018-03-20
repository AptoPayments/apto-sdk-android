package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.responses.users.UserResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.users.UpdateUserTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link UpdateUserTask} class.
 * @author Wijnand
 */
public class UpdateUserTaskTest {

    private RoboLinkApiTaskWrapper<UserResponseVo, DataPointList> mTask;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new UpdateUserTask(new DataPointList(), new MockApiWrapper(), new MockResponseHandler())
        );
    }

    /**
     * Given a {@link UpdateUserTask}.<br />
     * When the Task is executed.<br />
     * Then the API should be called.
     */
    @Test
    public void apiCallIsMade() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }

}
