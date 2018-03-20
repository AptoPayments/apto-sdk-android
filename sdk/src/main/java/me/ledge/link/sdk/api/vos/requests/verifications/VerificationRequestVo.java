package me.ledge.link.sdk.api.vos.requests.verifications;

/**
 * Request data to complete the user's verification.
 * @author Adrian
 */
public class VerificationRequestVo extends me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo {
    /**
     * Secret input from user
     */
    public String secret;
}
