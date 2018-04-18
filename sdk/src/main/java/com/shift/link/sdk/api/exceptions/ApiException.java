package com.shift.link.sdk.api.exceptions;

import com.shift.link.sdk.api.vos.responses.ApiErrorVo;

/**
 * API exception, thrown when an API request has failed.
 * @author Wijnand
 */
public class ApiException extends Exception {

    private final ApiErrorVo mError;

    /**
     * Creates a new {@link ApiException} instance.
     * @param error API error details.
     * @param message Detail message.
     */
    public ApiException(ApiErrorVo error, String message) {
        super(message);
        mError = error;
    }

    /**
     * Creates a new {@link ApiException} instance.
     * @param error API error details.
     * @param message Detail message.
     * @param cause The cause of this exception.
     */
    public ApiException(ApiErrorVo error, String message, Throwable cause) {
        super(message, cause);
        mError = error;
    }

    /**
     * @return API error details.
     */
    public ApiErrorVo getError() {
        return mError;
    }

}
