package me.ledge.link.sdk.sdk;

import android.os.AsyncTask;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.users.CreateUserRequestVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.loanapplication.CreateLoanApplicationTask;
import me.ledge.link.sdk.sdk.tasks.offers.InitialOffersTask;
import me.ledge.link.sdk.sdk.tasks.users.CreateUserTask;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.config.LoanPurposesTask;

import java.util.concurrent.Executor;

/**
 * Ledge Link SDK.<br />
 * <br />
 * Make sure to call {@link #setApiWrapper(LinkApiWrapper)} and {@link #setResponseHandler(ApiResponseHandler)} before
 * making any API requests.
 *
 * @author Wijnand
 */
public class LedgeLinkSdk {

    private static LinkApiWrapper mApiWrapper;
    private static Executor mExecutor;
    private static ApiResponseHandler mHandler;

    /**
     * Checks if all required components have been set. Will throw {@link NullPointerException}s when a component
     * appears to be missing.
     */
    protected static void checkComponents() {
        if (getApiWrapper() == null) {
            throw new NullPointerException(
                    "Make sure to call 'setApiWrapper(LinkApiWrapper)' before invoking any API request methods!");
        } else if (getResponseHandler() == null) {
            throw new NullPointerException("Make sure to call 'setResponseHandler(ApiResponseHandler)' before " +
                    "invoking any API request methods!");
        }
    }

    /**
     * @return API wrapper.
     */
    public static LinkApiWrapper getApiWrapper() {
        return mApiWrapper;
    }

    /**
     * Stores a new {@link LinkApiWrapper} instance.
     * @param wrapper Api wrapper.
     */
    public static void setApiWrapper(LinkApiWrapper wrapper) {
        mApiWrapper = wrapper;
    }

    /**
     * @return {@link AsyncTask} {@link Executor}.
     */
    public static Executor getExecutor() {
        if (mExecutor == null) {
            setExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        return mExecutor;
    }

    /**
     * Stores a new {@link Executor} that will be used to execute any {@link LedgeLinkApiTask}.
     * @param executor {@link AsyncTask} {@link Executor}.
     */
    public static void setExecutor(Executor executor) {
        mExecutor = executor;
    }

    /**
     * @return Api response handler.
     */
    public static ApiResponseHandler getResponseHandler() {
        return mHandler;
    }

    /**
     * Stores a new {@link ApiResponseHandler} that will be invoked by any {@link LedgeLinkApiTask} to publish its
     * results.
     * @param handler Api response handler.
     */
    public static void setResponseHandler(ApiResponseHandler handler) {
        mHandler = handler;
    }

    /**
     * Gets the loan purposes list.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getLoanPurposesList() {
        checkComponents();

        LoanPurposesTask task
                = new LoanPurposesTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        return (LoanPurposesTask) task.executeOnExecutor(getExecutor());
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask createUser(CreateUserRequestVo data) {
        checkComponents();

        CreateUserTask task = new CreateUserTask(data, getApiWrapper(), getResponseHandler());
        return (CreateUserTask) task.executeOnExecutor(getExecutor());
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getInitialOffers(InitialOffersRequestVo data) {
        checkComponents();

        InitialOffersTask task = new InitialOffersTask(data, getApiWrapper(), getResponseHandler());
        return (InitialOffersTask) task.executeOnExecutor(getExecutor());
    }

    /**
     * Creates a new loan application.
     * @param offerId The loan offer to apply to.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask createLoanApplication(long offerId) {
        checkComponents();

        CreateLoanApplicationTask task = new CreateLoanApplicationTask(offerId, getApiWrapper(), getResponseHandler());
        return (CreateLoanApplicationTask) task.executeOnExecutor(getExecutor());
    }

}
