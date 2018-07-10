package com.shiftpayments.link.sdk.sdk.tasks;

import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Generic {@link AsyncTask} to make API requests.
 *
 * @param <Params> The type of the parameters sent to the task upon execution.
 * @param <Progress> The type of the progress units published during the background computation.
 * @param <Result> The type of the result of the background computation.
 * @param <Request> The type of request data sent to the API upon execution.
 */
public abstract class ShiftApiTask<Params, Progress, Result, Request>
        extends AsyncTask<Params, Progress, Result> {

    private final Request mRequestData;
    private final ShiftApiWrapper mApiWrapper;
    private final ApiResponseHandler mResponseHandler;

    private boolean mSuccess;
    private ApiErrorVo mError;

    /**
     * Creates a new {@link ShiftApiTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public ShiftApiTask(Request requestData, ShiftApiWrapper apiWrapper, ApiResponseHandler responseHandler) {
        super();
        init();

        mRequestData = requestData;
        mApiWrapper = apiWrapper;
        mResponseHandler = responseHandler;
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mSuccess = false;
        mError = null;
    }

    /**
     * @return API request data.
     */
    protected Request getRequestData() {
        return mRequestData;
    }

    /**
     * @return The API wrapper instance to make API calls.
     */
    protected ShiftApiWrapper getApiWrapper() {
        return mApiWrapper;
    }

    /**
     * @return Result from the API call.
     */
    protected abstract Result callApi() throws ApiException;

    /**
     * Makes the API requests.
     * {@inheritDoc}
     */
    @Override
    protected Result doInBackground(Params... params) {
        Result result;

        try {
            if(getApiWrapper().isConnectedToInternet()) {
                result = callApi();
                mSuccess = true;
            }
            else {
                // TODO: throw custom exception
                mSuccess = false;
                mError = new ApiErrorVo();
                mError.serverMessage = "No internet connectivity!";
                result = null;
            }
        } catch (ApiException ae) {
            mError = ae.getError();
            if(mError.serverMessage == null) {
                mError.serverMessage = ae.getMessage();
            }
            mSuccess = false;
            result = null;
        } catch (Exception e) {
            mError = new ApiErrorVo();
            mError.serverMessage = e.getMessage();
            mSuccess = false;
            result = null;
        }

        return result;
    }

    /**
     * Publishes the results.
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if (mSuccess) {
            mResponseHandler.publishResult(result);
        } else {
            if(mError!=null && (mError.isSessionExpired || mError.serverCode==3031)) {
                mResponseHandler.publishResult(new SessionExpiredErrorVo(mError));
            }
            else {
                mResponseHandler.publishResult(mError);
            }
        }
    }
}
