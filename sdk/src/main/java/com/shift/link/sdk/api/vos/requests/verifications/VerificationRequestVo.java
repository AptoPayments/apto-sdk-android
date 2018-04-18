package com.shift.link.sdk.api.vos.requests.verifications;

import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

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
