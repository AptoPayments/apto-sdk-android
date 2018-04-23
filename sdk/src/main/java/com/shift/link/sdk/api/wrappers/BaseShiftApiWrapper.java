package com.shift.link.sdk.api.wrappers;

/**
 * Partial implementation of the {@link ShiftApiWrapper} interface.
 * @author Wijnand
 */
public abstract class BaseShiftApiWrapper implements ShiftApiWrapper {

    private String mDeveloperKey;
    private String mDevice;
    private String mBearerToken;
    private String mProjectToken;

    private String mApiEndPoint;
    private String mVgsEndPoint;

    /**
     * Creates a new {@link BaseShiftApiWrapper} instance.
     */
    public BaseShiftApiWrapper() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mDeveloperKey = null;
        mDevice = null;
        mProjectToken = null;
        mApiEndPoint = null;
    }

    /** {@inheritDoc} */
    @Override
    public String getDeveloperKey() {
        return mDeveloperKey;
    }

    /**
     * @return Device information.
     */
    public String getDeviceInfo() {
        return mDevice;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseRequestData(String developerKey, String device, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        mDeveloperKey = developerKey;
        mDevice = device;
    }

    /** {@inheritDoc} */
    @Override
    public String getBearerToken() {
        return mBearerToken;
    }

    /** {@inheritDoc} */
    @Override
    public void setBearerToken(String token) {
        mBearerToken = token;
    }

    /** {@inheritDoc} */
    @Override
    public void setDeveloperKey(String developerKey) {
        mDeveloperKey = developerKey;
    }

    /** {@inheritDoc} */
    @Override
    public void setProjectToken(String token) {
        mProjectToken = token;
    }

    /** {@inheritDoc} */
    @Override
    public String getProjectToken() {
        return mProjectToken;
    }

    /** {@inheritDoc} */
    @Override
    public String getApiEndPoint() {
        return mApiEndPoint;
    }

    /** {@inheritDoc} */
    @Override
    public void setApiEndPoint(String endPoint, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        mApiEndPoint = endPoint;
    }

    /** {@inheritDoc} */
    @Override
    public String getVgsEndPoint() {
        return mVgsEndPoint;
    }

    /** {@inheritDoc} */
    @Override
    public void setVgsEndPoint(String endPoint) {
        mVgsEndPoint = endPoint;
    }
}
