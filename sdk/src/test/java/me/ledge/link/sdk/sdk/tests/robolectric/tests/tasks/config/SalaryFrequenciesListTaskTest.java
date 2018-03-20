package me.ledge.link.sdk.sdk.tests.robolectric.tests.tasks.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.sdk.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import me.ledge.link.sdk.sdk.tasks.config.SalaryFrequenciesListTask;
import me.ledge.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class SalaryFrequenciesListTaskTest {

    private RoboLinkApiTaskWrapper<ConfigResponseVo, UnauthorizedRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(new SalaryFrequenciesListTask(
                new UnauthorizedRequestVo(), new MockApiWrapper(), new MockResponseHandler()
        ));
    }

    @Test
    public void apiResponseIsNotEmpty() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }
}
