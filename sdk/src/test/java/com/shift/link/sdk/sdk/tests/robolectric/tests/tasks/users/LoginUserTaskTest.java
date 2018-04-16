package com.shift.link.sdk.sdk.tests.robolectric.tests.tasks.users;

import com.shift.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shift.link.sdk.api.vos.responses.users.LoginUserResponseVo;
import com.shift.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shift.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shift.link.sdk.sdk.tasks.users.LoginUserTask;
import com.shift.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link LoginUserTask} class.
 * @author Adrian
 */
public class LoginUserTaskTest {

    private RoboLinkApiTaskWrapper<LoginUserResponseVo, LoginRequestVo> mTask;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new LoginUserTask(new LoginRequestVo(null), new MockApiWrapper(), new MockResponseHandler())
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
