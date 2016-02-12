package me.ledge.link.sdk.sdk.tasks.handlers;

/**
 * TODO: Class documentation.
 *
 * @author Wijnand
 */
public interface ApiResponseHandler {

    <T> void publishResult(T result);
}
