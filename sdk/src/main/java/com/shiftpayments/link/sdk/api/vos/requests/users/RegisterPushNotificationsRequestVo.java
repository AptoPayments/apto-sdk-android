package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.RegisterPushNotificationsTask;

/**
 * Request data to register a device for push notifications.
 * @author Adrian
 */
public class RegisterPushNotificationsRequestVo extends UnauthorizedRequestVo {

    @SerializedName("device_type")
    public String deviceType;

    @SerializedName("push_token")
    public String pushToken;

    public RegisterPushNotificationsRequestVo(String token) {
        deviceType = "ANDROID";
        pushToken = token;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new RegisterPushNotificationsTask(pushToken, shiftApiWrapper, responseHandler);
    }
}
