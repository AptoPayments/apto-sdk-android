package me.ledge.link.api.wrappers;

import java.util.HashMap;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.verifications.StartVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.api.vos.responses.config.ContextConfigResponseVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.api.vos.responses.offers.OffersListVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.CurrentUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserDataListResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.vos.responses.verifications.FinishPhoneVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartPhoneVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;

/**
 * Ledge Link API Wrapper.
 * @author Wijnand
 */
public interface LinkApiWrapper {

    String SHA_256_PREFIX = "sha256/";
    String SSL_FINGERPRINT_ONE = "k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=";
    String SSL_FINGERPRINT_TWO = "5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w=";
    String SSL_FINGERPRINT_THREE = "emrYgpjLplsXa6OnqyXuj5BgQDPaapisB5WfVm+jrFQ=";

    String API_END_POINT = "https://link.ledge.me";
    String SSL_API_HOST = "*.link.ledge.me";
    String FORWARD_SLASH = "/";

    String LINK_CONFIG_PATH = "v1/config/link";
    String CONFIG_PATH = "v1/config";

    String CREATE_USER_PATH = "v1/user";
    String UPDATE_USER_PATH = "v1/user";
    String GET_CURRENT_USER_PATH = "v1/user";
    String LOGIN_USER_PATH = "v1/user/login";

    String INITIAL_OFFERS_PATH = "v1/link/offersrequest";
    String MORE_OFFERS_PATH = "v1/link/offersrequest/{offer_request_id}/offers";

    String CREATE_LOAN_APPLICATION_PATH = "v1/link/offers/{offer_id}/apply";
    String LIST_LOAN_APPLICATIONS_PATH = "v1/link/offers/";

    String VERIFICATION_START_PATH = "v1/verifications/start";
    String VERIFICATION_STATUS_PATH = "v1/verifications/{ID}/status";
    String VERIFICATION_FINISH_PATH = "v1/verifications/{ID}/finish";

    String FINANCIAL_ACCOUNTS_PATH = "v1/user/financialaccounts";
    String PLAID_WEB_URL = "v1/bankoauth";

    int OFFERS_REQUEST_TIMEOUT = 150; // 2.5 minutes.

    /**
     * Stores the basic request data.
     * @param developerKey Public developer key.
     * @param device Device information, preferably including: manufacturer, model name and Android SDK version.
     * @param isCertificatePinningEnabled should certificate pinning be enabled
     * @param trustSelfSignedCerts should self signed certificates be trusted
     */
    void setBaseRequestData(String developerKey, String device, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts);

    /**
     * Stores a new bearer token.
     * @param token Bearer token.
     */
    void setBearerToken(String token);

    /**
     * @return Bearer key.
     */
    String getBearerToken();

    /**
     * @return Developer key.
     */
    String getDeveloperKey();

    /**
     * Stores a new developer key.
     * @param key Developer key.
     */
    void setDeveloperKey(String key);

    /**
     * Stores a new project token.
     * @param token Project token.
     */
    void setProjectToken(String token);

    /**
     * @return Project token.
     */
    String getProjectToken();

    /**
     * @return API endpoint.
     */
    String getApiEndPoint();

    public HashMap<String, String> getHTTPHeaders();

    /**
     * Stores a new API end point.<br />
     * <em>Use with care! Should be used for testing only!</em>
     * @param endPoint New end point.
     * @param isCertificatePinningEnabled should certificate pinning be enabled
     * @param trustSelfSignedCerts should self signed certificates be trusted
     */
    void setApiEndPoint(String endPoint, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts);

    /**
     * Gets the Link config which includes: disclaimers, a list of loan purposes, etc.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    LinkConfigResponseVo getLinkConfig(UnauthorizedRequestVo requestData) throws ApiException;

    /**
     * @param requestData Mandatory request data.
     * @return Team configuration and project configuration (list of housing types, salary frequencies and employment statuses)
     * @throws ApiException When there is an error making the request.
     */
    ContextConfigResponseVo getUserConfig(UnauthorizedRequestVo requestData) throws ApiException;

    /**
     * Asks the API to create a new user.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    CreateUserResponseVo createUser(DataPointList requestData) throws ApiException;

    /**
     * Updates an existing user.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    UserResponseVo updateUser(DataPointList requestData) throws ApiException;

    /**
     * Asks the API to recover an existing user.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    CreateUserResponseVo loginUser(DataPointList requestData) throws ApiException;

    /**
     * Gets the current user info.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    CurrentUserResponseVo getCurrentUser(UnauthorizedRequestVo requestData) throws ApiException;

    /**
     * Gets the initial list of loan offers.<br />
     * To fetch the next pages, use the {@link #getMoreOffers} method.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    InitialOffersResponseVo getInitialOffers(InitialOffersRequestVo requestData) throws ApiException;

    /**
     * Gets the next list of loan offers.
     * @param offerRequestId Offer request ID.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    OffersListVo getMoreOffers(String offerRequestId, ListRequestVo requestData) throws ApiException;

    /**
     * Creates a new loan application.
     * @param offerId The offer to apply to.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    LoanApplicationDetailsResponseVo createLoanApplication(String offerId) throws ApiException;

    /**
     * Gets the user's open loan applications.
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    LoanApplicationsListResponseVo getLoanApplicationsList(ListRequestVo requestData)
            throws ApiException;

    /**
     * Starts the process to verify a user's phone number.
     * @param requestData The phone number and country code.
     * @return A verification object with its verification ID.
     * @throws ApiException When there is an error making the request.
     */
    StartPhoneVerificationResponseVo startPhoneVerification(StartVerificationRequestVo requestData)
            throws ApiException;

    /**
     * Completes the process to verify a user's phone number.
     * @param requestData The secret inputted by the user ID.
     * @param verificationID The verification ID.
     * @return A verification object with the corresponding status.
     * @throws ApiException When there is an error making the request.
     */
    FinishPhoneVerificationResponseVo completePhoneVerification(VerificationRequestVo requestData, String verificationID)
            throws ApiException;

    /**
     * Starts the process to verify a user's email.
     * @param requestData The email address.
     * @return A verification object with its verification ID.
     * @throws ApiException When there is an error making the request.
     */
    StartEmailVerificationResponseVo startEmailVerification(StartVerificationRequestVo requestData)
            throws ApiException;

    /**
     * @param verificationID The verification ID to check.
     * @return The verification object with the current status.
     * @throws ApiException
     */
    VerificationStatusResponseVo getVerificationStatus(String verificationID) throws ApiException;

    /**
     * @param requestData The tokenized bank account.
     * @return The verification object with the current status.
     * @throws ApiException
     */
    VerificationStatusResponseVo addBankAccount(AddBankAccountRequestVo requestData) throws ApiException;

    /**
     * @param requestData The card DataPoint.
     * @return The same DataPoint with the accountId filled in.
     * @throws ApiException
     */
    Card addCard(Card requestData) throws ApiException;

    /**
     * @param requestData The user's phone and the amount
     * @return The virtual card
     * @throws ApiException
     */
    Card issueVirtualCard(IssueVirtualCardRequestVo requestData) throws ApiException;

    /**
     * Gets the user's financial accounts
     * @param requestData Mandatory request data.
     * @return API response.
     * @throws ApiException When there is an error making the request.
     */
    UserDataListResponseVo getFinancialAccounts(UnauthorizedRequestVo requestData) throws ApiException;
}
