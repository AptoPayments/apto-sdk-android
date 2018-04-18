package com.shift.link.sdk.api.vos.responses.users;

/**
 * API response when a user has been created.
 * @author Wijnand
 */
public class BaseUserResponseVo {

    /**
     * Bearer token. Use this token to make any future requests.
     * @see <a href="https://tools.ietf.org/html/rfc6750">RFC 6750</a>
     */
    public String type;
    public String user_id;
    public String user_token;
}
