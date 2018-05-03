package com.shiftpayments.link.sdk.sdk.tests.robolectric.tests.tasks.users;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.GetCurrentUserTask;
import com.shiftpayments.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the {@link GetCurrentUserTask} class.
 * @author Adrian
 */
public class GetCurrentUserTaskTest {

    private RoboLinkApiTaskWrapper<DataPointList, UnauthorizedRequestVo> mTask;

    /**
     * Sets up each test.
     */
    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(
                new GetCurrentUserTask(new UnauthorizedRequestVo(), new MockApiWrapper(), new MockResponseHandler(), false)
        );
    }

    /**
     * Given a {@link GetCurrentUserTask}.<br />
     * When the Task is executed.<br />
     * Then the API should be called.
     */
    @Test
    public void apiCallIsMade() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }

}
