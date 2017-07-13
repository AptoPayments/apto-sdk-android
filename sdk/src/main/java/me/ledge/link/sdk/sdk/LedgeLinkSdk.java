package me.ledge.link.sdk.sdk;

import android.os.AsyncTask;

import java.util.concurrent.Executor;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.verifications.EmailVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.PhoneVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.config.IncomeTypesListTask;
import me.ledge.link.sdk.sdk.tasks.config.HousingTypeListTask;
import me.ledge.link.sdk.sdk.tasks.config.LinkConfigTask;
import me.ledge.link.sdk.sdk.tasks.config.SalaryFrequenciesListTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.AddBankAccountTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.AddCardTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountsTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.IssueVirtualCardTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.loanapplication.CreateLoanApplicationTask;
import me.ledge.link.sdk.sdk.tasks.loanapplication.ListLoanApplicationsTask;
import me.ledge.link.sdk.sdk.tasks.offers.InitialOffersTask;
import me.ledge.link.sdk.sdk.tasks.users.CreateUserTask;
import me.ledge.link.sdk.sdk.tasks.users.GetCurrentUserTask;
import me.ledge.link.sdk.sdk.tasks.users.LoginUserTask;
import me.ledge.link.sdk.sdk.tasks.users.UpdateUserTask;
import me.ledge.link.sdk.sdk.tasks.verifications.CompletePhoneVerificationTask;
import me.ledge.link.sdk.sdk.tasks.verifications.GetVerificationStatusTask;
import me.ledge.link.sdk.sdk.tasks.verifications.StartEmailVerificationTask;
import me.ledge.link.sdk.sdk.tasks.verifications.StartPhoneVerificationTask;

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
    public static LedgeLinkApiTask getLinkConfig() {
        checkComponents();

        LinkConfigTask task
                = new LinkConfigTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the housing type list.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getHousingTypeList() {
        checkComponents();

        HousingTypeListTask task
                = new HousingTypeListTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the employment statuses list.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getIncomeTypesList() {
        checkComponents();

        IncomeTypesListTask task
                = new IncomeTypesListTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the salary frequencies list.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getSalaryFrequenciesList() {
        checkComponents();

        SalaryFrequenciesListTask task
                = new SalaryFrequenciesListTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask createUser(DataPointList data) {
        checkComponents();

        CreateUserTask task = new CreateUserTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Updates an existing user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask updateUser(DataPointList data) {
        checkComponents();

        UpdateUserTask task = new UpdateUserTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask loginUser(DataPointList data) {
        checkComponents();

        LoginUserTask task = new LoginUserTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the current user info.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getCurrentUser() {
        checkComponents();

        GetCurrentUserTask task = new GetCurrentUserTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }


    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getInitialOffers(InitialOffersRequestVo data) {
        checkComponents();

        InitialOffersTask task = new InitialOffersTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Creates a new loan application.
     * @param offerId The loan offer to apply to.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask createLoanApplication(String offerId) {
        checkComponents();

        CreateLoanApplicationTask task = new CreateLoanApplicationTask(offerId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Fetches the list of open loan applications.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getLoanApplicationsList(ListRequestVo data) {
        checkComponents();

        ListLoanApplicationsTask task = new ListLoanApplicationsTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Starts the phone verification process.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask startPhoneVerification(PhoneVerificationRequestVo data) {
        checkComponents();

        StartPhoneVerificationTask task = new StartPhoneVerificationTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Completes the phone verification process.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask completePhoneVerification(VerificationRequestVo data) {
        checkComponents();

        CompletePhoneVerificationTask task = new CompletePhoneVerificationTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Starts the email verification process.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask startEmailVerification(EmailVerificationRequestVo data) {
        checkComponents();

        StartEmailVerificationTask task = new StartEmailVerificationTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Retrieve the current verification status.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getVerificationStatus(String data) {
        checkComponents();

        GetVerificationStatusTask task = new GetVerificationStatusTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Add a bank account
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask addBankAccount(AddBankAccountRequestVo data) {
        checkComponents();

        AddBankAccountTask task = new AddBankAccountTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Add a credit/debit card
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask addCard(Card data) {
        checkComponents();

        AddCardTask task = new AddCardTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Issue a new virtual card
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask issueVirtualCard(IssueVirtualCardRequestVo data) {
        checkComponents();

        IssueVirtualCardTask task = new IssueVirtualCardTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the user's financial accounts.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getFinancialAccounts() {
        checkComponents();

        GetFinancialAccountsTask task = new GetFinancialAccountsTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

}
