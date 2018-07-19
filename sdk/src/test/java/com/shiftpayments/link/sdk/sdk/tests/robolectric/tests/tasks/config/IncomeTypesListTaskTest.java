package com.shiftpayments.link.sdk.sdk.tests.robolectric.tests.tasks.config;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetProjectConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.config.IncomeTypesListTask;
import com.shiftpayments.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class IncomeTypesListTaskTest {

    private RoboLinkApiTaskWrapper<ConfigResponseVo, UnauthorizedRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(new IncomeTypesListTask(
                new GetProjectConfigRequestVo(), new MockApiWrapper(), new MockResponseHandler()
        ));
    }

    @Test
    public void apiResponseIsNotEmpty() {
        Assert.assertThat("Result should not be empty.", mTask.execute(), not(nullValue()));
    }
}
