package com.shiftpayments.link.sdk.sdk.tasks.users;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.users.RegisterPushNotificationsRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.PushNotificationRegistrationResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to register a device for push notifications
 * @author Adrian
 */
public class RegisterPushNotificationsTask extends ShiftApiTask<Void, Void, PushNotificationRegistrationResponseVo, String> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public RegisterPushNotificationsTask(String requestData, ShiftApiWrapper apiWrapper,
                                         ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected PushNotificationRegistrationResponseVo callApi() throws ApiException {
        RegisterPushNotificationsRequestVo request = new RegisterPushNotificationsRequestVo(getRequestData());
        return getApiWrapper().registerNotificationsToken(request);
    }
}
