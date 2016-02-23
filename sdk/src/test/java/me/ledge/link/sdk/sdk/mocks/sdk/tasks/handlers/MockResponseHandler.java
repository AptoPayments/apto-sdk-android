package me.ledge.link.sdk.sdk.mocks.sdk.tasks.handlers;

import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Mock implementation of the {@link ApiResponseHandler} interface.
 * @author Wijnand
 */
public class MockResponseHandler implements ApiResponseHandler {

    /** {@inheritDoc} */
    @Override
    public <T> void publishResult(T result) {
        // Do nothing.
    }
}
