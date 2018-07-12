package com.shiftpayments.link.sdk.api.vos.responses;

/**
 * Error thrown when phone is not connected to the internet
 * @author Adrian
 */
public class NoConnectionErrorVo extends ApiErrorVo {
    public NoConnectionErrorVo() {
        serverMessage = "No internet connectivity!";
    }
}
