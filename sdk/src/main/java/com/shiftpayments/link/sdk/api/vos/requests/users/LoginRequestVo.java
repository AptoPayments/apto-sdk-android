package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;

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
