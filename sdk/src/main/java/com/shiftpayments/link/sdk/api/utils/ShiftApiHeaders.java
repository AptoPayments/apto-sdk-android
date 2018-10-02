package com.shiftpayments.link.sdk.api.utils;

/**
 * Default headers used in API requests.
 * TODO: Move to API common project.
 * @author Wijnand
 */
public class ShiftApiHeaders {

    /**
     * Device information. Suggested data:<br />
     *  - Device manufacturer<br />
     *  - Device model name<br />
     *  - Device OS version name.<br />
     * i.e. "[manufacturer] -- [model] -- [os version]".
     */
    public static final String DEVICE_HEADER_NAME = "X-Device";

    /**
     * API version
     */
    public static final String API_VERSION_HEADER_NAME = "X-Api-Version";

    public static final String API_VERSION_VALUE = "1.0";

    /**
     * Bearer token.
     * @see <a href="https://tools.ietf.org/html/rfc6750">RFC 6750</a>
     */
    public static final String BEARER_TOKEN_HEADER_NAME = "Authorization:Bearer";

    public static final String API_KEY_HEADER_NAME = "Api-Key:Bearer";
}
