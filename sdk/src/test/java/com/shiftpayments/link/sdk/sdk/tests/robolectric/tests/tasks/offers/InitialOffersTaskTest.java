package com.shiftpayments.link.sdk.sdk.tests.robolectric.tests.tasks.offers;

import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.offers.InitialOffersTask;
import com.shiftpayments.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;
import com.shiftpayments.link.sdk.sdk.utils.tasks.RoboLinkApiTaskWrapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;

public class InitialOffersTaskTest {

    private RoboLinkApiTaskWrapper<InitialOffersResponseVo, InitialOffersRequestVo> mTask;

    @Before
    public void setUp() {
        mTask = new RoboLinkApiTaskWrapper<>(new InitialOffersTask(
                new InitialOffersRequestVo(), new MockApiWrapper(), new MockResponseHandler()
        ));
    }

    @Test
    public void apiResponseIsNotEmpty() {
        InitialOffersResponseVo response = mTask.execute();

        Assert.assertThat("Result should not be empty.", response, not(nullValue()));
        Assert.assertThat("Offers container should not be empty.", response.offers, not(nullValue()));
        Assert.assertThat("Offers list should not be empty.", response.offers.data, not(nullValue()));
        Assert.assertThat("Incorrect number of offers.", response.offers.data.length, equalTo(2));
    }
}
