package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

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
}
