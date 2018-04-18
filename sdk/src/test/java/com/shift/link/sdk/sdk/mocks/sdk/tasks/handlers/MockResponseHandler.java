package com.shift.link.sdk.sdk.mocks.sdk.tasks.handlers;

import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Mock implementation of the {@link ApiResponseHandler} interface.
 * @author Wijnand
 */
public class MockResponseHandler implements ApiResponseHandler {

    /** {@inheritDoc} */
    @Override
    public void subscribe(Object target) {
        // Do nothing.
    }

    /** {@inheritDoc} */
    @Override
    public void unsubscribe(Object target) {
        // Do nothing.
    }

    /** {@inheritDoc} */
    @Override
    public <T> void publishResult(T result) {
        // Do nothing.
    }
}
