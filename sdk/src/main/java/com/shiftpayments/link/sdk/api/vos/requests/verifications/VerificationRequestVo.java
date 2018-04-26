package com.shiftpayments.link.sdk.api.vos.requests.verifications;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

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
