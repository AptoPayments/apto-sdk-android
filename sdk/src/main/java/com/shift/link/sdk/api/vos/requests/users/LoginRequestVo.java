package com.shift.link.sdk.api.vos.requests.users;

import com.shift.link.sdk.api.vos.datapoints.VerificationVo;

import com.shift.link.sdk.api.vos.datapoints.VerificationVo;
import com.shift.link.sdk.api.vos.requests.base.ListRequestVo;

/**
 * Request data for the login API call.
 * @author Adrian
 */
public class LoginRequestVo {
    public ListRequestVo<VerificationVo[]> verifications;

    public LoginRequestVo(ListRequestVo<VerificationVo[]> verifications) {
        this.verifications = verifications;
    }
}
