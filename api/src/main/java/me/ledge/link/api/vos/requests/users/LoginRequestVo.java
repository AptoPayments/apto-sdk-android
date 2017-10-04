package me.ledge.link.api.vos.requests.users;

import me.ledge.link.api.vos.datapoints.VerificationVo;
import me.ledge.link.api.vos.requests.base.ListRequestVo;

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
