package me.ledge.link.sdk.sdk.mocks.util.concurrent;

import java.util.concurrent.Executor;

/**
 * Mock implementation of the {@link Executor} interface.
 * @author Wijnand
 */
public class MockExecutor implements Executor {

    /** {@inheritDoc} */
    @Override
    public void execute(Runnable command) {
        // Do nothing.
    }
}
