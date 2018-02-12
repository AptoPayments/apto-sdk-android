package me.ledge.link.api.wrappers.retrofit.two;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.utils.VerificationSerializer;
import me.ledge.link.api.utils.parsers.ActionConfigurationParser;
import me.ledge.link.api.utils.parsers.DataPointParser;
import me.ledge.link.api.utils.parsers.FinancialAccountParser;
import me.ledge.link.api.utils.parsers.RequiredDataPointParser;
import me.ledge.link.api.utils.parsers.VirtualCardParser;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.api.vos.datapoints.VerificationVo;
import me.ledge.link.api.vos.datapoints.VirtualCard;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.requests.dashboard.CreateProjectRequestVo;
import me.ledge.link.api.vos.requests.dashboard.CreateTeamRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.requests.users.DeleteUserRequestVo;
import me.ledge.link.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.api.vos.requests.verifications.StartVerificationRequestVo;
import me.ledge.link.api.vos.requests.verifications.VerificationRequestVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.config.ContextConfigResponseVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.dashboard.CreateProjectResponseVo;
import me.ledge.link.api.vos.responses.dashboard.CreateTeamResponseVo;
import me.ledge.link.api.vos.responses.errors.ErrorResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.api.vos.responses.offers.OffersListVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.users.CurrentUserResponseVo;
import me.ledge.link.api.vos.responses.users.LoginUserResponseVo;
import me.ledge.link.api.vos.responses.users.UserDataListResponseVo;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.vos.responses.verifications.FinishVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.api.vos.responses.workflow.ActionConfigurationVo;
import me.ledge.link.api.wrappers.BaseLinkApiWrapper;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.api.wrappers.retrofit.two.interceptors.LedgeLinkOkThreeInterceptor;
import me.ledge.link.api.wrappers.retrofit.two.services.ConfigService;
import me.ledge.link.api.wrappers.retrofit.two.services.DashboardService;
import me.ledge.link.api.wrappers.retrofit.two.services.FinancialAccountService;
import me.ledge.link.api.wrappers.retrofit.two.services.LoanApplicationService;
import me.ledge.link.api.wrappers.retrofit.two.services.OfferService;
import me.ledge.link.api.wrappers.retrofit.two.services.UserService;
import me.ledge.link.api.wrappers.retrofit.two.services.VerificationService;
import me.ledge.link.api.wrappers.retrofit.two.utils.ErrorUtil;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Concrete {@link LinkApiWrapper} using Retrofit 2.
 * @author Wijnand
 */
public class RetrofitTwoLinkApiWrapper extends BaseLinkApiWrapper implements LinkApiWrapper {

    private ErrorUtil mErrorUtil;
    private LedgeLinkOkThreeInterceptor mInterceptor;

    private boolean mIsCertificatePinningEnabled;
    private boolean mTrustSelfSignedCerts;

    private ConfigService mConfigService;
    private UserService mUserService;
    private OfferService mOfferService;
    private LoanApplicationService mLoanApplicationService;
    private VerificationService mVerificationService;
    private FinancialAccountService mFinancialAccountService;
    private DashboardService mDashboardService;

    /**
     * Creates a new {@link RetrofitTwoLinkApiWrapper} instance.
     */
    public RetrofitTwoLinkApiWrapper() {
        super();
    }

    /**
     * Sets up all Retrofit parts.
     */
    private void setUpRetrofit(boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        mIsCertificatePinningEnabled = isCertificatePinningEnabled;
        mTrustSelfSignedCerts = trustSelfSignedCerts;
        Retrofit retrofit = getRetrofitBuilder()
                .client(createDefaultClient(isCertificatePinningEnabled, trustSelfSignedCerts))
                .build();

        Retrofit longTimeoutRetrofit = getRetrofitBuilder()
                .client(createLongTimeoutClient(isCertificatePinningEnabled, trustSelfSignedCerts))
                .build();

        mErrorUtil = new ErrorUtil(retrofit);
        mConfigService = retrofit.create(ConfigService.class);
        mUserService = retrofit.create(UserService.class);
        mLoanApplicationService = retrofit.create(LoanApplicationService.class);
        mVerificationService = retrofit.create(VerificationService.class);
        mDashboardService = retrofit.create(DashboardService.class);

        /**
         * TODO: This is probably still slightly too generic.
         * Long timeout only matters for the 'offer/requestOffers' API call.
         */
        mOfferService = longTimeoutRetrofit.create(OfferService.class);
        mFinancialAccountService = longTimeoutRetrofit.create(FinancialAccountService.class);
    }

    private Retrofit.Builder getRetrofitBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DataPointVo.class, new DataPointParser());
        gsonBuilder.registerTypeAdapter(RequiredDataPointVo.class, new RequiredDataPointParser());
        gsonBuilder.registerTypeAdapter(VerificationVo.class, new VerificationSerializer());
        gsonBuilder.registerTypeAdapter(ActionConfigurationVo.class, new ActionConfigurationParser());
        gsonBuilder.registerTypeAdapter(FinancialAccountVo.class, new FinancialAccountParser());
        gsonBuilder.registerTypeAdapter(VirtualCard.class, new VirtualCardParser());

        // Adding serializeNulls option to avoid bug in API where keys with null values
        // must be present
        Gson gson = gsonBuilder.serializeNulls().create();

        return new Retrofit.Builder()
                .baseUrl(getApiEndPoint())
                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    /**
     * @return Custom {@link OkHttpClient}.
     */
    private OkHttpClient createDefaultClient(boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        return createDefaultClientBuilder(isCertificatePinningEnabled, trustSelfSignedCerts).build();
    }

    private OkHttpClient createLongTimeoutClient(boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        return createDefaultClientBuilder(isCertificatePinningEnabled, trustSelfSignedCerts)
                .readTimeout(LinkApiWrapper.OFFERS_REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private OkHttpClient.Builder createDefaultClientBuilder(boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        // Send correct data in the headers.
        if (mInterceptor == null) {
            mInterceptor = new LedgeLinkOkThreeInterceptor(getDeviceInfo(), getDeveloperKey(), getProjectToken());
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1)); // Avoid HTTP 2

        if(isCertificatePinningEnabled) {
            // Pin SSL certificates.
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_ONE)
                    .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_TWO)
                    .add(SSL_API_HOST, SHA_256_PREFIX + SSL_FINGERPRINT_THREE)
                    .build();
            builder.certificatePinner(certificatePinner);
        }

        if(trustSelfSignedCerts) {
            return configureUntrustedBuilder(builder);
        }

        return builder;
    }

    private OkHttpClient.Builder configureUntrustedBuilder(final OkHttpClient.Builder builder) {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
            }
        }};

        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            return builder;
        } catch (KeyManagementException e) {
            return builder;
        }

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                try {
                    URL url = new URL(getApiEndPoint());
                    return hostname.equals(url.getHost());
                } catch (MalformedURLException e) {
                    return false;
                }
            }
        };
        builder.hostnameVerifier(hostnameVerifier);
        return builder;
    }

    /**
     * @param response API response.
     * @param requestPath The request path.
     * @param <T> Type of the API response.
     * @return Parsed response OR null if the call wasn't successful.
     * @throws ApiException
     */
    private <T> T handleResponse(Response<T> response, String requestPath) throws ApiException {
        T result;

        if (response.isSuccessful()) {
            result = response.body();
        } else {
            handleErrorResponse(response, requestPath);
            result = null;
        }

        return result;
    }

    /**
     * Parses the error {@link Response} and throws a new {@link ApiException}.
     * @param response The error response.
     * @param path API path.
     * @throws ApiException Always.
     */
    private void handleErrorResponse(Response<?> response, String path) throws ApiException {
        int statusCode = response.code();
        ErrorResponseVo errorResponse = mErrorUtil.parseError(response);

        ApiErrorVo apiError = new ApiErrorVo();
        apiError.statusCode = statusCode;
        apiError.serverCode = errorResponse.code;
        apiError.serverMessage = errorResponse.message;

        if(statusCode == 401) {
            apiError.isSessionExpired = true;
        }

        throwApiException(apiError, path, null);
    }

    /**
     * Throws a new {@link ApiException}.
     * @param error The error details.
     * @param path API request path.
     * @param cause Cause of the exception.
     * @throws ApiException Always when this method is invoked.
     */
    private void throwApiException(ApiErrorVo error, String path, Throwable cause) throws ApiException {
        if (error != null) {
            error.request_path = path;
        }

        ApiException exception;
        String message = String.format("Error requesting '%s' from API!", path);

        if (cause == null) {
            exception = new ApiException(error, message);
        } else {
            exception = new ApiException(error, message, cause);
        }

        throw exception;
    }

    /** {@inheritDoc} */
    @Override
    public void setBaseRequestData(String developerKey, String device,
                                   boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        super.setBaseRequestData(developerKey, device, isCertificatePinningEnabled, trustSelfSignedCerts);
        setUpRetrofit(isCertificatePinningEnabled, trustSelfSignedCerts);
        mInterceptor.setDeveloperKey(developerKey);
    }

    /** {@inheritDoc} */
    @Override
    public void setBearerToken(String token) {
        super.setBearerToken(token);
        mInterceptor.setBearerToken(token);
    }

    /** {@inheritDoc} */
    @Override
    public void setProjectToken(String projectToken) {
        super.setProjectToken(projectToken);
        mInterceptor.setProjectToken(projectToken);
    }

    @Override
    public HashMap<String, String> getHTTPHeaders() {

        HashMap<String, String> additionalHttpHeaders = new HashMap<>();
        additionalHttpHeaders.put("Developer-Authorization", "Bearer=" + getDeveloperKey());
        additionalHttpHeaders.put("Project", "Bearer=" + getProjectToken());
        additionalHttpHeaders.put("Authorization", "Bearer=" + getBearerToken());

        return additionalHttpHeaders;
    }

    /** {@inheritDoc} */
    @Override
    public void setDeveloperKey(String developerKey) {
        super.setDeveloperKey(developerKey);
        mInterceptor.setDeveloperKey(developerKey);
    }

    /** {@inheritDoc} */
    @Override
    public void setApiEndPoint(String endPoint, boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        super.setApiEndPoint(endPoint, isCertificatePinningEnabled, trustSelfSignedCerts);
        setUpRetrofit(isCertificatePinningEnabled, trustSelfSignedCerts);
    }

    /** {@inheritDoc} */
    @Override
    public LinkConfigResponseVo getLinkConfig(UnauthorizedRequestVo requestData) throws ApiException {
        LinkConfigResponseVo result;

        try {
            Response<LinkConfigResponseVo> response = mConfigService.getLoanPurposesList().execute();
            result = handleResponse(response, LinkApiWrapper.LINK_CONFIG_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.LINK_CONFIG_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public ContextConfigResponseVo getUserConfig(UnauthorizedRequestVo requestData) throws ApiException {
        ContextConfigResponseVo result;

        try {
            Response<ContextConfigResponseVo> response = mConfigService.getUserConfig().execute();
            result = handleResponse(response, LinkApiWrapper.CONFIG_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.CONFIG_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public CreateUserResponseVo createUser(DataPointList requestData) throws ApiException {
        CreateUserResponseVo result;

        try {
            JsonObject aRequest = requestData.toJSON();
            Response<CreateUserResponseVo> response = mUserService.createUser(aRequest).execute();
            result = handleResponse(response, LinkApiWrapper.CREATE_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.CREATE_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public UserResponseVo updateUser(DataPointList requestData) throws ApiException {
        UserResponseVo result;

        try {
            JsonObject aRequest = requestData.toJSON();
            Response<UserResponseVo> response = mUserService.updateUser(aRequest).execute();
            result = handleResponse(response, LinkApiWrapper.UPDATE_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.UPDATE_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public LoginUserResponseVo loginUser(LoginRequestVo requestData) throws ApiException {
        LoginUserResponseVo result;

        try {
            Response<LoginUserResponseVo> response = mUserService.loginUser(requestData).execute();
            result = handleResponse(response, LinkApiWrapper.LOGIN_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.LOGIN_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public CurrentUserResponseVo getCurrentUser(UnauthorizedRequestVo requestData, boolean throwSessionExpiredError) throws ApiException {
        CurrentUserResponseVo result;

        try {
            Response<CurrentUserResponseVo> response = mUserService.getCurrentUser().execute();
            result = handleResponse(response, LinkApiWrapper.GET_CURRENT_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.GET_CURRENT_USER_PATH, ioe);
        } catch (ApiException apiException) {
            if(!throwSessionExpiredError) {
                apiException.getError().isSessionExpired = false;
                throw apiException;
            }
            throw apiException;
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public InitialOffersResponseVo getInitialOffers(InitialOffersRequestVo requestData) throws ApiException {
        InitialOffersResponseVo result;

        try {
            Response<InitialOffersResponseVo> response = mOfferService.getInitialOffers(requestData).execute();
            result = handleResponse(response, LinkApiWrapper.INITIAL_OFFERS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.INITIAL_OFFERS_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public OffersListVo getMoreOffers(String offerRequestId, ListRequestVo requestData)
            throws ApiException {
        OffersListVo result;

        try {
            Response<OffersListVo> response = mOfferService.getMoreOffers(offerRequestId, requestData).execute();
            result = handleResponse(response, LinkApiWrapper.INITIAL_OFFERS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.INITIAL_OFFERS_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationDetailsResponseVo createLoanApplication(String offerId) throws ApiException {
        LoanApplicationDetailsResponseVo result;
        try {
            Response<LoanApplicationDetailsResponseVo> response
                    = mLoanApplicationService.createLoanApplication(offerId).execute();
            result = handleResponse(response, LinkApiWrapper.CREATE_LOAN_APPLICATION_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.CREATE_LOAN_APPLICATION_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationsSummaryListResponseVo getPendingLoanApplicationsList(ListRequestVo requestData)
            throws ApiException {
        LoanApplicationsSummaryListResponseVo result;
        try {
            Response<LoanApplicationsSummaryListResponseVo> response
                    = mLoanApplicationService.getLoanApplicationsSummaryList().execute();
            result = handleResponse(response, LinkApiWrapper.LIST_LOAN_APPLICATIONS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.LIST_LOAN_APPLICATIONS_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationDetailsResponseVo getApplicationStatus(String applicationId)
            throws ApiException {
        LoanApplicationDetailsResponseVo result;
        try {
            Response<LoanApplicationDetailsResponseVo> response
                    = mLoanApplicationService.getApplicationStatus(applicationId).execute();
            result = handleResponse(response, LinkApiWrapper.APPLICATION_STATUS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.APPLICATION_STATUS_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationDetailsResponseVo setApplicationAccount(ApplicationAccountRequestVo applicationRequestVo, String applicationId)
            throws ApiException {
        LoanApplicationDetailsResponseVo result;
        try {
            Response<LoanApplicationDetailsResponseVo> response
                    = mLoanApplicationService.setApplicationAccount(applicationRequestVo, applicationId).execute();
            result = handleResponse(response, LinkApiWrapper.APPLICATION_ACCOUNT_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.APPLICATION_ACCOUNT_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public StartVerificationResponseVo startVerification(StartVerificationRequestVo startVerificationRequestVo)
            throws ApiException {
        StartVerificationResponseVo result;
        try {
            Response<StartVerificationResponseVo> response
                    = mVerificationService.startVerification(startVerificationRequestVo).execute();
            result = handleResponse(response, LinkApiWrapper.VERIFICATION_START_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.VERIFICATION_START_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public FinishVerificationResponseVo completeVerification(VerificationRequestVo verificationRequestVo, String verificationID)
            throws ApiException {
        FinishVerificationResponseVo result;
        try {
            Response<FinishVerificationResponseVo> response
                    = mVerificationService.completeVerification(verificationID, verificationRequestVo).execute();
            result = handleResponse(response, LinkApiWrapper.VERIFICATION_FINISH_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.VERIFICATION_FINISH_PATH, ioe);
        }

        return result;
    }

    @Override
    public VerificationResponseVo restartVerification(String verificationId)
            throws ApiException {
        VerificationResponseVo result;
        try {
            Response<VerificationResponseVo> response
                    = mVerificationService.restartVerification(verificationId).execute();
            result = handleResponse(response, LinkApiWrapper.VERIFICATION_RESTART_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.VERIFICATION_RESTART_PATH, ioe);
        }

        return result;
    }

    @Override
    public VerificationStatusResponseVo getVerificationStatus(String verificationId)
            throws ApiException {
        VerificationStatusResponseVo result = null;
        try {
            if(verificationId!=null) {
                Response<VerificationStatusResponseVo> response
                        = mVerificationService.getVerificationStatus(verificationId).execute();
                result = handleResponse(response, LinkApiWrapper.VERIFICATION_STATUS_PATH);
            }
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), LinkApiWrapper.VERIFICATION_STATUS_PATH, ioe);
        }

        return result;
    }

    @Override
    public VerificationStatusResponseVo addBankAccount(AddBankAccountRequestVo addBankAccountRequestVo) throws ApiException {
        VerificationStatusResponseVo result;
        try {
            Response<VerificationStatusResponseVo> response
                    = mFinancialAccountService.addBankAccount(addBankAccountRequestVo).execute();
            result = handleResponse(response, LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
        }

        return result;
    }

    @Override
    public Card addCard(Card card) throws ApiException {
        Card result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            JsonObject aRequest = card.toJSON();
            Response<Card> response
                    = mFinancialAccountService.addCard(aRequest).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
        }

        return result;
    }

    @Override
    public VirtualCard issueVirtualCard(IssueVirtualCardRequestVo issueVirtualCardRequestVo) throws ApiException {
        VirtualCard result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            Response<VirtualCard> response
                    = mFinancialAccountService.issueVirtualCard(issueVirtualCardRequestVo).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
        }

        return result;
    }

    @Override
    public UserDataListResponseVo getFinancialAccounts(UnauthorizedRequestVo requestData) throws ApiException {
        UserDataListResponseVo result;

        try {
            Response<UserDataListResponseVo> response = mFinancialAccountService.getFinancialAccounts().execute();
            result = handleResponse(response, LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
        }

        return result;
    }

    @Override
    public FinancialAccountVo getFinancialAccount(String accountId) throws ApiException {
        FinancialAccountVo result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            Response<FinancialAccountVo> response = mFinancialAccountService.getFinancialAccount(accountId).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, LinkApiWrapper.FINANCIAL_ACCOUNT_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.FINANCIAL_ACCOUNT_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteUser(DeleteUserRequestVo requestData) throws ApiException {
        try {
            mUserService.deleteUser(requestData).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), LinkApiWrapper.DELETE_USER_PATH, ioe);
        }
    }

    @Override
    public CreateTeamResponseVo createTeam(CreateTeamRequestVo requestData) throws ApiException {
        CreateTeamResponseVo result;

        try {
            Response<CreateTeamResponseVo> response = mDashboardService.createTeam(requestData).execute();
            result = handleResponse(response, LinkApiWrapper.CREATE_TEAM_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.CREATE_TEAM_PATH, ioe);
        }

        return result;
    }

    @Override
    public void deleteTeam(String teamId) throws ApiException {
        try {
            mDashboardService.deleteTeam(teamId).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), LinkApiWrapper.DELETE_TEAM_PATH, ioe);
        }
    }

    @Override
    public CreateProjectResponseVo createProject(CreateProjectRequestVo requestData, String teamId) throws ApiException {
        CreateProjectResponseVo result;

        try {
            Response<CreateProjectResponseVo> response = mDashboardService.createProject(requestData, teamId).execute();
            result = handleResponse(response, LinkApiWrapper.CREATE_TEAM_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), LinkApiWrapper.CREATE_TEAM_PATH, ioe);
        }

        return result;
    }

    @Override
    public void deleteProject(String teamId, String projectId) throws ApiException {
        try {
            mDashboardService.deleteProject(teamId, projectId).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), LinkApiWrapper.DELETE_PROJECT_PATH, ioe);
        }
    }
}
