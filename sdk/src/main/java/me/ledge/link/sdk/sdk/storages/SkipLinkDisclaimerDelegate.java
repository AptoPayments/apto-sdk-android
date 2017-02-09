package me.ledge.link.sdk.sdk.storages;

/**
 * Delegation interface for accessing the skip link disclaimer option stored in the Config Storage.
 *
 * @author Adrian
 */
public interface SkipLinkDisclaimerDelegate {

    void skipLinkDisclaimerRetrieved(boolean skipLinkDisclaimer);

}
