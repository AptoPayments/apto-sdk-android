package me.ledge.link.api.wrappers;

/**
 * Partial implementation of the {@link LinkApiWrapper} interface.
 * @author Wijnand
 */
public abstract class BaseLinkApiWrapper implements LinkApiWrapper {

    private String mDeveloperKey;
    private String mDevice;
    private String mBearerToken;
    private String mProjectToken;

    private String mApiEndPoint;

    /**
     * Creates a new {@link BaseLinkApiWrapper} instance.
     */
    public BaseLinkApiWrapper() {
        init();
    }

    /**
     * Initializes this class.
     */
    protected void init() {
        mDeveloperKey = null;
        mDevice = null;
        mProjectToken = null;
        mApiEndPoint = LinkApiWrapper.API_END_POINT;
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
}
