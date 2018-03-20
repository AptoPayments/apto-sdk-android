package me.ledge.link.sdk.api.vos.requests.users;

import me.ledge.link.sdk.api.vos.datapoints.VerificationVo;

/**
 * Request data for the login API call.
 * @author Adrian
 */
public class LoginRequestVo {
    public me.ledge.link.sdk.api.vos.requests.base.ListRequestVo<VerificationVo[]> verifications;

    public LoginRequestVo(me.ledge.link.sdk.api.vos.requests.base.ListRequestVo<VerificationVo[]> verifications) {
        this.verifications = verifications;
    }
}
