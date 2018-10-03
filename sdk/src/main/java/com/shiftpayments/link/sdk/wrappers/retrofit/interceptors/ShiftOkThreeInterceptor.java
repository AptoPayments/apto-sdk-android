package com.shiftpayments.link.sdk.wrappers.retrofit.interceptors;


import com.shiftpayments.link.sdk.api.utils.ShiftApiHeaders;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An OkHttp3 {@link Interceptor} that will add all required headers for API calls.
 * @author Wijnand
 */
public class ShiftOkThreeInterceptor implements Interceptor {

    private String mDevice;
    private String mBearerToken;
    private String mApiKey;

    /**
     * Creates a new {@link ShiftOkThreeInterceptor} instance.
     * @param device Device information.
     */
    public ShiftOkThreeInterceptor(String device, String apiKey) {
        mDevice = device;
        mApiKey = apiKey;
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mBearerToken = null;
    }

    /**
     * @param value The value to check.
     * @return Whether the passed in value is not empty.
     */
    private boolean hasHeaderValue(String value) {
        return value != null && !"".equals(value.trim());
    }

    /** {@inheritDoc} */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header(ShiftApiHeaders.API_VERSION_HEADER_NAME, ShiftApiHeaders.API_VERSION_VALUE);

        if (hasHeaderValue(mDevice)) {
            builder.header(ShiftApiHeaders.DEVICE_HEADER_NAME, mDevice);
        }
        if (hasHeaderValue(mBearerToken)) {
            builder.header(ShiftApiHeaders.BEARER_TOKEN_HEADER_NAME, mBearerToken);
        }
        if (hasHeaderValue(mApiKey)) {
            builder.header(ShiftApiHeaders.API_KEY_HEADER_NAME, mApiKey);
        }

        return chain.proceed(builder.build());
    }

    /**
     * Stores a new bearer token.
     * @param token Bearer token.
     */
    public void setBearerToken(String token) {
        mBearerToken = token;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }
}
