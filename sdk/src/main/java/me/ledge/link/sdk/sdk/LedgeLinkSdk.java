package me.ledge.link.sdk.sdk;

import android.os.AsyncTask;

import java.util.concurrent.Executor;

import me.ledge.link.sdk.api.vos.Card;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.requests.base.ListRequestVo;
import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import me.ledge.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.sdk.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import me.ledge.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.config.HousingTypeListTask;
import me.ledge.link.sdk.sdk.tasks.config.IncomeTypesListTask;
import me.ledge.link.sdk.sdk.tasks.config.LinkConfigTask;
import me.ledge.link.sdk.sdk.tasks.config.SalaryFrequenciesListTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.AddBankAccountTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.AddCardTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountFundingSourceTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountTransactionsTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountsTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.GetUserFundingSourcesTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.IssueVirtualCardTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.UpdateFinancialAccountPinTask;
import me.ledge.link.sdk.sdk.tasks.financialaccounts.UpdateFinancialAccountTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.loanapplication.CreateLoanApplicationTask;
import me.ledge.link.sdk.sdk.tasks.loanapplication.GetLoanApplicationStatusTask;
import me.ledge.link.sdk.sdk.tasks.loanapplication.ListPendingLoanApplicationsTask;
import me.ledge.link.sdk.sdk.tasks.loanapplication.SetApplicationAccountTask;
import me.ledge.link.sdk.sdk.tasks.offers.InitialOffersTask;
import me.ledge.link.sdk.sdk.tasks.users.CreateUserTask;
import me.ledge.link.sdk.sdk.tasks.users.GetCurrentUserTask;
import me.ledge.link.sdk.sdk.tasks.users.LoginUserTask;
import me.ledge.link.sdk.sdk.tasks.users.UpdateUserTask;
import me.ledge.link.sdk.sdk.tasks.verifications.CompleteVerificationTask;
import me.ledge.link.sdk.sdk.tasks.verifications.GetVerificationStatusTask;
import me.ledge.link.sdk.sdk.tasks.verifications.RestartVerificationTask;
import me.ledge.link.sdk.sdk.tasks.verifications.StartVerificationTask;

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
    public static LedgeLinkApiTask loginUser(LoginRequestVo data) {
        checkComponents();

        LoginUserTask task = new LoginUserTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the current user info.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getCurrentUser(boolean throwSessionExpiredError) {
        checkComponents();

        GetCurrentUserTask task = new GetCurrentUserTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler(), throwSessionExpiredError);
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
    public static LedgeLinkApiTask getPendingLoanApplicationsList(ListRequestVo data) {
        checkComponents();

        ListPendingLoanApplicationsTask task = new ListPendingLoanApplicationsTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Fetches the current status of the application.
     * @param applicationId Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getApplicationStatus(String applicationId) {
        checkComponents();

        GetLoanApplicationStatusTask task = new GetLoanApplicationStatusTask(applicationId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Links a financial account to the an application.
     * @param applicationId Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask setApplicationAccount(ApplicationAccountRequestVo data, String applicationId) {
        checkComponents();

        SetApplicationAccountTask task = new SetApplicationAccountTask(data, applicationId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }


    /**
     * Starts the phone verification process.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask startVerification(StartVerificationRequestVo data) {
        checkComponents();

        StartVerificationTask task = new StartVerificationTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Completes the verification process.
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask completeVerification(VerificationRequestVo data, String verificationId) {
        checkComponents();

        CompleteVerificationTask task = new CompleteVerificationTask(data, verificationId, getApiWrapper(), getResponseHandler());
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
     * Restart the verification for the given verification ID
     * @param data Mandatory API request data.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask restartVerification(String data) {
        checkComponents();

        RestartVerificationTask task = new RestartVerificationTask(data, getApiWrapper(), getResponseHandler());
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

    /**
     * Gets the user's financial accounts.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getFinancialAccount(String data) {
        checkComponents();

        GetFinancialAccountTask task = new GetFinancialAccountTask(data, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Update financial account.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask updateFinancialAccount(UpdateFinancialAccountRequestVo data, String accountId) {
        checkComponents();

        UpdateFinancialAccountTask task = new UpdateFinancialAccountTask(data, accountId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Update pin of financial account.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask updateFinancialAccountPin(UpdateFinancialAccountPinRequestVo data, String accountId) {
        checkComponents();

        UpdateFinancialAccountPinTask task = new UpdateFinancialAccountPinTask(data, accountId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the financial account's transactions.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getFinancialAccountTransactions(String accountId, int rows, String lastTransactionId) {
        checkComponents();

        GetFinancialAccountTransactionsTask task = new GetFinancialAccountTransactionsTask(accountId, rows, lastTransactionId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the financial account's funding source.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getFinancialAccountFundingSource(String accountId) {
        checkComponents();

        GetFinancialAccountFundingSourceTask task = new GetFinancialAccountFundingSourceTask(accountId, getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }

    /**
     * Gets the financial account's funding source.
     * @return The {@link LedgeLinkApiTask} that is being executed.
     */
    public static LedgeLinkApiTask getUserFundingSources() {
        checkComponents();

        GetUserFundingSourcesTask task = new GetUserFundingSourcesTask(new UnauthorizedRequestVo(), getApiWrapper(), getResponseHandler());
        task.executeOnExecutor(getExecutor());

        return task;
    }
}
