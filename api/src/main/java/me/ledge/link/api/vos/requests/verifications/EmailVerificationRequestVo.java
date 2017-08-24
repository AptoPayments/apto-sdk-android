package me.ledge.link.api.vos.requests.verifications;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to verify the user's e-mail.
 * @author Adrian
 */
public class EmailVerificationRequestVo extends UnauthorizedRequestVo {
    /**
     * E-mail.
     */
    public String email;
    /**
     * Returns verification secret in response (for testing purposes)
     */
    public boolean show_verification_secret;
}
