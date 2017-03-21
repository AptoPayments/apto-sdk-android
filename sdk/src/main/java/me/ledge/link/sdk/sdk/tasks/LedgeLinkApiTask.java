package me.ledge.link.sdk.sdk.tasks;

import android.os.AsyncTask;
import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Generic {@link AsyncTask} to make API requests.
 *
 * @param <Params> The type of the parameters sent to the task upon execution.
 * @param <Progress> The type of the progress units published during the background computation.
 * @param <Result> The type of the result of the background computation.
 * @param <Request> The type of request data sent to the API upon execution.
 */
public abstract class LedgeLinkApiTask<Params, Progress, Result, Request>
        extends AsyncTask<Params, Progress, Result> {

    private final Request mRequestData;
    private final LinkApiWrapper mApiWrapper;
    private final ApiResponseHandler mResponseHandler;

    private boolean mSuccess;
    private ApiErrorVo mError;

    /**
     * Creates a new {@link LedgeLinkApiTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public LedgeLinkApiTask(Request requestData, LinkApiWrapper apiWrapper, ApiResponseHandler responseHandler) {
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
    protected LinkApiWrapper getApiWrapper() {
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
            result = callApi();
            mSuccess = true;
        } catch (ApiException ae) {
            mError = ae.getError();
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
            mResponseHandler.publishResult(mError);
        }
    }
}
