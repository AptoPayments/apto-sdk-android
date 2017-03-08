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
}
