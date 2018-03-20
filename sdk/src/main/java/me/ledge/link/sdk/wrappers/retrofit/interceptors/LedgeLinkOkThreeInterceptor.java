package me.ledge.link.sdk.wrappers.retrofit.interceptors;


import java.io.IOException;

import me.ledge.link.sdk.api.utils.LedgeLinkHeaders;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An OkHttp3 {@link Interceptor} that will add all required headers for API calls.
 * @author Wijnand
 */
public class LedgeLinkOkThreeInterceptor implements Interceptor {

    private String mDevice;
    private String mBearerToken;
    private String mDeveloperKey;
    private String mProjectToken;

    /**
     * Creates a new {@link LedgeLinkOkThreeInterceptor} instance.
     * @param device Device information.
     */
    public LedgeLinkOkThreeInterceptor(String device, String developerKey, String projectToken) {
        mDevice = device;
        mDeveloperKey = developerKey;
        mProjectToken = projectToken;
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
                .header(LedgeLinkHeaders.API_VERSION_HEADER_NAME, LedgeLinkHeaders.API_VERSION_VALUE);

        if (hasHeaderValue(mDevice)) {
            builder.header(LedgeLinkHeaders.DEVICE_HEADER_NAME, mDevice);
        }
        if (hasHeaderValue(mBearerToken)) {
            builder.header(LedgeLinkHeaders.BEARER_TOKEN_HEADER_NAME, mBearerToken);
        }
        if (hasHeaderValue(mDeveloperKey)) {
            builder.header(LedgeLinkHeaders.DEV_KEY_HEADER_NAME, mDeveloperKey);
        }
        if (hasHeaderValue(mProjectToken)) {
            builder.header(LedgeLinkHeaders.PROJECT_TOKEN_HEADER_NAME, mProjectToken);
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

    public void setDeveloperKey(String devKey) {
        mDeveloperKey = devKey;
    }

    public void setProjectToken(String projectToken) {
        mProjectToken = projectToken;
    }
}
