package me.ledge.link.sdk.sdk.tasks.handlers;

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
    public <T> void publishResult(T result) {
        if (result != null) {
            mBus.post(result);
        }
    }
}