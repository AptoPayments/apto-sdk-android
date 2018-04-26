package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to delete a user.
 * @author Adrian
 */
public class DeleteUserRequestVo extends UnauthorizedRequestVo {

    public int country_code;

    public String phone_number;
}
