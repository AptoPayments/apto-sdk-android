package com.shiftpayments.link.sdk.api.wrappers;

import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.utils.NetworkCallback;
import com.shiftpayments.link.sdk.api.utils.NetworkDelegate;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Partial implementation of the {@link ShiftApiWrapper} interface.
 * @author Wijnand
 */
public abstract class BaseShiftApiWrapper implements ShiftApiWrapper, NetworkDelegate {

    public LinkedBlockingQueue<ShiftApiTask> pendingApiCalls;
    private boolean mIsConnectedToInternet;
    private NetworkCallback mOnNoInternetConnection;

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
        mIsConnectedToInternet = true;
        pendingApiCalls = new LinkedBlockingQueue<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeveloperKey() {
        return mDeveloperKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeveloperKey(String developerKey) {
        mDeveloperKey = developerKey;
    }

    /**
     * @return Device information.
     */
    public String getDeviceInfo() {
        return mDevice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseRequestData(String developerKey, String device, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        mDeveloperKey = developerKey;
        mDevice = device;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBearerToken() {
        return mBearerToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBearerToken(String token) {
        mBearerToken = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProjectToken() {
        return mProjectToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectToken(String token) {
        mProjectToken = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApiEndPoint() {
        return mApiEndPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApiEndPoint(String endPoint, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        mApiEndPoint = endPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVgsEndPoint() {
        return mVgsEndPoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVgsEndPoint(String endPoint) {
        mVgsEndPoint = endPoint;
    }

    @Override
    public boolean isConnectedToInternet() {
        return mIsConnectedToInternet;
    }

    @Override
    public void onNetworkStatusChanged(boolean isConnected) {
        mIsConnectedToInternet = isConnected;
        if(mIsConnectedToInternet) {
            executePendingApiCalls();
        }
    }

    @Override
    public void enqueueApiCall(ShiftApiTask task) {
        pendingApiCalls.add(task);
    }

    @Override
    public Executor getExecutor() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    @Override
    public void setOnNoInternetConnectionCallback(NetworkCallback onNoInternetConnection) {
        mOnNoInternetConnection = onNoInternetConnection;
    }

    @Override
    public NetworkCallback getOnNoInternetConnectionCallback() {
        return mOnNoInternetConnection;
    }

    private void executePendingApiCalls() {
        for(ShiftApiTask call : pendingApiCalls) {
            call.executeOnExecutor(getExecutor());
            pendingApiCalls.remove(call);
        }
    }
}
