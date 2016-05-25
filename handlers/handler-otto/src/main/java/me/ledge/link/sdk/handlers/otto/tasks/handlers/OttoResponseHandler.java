package me.ledge.link.sdk.handlers.otto.tasks.handlers;

import com.squareup.otto.Bus;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Concrete {@link ApiResponseHandler} that uses the Square's {@link Otto} event bus.
 * @author Wijnand
 */
public class OttoResponseHandler implements ApiResponseHandler {

    private Bus mBus;

    /**
     * Creates a new {@link OttoResponseHandler} instance.
     */
    public OttoResponseHandler() {
        this(new Bus());
    }

    /**
     * Creates a new {@link OttoResponseHandler} instance.
     * @param bus The event {@link Bus} to use.
     */
    public OttoResponseHandler(Bus bus) {
        mBus = bus;
    }

    /** {@inheritDoc} */
    @Override
    public void subscribe(Object target) {
        mBus.register(target);
    }

    /** {@inheritDoc} */
    @Override
    public void unsubscribe(Object target) {
        try {
            mBus.unregister(target);
        } catch (IllegalArgumentException e) {
            // Bus was already unregistered.
        }
    }

    /** {@inheritDoc} */
    @Override
    public <T> void publishResult(T result) {
        if (result != null) {
            mBus.post(result);
        }
    }
}
