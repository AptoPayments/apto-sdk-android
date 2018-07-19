package com.shiftpayments.link.sdk.sdk;

import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.utils.NetworkCallback;
import com.shiftpayments.link.sdk.api.utils.NetworkDelegate;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.cardapplication.CreateCardApplicationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.cardapplication.GetCardApplicationStatusRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetCardConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.config.GetLinkConfigRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.ActivateFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.DisableFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.EnableFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.GetFinancialAccountFundingSourceRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.GetFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.GetFinancialAccountTransactionsRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.GetFinancialAccountsRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.GetUserFundingSourceRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetBalanceStoreRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetFundingSourceRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.loanapplication.CreateLoanApplicationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.loanapplication.GetLoanApplicationStatusRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.loanapplication.GetPendingLoanApplicationListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.AcceptDisclaimerRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.CreateUserRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.CurrentUserRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.GetOAuthStatusRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.RegisterPushNotificationsRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.StartOAuthRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.UpdateUserRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.GetVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.RestartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.NoConnectionErrorVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

import java.util.concurrent.Executor;

/**
 * Shift Link SDK.<br />
 * <br />
 * Make sure to call {@link #setApiWrapper(ShiftApiWrapper)} and {@link #setResponseHandler(ApiResponseHandler)} before
 * making any API requests.
 *
 * @author Wijnand
 */
public class ShiftLinkSdk {

    private static ShiftApiWrapper mApiWrapper;
    private static Executor mExecutor;
    private static ApiResponseHandler mHandler;
    private static NetworkManager mNetworkManager = new NetworkManager();

    /**
     * Checks if all required components have been set. Will throw {@link NullPointerException}s when a component
     * appears to be missing.
     */
    protected static void checkComponents() {
        if (getApiWrapper() == null) {
            throw new NullPointerException(
                    "Make sure to call 'setApiWrapper(ShiftApiWrapper)' before invoking any API request methods!");
        } else if (getResponseHandler() == null) {
            throw new NullPointerException("Make sure to call 'setResponseHandler(ApiResponseHandler)' before " +
                    "invoking any API request methods!");
        }
    }

    /**
     * @return API wrapper.
     */
    public static ShiftApiWrapper getApiWrapper() {
        return mApiWrapper;
    }

    /**
     * Stores a new {@link ShiftApiWrapper} instance.
     * @param wrapper Api wrapper.
     */
    public static void setApiWrapper(ShiftApiWrapper wrapper) {
        mApiWrapper = wrapper;
        NetworkManager.setApiWrapper(wrapper);
    }

    /**
     * @return {@link AsyncTask} {@link Executor}.
     */
    public static Executor getExecutor() {
        if (mExecutor == null) {
            setExecutor(getApiWrapper().getExecutor());
        }

        return mExecutor;
    }

    /**
     * Stores a new {@link Executor} that will be used to execute any {@link ShiftApiTask}.
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
     * Stores a new {@link ApiResponseHandler} that will be invoked by any {@link ShiftApiTask} to publish its
     * results.
     * @param handler Api response handler.
     */
    public static void setResponseHandler(ApiResponseHandler handler) {
        mHandler = handler;
        NetworkManager.setApiResponseHandler(handler);
    }

    public static NetworkDelegate getNetworkDelegate() {
        return mNetworkManager;
    }

    private static void executeOrEnqueueRequest(UnauthorizedRequestVo request) {
        request.mHandler = getResponseHandler();
        if(NetworkManager.isConnectedToInternet) {
            request.getApiTask(mApiWrapper, mHandler).executeOnExecutor(getExecutor());
        }
        else {
            getApiWrapper().enqueueApiCall(request);
            NetworkCallback callback = getApiWrapper().getOnNoInternetConnectionCallback();
            if(callback != null) {
                callback.onNoInternetConnection();
            }
            else {
                mHandler.publishResult(new NoConnectionErrorVo());
            }
        }
    }

    /**
     * Gets the link config.
     */
    public static void getLinkConfig() {
        checkComponents();
        GetLinkConfigRequestVo request = new GetLinkConfigRequestVo();
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the card config.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getCardConfig() {
        checkComponents();
        GetCardConfigRequestVo request = new GetCardConfigRequestVo();
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the housing type list.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getHousingTypeList() {
        checkComponents();
        //TODO
    }

    /**
     * Gets the employment statuses list.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getIncomeTypesList() {
        checkComponents();
        //TODO
    }

    /**
     * Gets the salary frequencies list.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getSalaryFrequenciesList() {
        checkComponents();
        //TODO
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void createUser(DataPointList data) {
        checkComponents();
        CreateUserRequestVo request = new CreateUserRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Updates an existing user.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void updateUser(DataPointList data) {
        checkComponents();
        UpdateUserRequestVo request = new UpdateUserRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void loginUser(LoginRequestVo data) {
        checkComponents();
        executeOrEnqueueRequest(data);
    }

    /**
     * Gets the current user info.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getCurrentUser(boolean throwSessionExpiredError) {
        checkComponents();
        CurrentUserRequestVo request = new CurrentUserRequestVo(throwSessionExpiredError);
        executeOrEnqueueRequest(request);
    }

    /**
     * Creates a new user.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getInitialOffers(InitialOffersRequestVo data) {
        checkComponents();
        executeOrEnqueueRequest(data);
    }

    /**
     * Creates a new loan application.
     * @param offerId The loan offer to apply to.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void createLoanApplication(String offerId) {
        checkComponents();
        CreateLoanApplicationRequestVo request = new CreateLoanApplicationRequestVo(offerId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Fetches the list of open loan applications.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getPendingLoanApplicationsList(ListRequestVo data) {
        checkComponents();
        GetPendingLoanApplicationListRequestVo request = new GetPendingLoanApplicationListRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Fetches the current status of the application.
     * @param applicationId Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getLoanApplicationStatus(String applicationId) {
        checkComponents();
        GetLoanApplicationStatusRequestVo request = new GetLoanApplicationStatusRequestVo(applicationId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Links a financial account to the an application.
     * @param applicationId Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void setApplicationAccount(ApplicationAccountRequestVo data, String applicationId) {
        checkComponents();

        // TODO: refactor
        data.applicationId = applicationId;
        executeOrEnqueueRequest(data);
    }

    /**
     * Starts the phone verification process.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void startVerification(StartVerificationRequestVo data) {
        checkComponents();
        data.mHandler = getResponseHandler();
        executeOrEnqueueRequest(data);
    }


    /**
     * Completes the verification process.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void completeVerification(VerificationRequestVo data, String verificationId) {
        checkComponents();

        // TODO: refactor
        data.verificationId = verificationId;
        executeOrEnqueueRequest(data);
    }

    /**
     * Retrieve the current verification status.
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getVerificationStatus(String data) {
        checkComponents();
        GetVerificationRequestVo request = new GetVerificationRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Restart the verification for the given verification ID
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void restartVerification(String data) {
        checkComponents();
        RestartVerificationRequestVo request = new RestartVerificationRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Add a bank account
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void addBankAccount(AddBankAccountRequestVo data) {
        checkComponents();
        executeOrEnqueueRequest(data);
    }

    /**
     * Add a credit/debit card
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void addCard(Card data) {
        checkComponents();
        AddCardRequestVo request = new AddCardRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Issue a new virtual card
     * @param data Mandatory API request data.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void issueVirtualCard(IssueVirtualCardRequestVo data) {
        checkComponents();
        executeOrEnqueueRequest(data);
    }

    /**
     * Gets the user's financial accounts.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getFinancialAccounts() {
        checkComponents();
        GetFinancialAccountsRequestVo request = new GetFinancialAccountsRequestVo();
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the user's financial accounts.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getFinancialAccount(String data) {
        checkComponents();
        GetFinancialAccountRequestVo request = new GetFinancialAccountRequestVo(data);
        executeOrEnqueueRequest(request);
    }

    /**
     * Activate financial account.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void activateFinancialAccount(String accountId) {
        checkComponents();
        ActivateFinancialAccountRequestVo request = new ActivateFinancialAccountRequestVo(accountId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Enable financial account.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void enableFinancialAccount(String accountId) {
        checkComponents();
        EnableFinancialAccountRequestVo request = new EnableFinancialAccountRequestVo(accountId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Disable financial account.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void disableFinancialAccount(String accountId) {
        checkComponents();
        DisableFinancialAccountRequestVo request = new DisableFinancialAccountRequestVo(accountId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Update pin of financial account.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void updateFinancialAccountPin(UpdateFinancialAccountPinRequestVo data, String accountId) {
        checkComponents();

        // TODO: refactor
        data.accountId = accountId;
        executeOrEnqueueRequest(data);
    }

    /**
     * Gets the financial account's transactions.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getFinancialAccountTransactions(String accountId, int rows, String lastTransactionId) {
        checkComponents();
        GetFinancialAccountTransactionsRequestVo request = new GetFinancialAccountTransactionsRequestVo(accountId, rows, lastTransactionId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the financial account's funding source.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getFinancialAccountFundingSource(String accountId) {
        checkComponents();
        GetFinancialAccountFundingSourceRequestVo request = new GetFinancialAccountFundingSourceRequestVo(accountId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the financial account's funding source.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getUserFundingSources() {
        checkComponents();
        GetUserFundingSourceRequestVo request = new GetUserFundingSourceRequestVo();
        executeOrEnqueueRequest(request);
    }

    /**
     * Sets the financial account's funding source.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void setAccountFundingSource(String accountId, String fundingSourceId) {
        checkComponents();

        SetFundingSourceRequestVo request = new SetFundingSourceRequestVo(fundingSourceId);
        // TODO: refactor
        request.accountId = accountId;
        
        executeOrEnqueueRequest(request);
    }

    /**
     * Registers the device to receive push notifications
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void registerPushNotificationToken(String token) {
        checkComponents();
        RegisterPushNotificationsRequestVo request = new RegisterPushNotificationsRequestVo(token);
        executeOrEnqueueRequest(request);
    }

    /**
     * Requests a URL to start the OAuth for the given provider
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void startOAuth(String provider) {
        checkComponents();
        StartOAuthRequestVo request = new StartOAuthRequestVo(provider);
        executeOrEnqueueRequest(request);
    }

    /**
     * Requests the OAuth status
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getOAuthStatus(String id) {
        checkComponents();
        GetOAuthStatusRequestVo request = new GetOAuthStatusRequestVo(id);
        executeOrEnqueueRequest(request);
    }

    /**
     * Creates a new card application.
     * @param cardProductId The card product ID.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void createCardApplication(String cardProductId) {
        checkComponents();
        CreateCardApplicationRequestVo request = new CreateCardApplicationRequestVo(cardProductId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Gets the card application status.
     * @param applicationId The card product ID.
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void getCardApplicationStatus(String applicationId) {
        checkComponents();
        GetCardApplicationStatusRequestVo request = new GetCardApplicationStatusRequestVo(applicationId);
        executeOrEnqueueRequest(request);
    }

    /**
     * Sets the balance store data
     * @param request The custodian
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void setBalanceStore(String applicationId, SetBalanceStoreRequestVo request) {
        checkComponents();

        // TODO: refactor
        request.applicationId = applicationId;
        executeOrEnqueueRequest(request);
    }

    /**
     * Request to be sent when the user accepts the disclaimer
     * @param request The application ID, Workflow ID and Action ID
     * @return The {@link ShiftApiTask} that is being executed.
     */
    public static void acceptDisclaimer(AcceptDisclaimerRequestVo request) {
        checkComponents();
        executeOrEnqueueRequest(request);
    }
}
