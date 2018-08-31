package com.shiftpayments.link.sdk.sdk;

import com.shiftpayments.link.sdk.api.utils.NetworkDelegate;
import com.shiftpayments.link.sdk.api.vos.responses.ConnectionEstablishedVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class NetworkManager implements NetworkDelegate {

    public static boolean isConnectedToInternet = true;
    private static ShiftApiWrapper mApiWrapper;
    private static ApiResponseHandler mHandler;

    @Override
    public void onNetworkStatusChanged(boolean isConnected) {
        isConnectedToInternet = isConnected;
        if(isConnectedToInternet && mHandler!=null) {
            mHandler.publishResult(new ConnectionEstablishedVo());
            mApiWrapper.executePendingApiCalls();
        }
    }

    public static void setApiWrapper(ShiftApiWrapper apiWrapper) {
        mApiWrapper = apiWrapper;
    }

    public static void setApiResponseHandler(ApiResponseHandler handler) {
        mHandler = handler;
    }
}
