package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.users.LoginUserTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link LoginUserTask} class.
 * @author Adrian
 */
public class LoginUserTaskTest {

    private RoboLinkApiTaskWrapper<CreateUserResponseVo, DataPointList> mTask;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new LoginUserTask(new DataPointList(), new MockApiWrapper(), new MockResponseHandler())
        );
    }

    /**
     * Given a {@link LoginUserTask}.<br />
     * When the Task is executed.<br />
     * Then the API should be called.
     */
    @Test
    public void apiCallIsMade() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }

}
