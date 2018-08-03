package com.shiftpayments.link.sdk.sdk.tests.robolectric.tests;

import android.os.AsyncTask;

import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.mocks.api.wrappers.MockApiWrapper;
import com.shiftpayments.link.sdk.sdk.mocks.sdk.tasks.handlers.MockResponseHandler;
import com.shiftpayments.link.sdk.sdk.mocks.util.concurrent.MockExecutor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Tests the {@link ShiftLinkSdk} class.
 * @author Wijnand
 */
public class ShiftLinkSdkTest {

    @Before
    public void setUp() {
        ShiftLinkSdk.setApiWrapper(new MockApiWrapper());
        ShiftLinkSdk.setExecutor(new MockExecutor());
        ShiftLinkSdk.setResponseHandler(new MockResponseHandler());
    }

    /**
     * Cleans up after each test.
     */
    @After
    public void tearDown() {
        clearMocks();
    }

    private void clearMocks() {
        ShiftLinkSdk.setApiWrapper(null);
        ShiftLinkSdk.setExecutor(null);
        ShiftLinkSdk.setResponseHandler(null);
    }

    /**
     * Given no Task {@link Executor} has been set yet.<br />
     * When trying to get a reference to the {@link Executor}.<br />
     * Then the default {@link Executor} from the ApiWrapper should be returned.
     */
    @Test
    public void defaultExecutorIsUsed() {
        clearMocks();
        ShiftLinkSdk.setApiWrapper(new MockApiWrapper());
        Assert.assertThat("Incorrect Executor.", ShiftLinkSdk.getExecutor(), equalTo(AsyncTask.THREAD_POOL_EXECUTOR));
    }

    /**
     * Given a custom {@link Executor} has been set.<br />
     * When fetching the {@link Executor}.<br />
     * Then the custom {@link Executor} should be returned.
     */
    @Test
    public void customExecutorIsUsed() {
        clearMocks();

        MockExecutor executor = new MockExecutor();
        ShiftLinkSdk.setExecutor(executor);

        Assert.assertThat("Incorrect Executor.", ShiftLinkSdk.getExecutor(), equalTo(executor));
    }

    /**
     * Given no API Wrapper has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noApiWrapperThrowsError() {
        clearMocks();
        ShiftLinkSdk.createUser(null);
    }

    /**
     * Given no response handler has been set.<br />
     * When trying to make an API call.<br />
     * Then a {@link NullPointerException} should be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void noResponseHandlerThrowsError() {
        clearMocks();
        ShiftLinkSdk.setApiWrapper(new MockApiWrapper());
        ShiftLinkSdk.createUser(null);
    }
}
