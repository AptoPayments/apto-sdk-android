package me.ledge.link.api.vos.requests.verifications;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to verify the user's phone.
 * @author Adrian
 */
public class PhoneVerificationRequestVo extends UnauthorizedRequestVo {
    /**
     * US phone number, formatted <a href="https://en.wikipedia.org/wiki/E.164">E.164</a>.
     */
    public String phone_number;

    /**
     * Country code.
     */
    public int country_code;

    /**
     * Returns verification secret in response (for testing purposes)
     */
    public boolean show_verification_secret;
}
