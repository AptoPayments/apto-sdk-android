package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to start oAuth.
 * @author Adrian
 */
public class StartOAuthRequestVo extends UnauthorizedRequestVo {
    public String provider;

    public StartOAuthRequestVo(String provider) {
        this.provider = provider;
    }
}
