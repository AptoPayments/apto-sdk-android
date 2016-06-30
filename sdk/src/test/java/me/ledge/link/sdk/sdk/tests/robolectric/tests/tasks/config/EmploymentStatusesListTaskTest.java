package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.config;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.EmploymentStatusListResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.config.EmploymentStatusesListTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class EmploymentStatusesListTaskTest {

    private RoboLinkApiTaskWrapper<EmploymentStatusListResponseVo, UnauthorizedRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(new EmploymentStatusesListTask(
                new UnauthorizedRequestVo(), new MockApiWrapper(), new MockResponseHandler()
        ));
    }

    @Test
    public void apiResponseIsNotEmpty() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }
}
