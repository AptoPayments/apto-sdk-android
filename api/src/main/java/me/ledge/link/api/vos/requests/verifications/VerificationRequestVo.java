package me.ledge.link.api.vos.requests.verifications;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to complete the user's verification.
 * @author Adrian
 */
public class VerificationRequestVo extends UnauthorizedRequestVo {
    /**
     * Secret input from user
     */
    public String secret;
}
