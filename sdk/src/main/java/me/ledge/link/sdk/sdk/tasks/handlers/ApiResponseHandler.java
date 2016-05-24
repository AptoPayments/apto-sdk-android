package me.ledge.link.sdk.sdk.tasks.handlers;

/**
 * API response handler.
 * @author Wijnand
 */
public interface ApiResponseHandler {

    /**
     * Registers the {@code target} to be notified of API response.
     * @param target The target.
     */
    void subscribe(Object target);

    /**
     * Unregisters the {@code target}.
     * @param target The target.
     */
    void unsubscribe(Object target);

    /**
     * Publishes the API response.
     * @param result The result to publish.
     * @param <T> Type of the result.
     */
    <T> void publishResult(T result);
}
