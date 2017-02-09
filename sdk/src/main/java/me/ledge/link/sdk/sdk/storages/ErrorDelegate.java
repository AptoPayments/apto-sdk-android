package me.ledge.link.sdk.sdk.storages;

/**
 * Delegation interface when an error is received from the Config Storage.
 *
 * @author Adrian
 */
public interface ErrorDelegate {

    void errorReceived(String error);
}
