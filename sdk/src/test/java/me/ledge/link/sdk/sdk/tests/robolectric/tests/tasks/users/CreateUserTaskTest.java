package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.users;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.users.CreateUserTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link CreateUserTask} class.
 * @author Wijnand
 */
public class CreateUserTaskTest {

    private RoboLinkApiTaskWrapper<CreateUserResponseVo, DataPointList> mTask;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new CreateUserTask(new DataPointList(), new MockApiWrapper(), new MockResponseHandler())
        );
    }

    /**
     * Given a {@link CreateUserTask}.<br />
     * When the Task is executed.<br />
     * Then the API should be called.
     */
    @Test
    public void apiCallIsMade() {
        CreateUserResponseVo result = mTask.execute();

        Assert.assertThat("Result should not be empty.", result, not(nullValue()));
        Assert.assertThat("Incorrect token.", result.user_token, equalTo(MockApiWrapper.TOKEN));
    }

}
