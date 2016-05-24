package me.ledge.link.sdk.handlers.eventbus.tasks.handlers;

import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import org.greenrobot.eventbus.EventBus;

/**
 * Concrete {@link ApiResponseHandler} that uses the GreenRobot {@link EventBus}.
 * @author Wijnand
 */
public class EventBusThreeResponseHandler implements ApiResponseHandler {

    private EventBus mBus;

    /**
     * Creates a new {@link EventBusThreeResponseHandler} instance.
     */
    public EventBusThreeResponseHandler() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mBus = EventBus.getDefault();
    }

    /** {@inheritDoc} */
    @Override
    public void subscribe(Object target) {
        mBus.register(target);
    }

    /** {@inheritDoc} */
    @Override
    public void unsubscribe(Object target) {
        mBus.unregister(target);
    }

    /** {@inheritDoc} */
    @Override
    public <T> void publishResult(T result) {
        if (result != null) {
            mBus.post(result);
        }
    }
}
