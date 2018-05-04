package com.shiftpayments.link.sdk.wrappers.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.utils.VerificationSerializer;
import com.shiftpayments.link.sdk.api.utils.parsers.ActionConfigurationParser;
import com.shiftpayments.link.sdk.api.utils.parsers.DataPointParser;
import com.shiftpayments.link.sdk.api.utils.parsers.FinancialAccountParser;
import com.shiftpayments.link.sdk.api.utils.parsers.RequiredDataPointParser;
import com.shiftpayments.link.sdk.api.utils.parsers.UpdateAccountParser;
import com.shiftpayments.link.sdk.api.utils.parsers.UpdateAccountPinParser;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.dashboard.CreateProjectRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.dashboard.CreateTeamRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetFundingSourceRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.DeleteUserRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LinkConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.dashboard.CreateProjectResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.dashboard.CreateTeamResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.errors.ErrorResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.OffersListVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.CreateUserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.CurrentUserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.LoginUserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.UserDataListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.UserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.StartVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionConfigurationVo;
import com.shiftpayments.link.sdk.api.wrappers.BaseShiftApiWrapper;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.wrappers.retrofit.interceptors.ShiftOkThreeInterceptor;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.ConfigService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.DashboardService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.FinancialAccountService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.LoanApplicationService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.OfferService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.UserService;
import com.shiftpayments.link.sdk.wrappers.retrofit.services.VerificationService;
import com.shiftpayments.link.sdk.wrappers.retrofit.utils.ErrorUtil;

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

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Concrete {@link ShiftApiWrapper} using Retrofit 2.
 * @author Wijnand
 */
public class RetrofitTwoShiftApiWrapper extends BaseShiftApiWrapper implements ShiftApiWrapper {

    private ErrorUtil mErrorUtil;
    private ShiftOkThreeInterceptor mInterceptor;

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
     * Creates a new {@link RetrofitTwoShiftApiWrapper} instance.
     */
    public RetrofitTwoShiftApiWrapper() {
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
        gsonBuilder.registerTypeAdapter(UpdateFinancialAccountResponseVo.class, new UpdateAccountParser());
        gsonBuilder.registerTypeAdapter(UpdateFinancialAccountPinResponseVo.class, new UpdateAccountPinParser());

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
                .readTimeout(ShiftApiWrapper.OFFERS_REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private OkHttpClient.Builder createDefaultClientBuilder(boolean isCertificatePinningEnabled, boolean trustSelfSignedCerts) {
        // Send correct data in the headers.
        if (mInterceptor == null) {
            mInterceptor = new ShiftOkThreeInterceptor(getDeviceInfo(), getDeveloperKey(), getProjectToken());
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
            result = handleResponse(response, ShiftApiWrapper.LINK_CONFIG_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.LINK_CONFIG_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public ContextConfigResponseVo getUserConfig(UnauthorizedRequestVo requestData) throws ApiException {
        ContextConfigResponseVo result;

        try {
            Response<ContextConfigResponseVo> response = mConfigService.getUserConfig().execute();
            result = handleResponse(response, ShiftApiWrapper.CONFIG_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.CONFIG_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.CREATE_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.CREATE_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public UserResponseVo updateUser(DataPointList requestData) throws ApiException {
        UserResponseVo result;

        try {
            JsonObject aRequest = requestData.toJSON();
            Response<UserResponseVo> response = mUserService.updateUser(aRequest).execute();
            result = handleResponse(response, ShiftApiWrapper.UPDATE_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.UPDATE_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public LoginUserResponseVo loginUser(LoginRequestVo requestData) throws ApiException {
        LoginUserResponseVo result;

        try {
            Response<LoginUserResponseVo> response = mUserService.loginUser(requestData).execute();
            result = handleResponse(response, ShiftApiWrapper.LOGIN_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.LOGIN_USER_PATH, ioe);
        }

        return result;
    }

    @Override
    public CurrentUserResponseVo getCurrentUser(UnauthorizedRequestVo requestData, boolean throwSessionExpiredError) throws ApiException {
        CurrentUserResponseVo result;

        try {
            Response<CurrentUserResponseVo> response = mUserService.getCurrentUser().execute();
            result = handleResponse(response, ShiftApiWrapper.GET_CURRENT_USER_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.GET_CURRENT_USER_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.INITIAL_OFFERS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.INITIAL_OFFERS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.INITIAL_OFFERS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.INITIAL_OFFERS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.CREATE_LOAN_APPLICATION_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.CREATE_LOAN_APPLICATION_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.LIST_LOAN_APPLICATIONS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.LIST_LOAN_APPLICATIONS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.APPLICATION_STATUS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.APPLICATION_STATUS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.APPLICATION_ACCOUNT_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.APPLICATION_ACCOUNT_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.VERIFICATION_START_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.VERIFICATION_START_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.VERIFICATION_FINISH_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.VERIFICATION_FINISH_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.VERIFICATION_RESTART_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.VERIFICATION_RESTART_PATH, ioe);
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
                result = handleResponse(response, ShiftApiWrapper.VERIFICATION_STATUS_PATH);
            }
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.VERIFICATION_STATUS_PATH, ioe);
        }

        return result;
    }

    @Override
    public VerificationStatusResponseVo addBankAccount(AddBankAccountRequestVo addBankAccountRequestVo) throws ApiException {
        VerificationStatusResponseVo result;
        try {
            Response<VerificationStatusResponseVo> response
                    = mFinancialAccountService.addBankAccount(addBankAccountRequestVo).execute();
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
        }

        return result;
    }

    @Override
    public Card issueVirtualCard(IssueVirtualCardRequestVo issueVirtualCardRequestVo) throws ApiException {
        Card result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            Response<Card> response
                    = mFinancialAccountService.issueVirtualCard(issueVirtualCardRequestVo).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, ShiftApiWrapper.ISSUE_CARD_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.ISSUE_CARD_PATH, ioe);
        }

        return result;
    }

    @Override
    public UserDataListResponseVo getFinancialAccounts(UnauthorizedRequestVo requestData) throws ApiException {
        UserDataListResponseVo result;

        try {
            Response<UserDataListResponseVo> response = mFinancialAccountService.getFinancialAccounts().execute();
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNTS_PATH, ioe);
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
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_PATH, ioe);
        }

        return result;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteUser(DeleteUserRequestVo requestData) throws ApiException {
        try {
            mUserService.deleteUser(requestData).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.DELETE_USER_PATH, ioe);
        }
    }

    @Override
    public CreateTeamResponseVo createTeam(CreateTeamRequestVo requestData) throws ApiException {
        CreateTeamResponseVo result;

        try {
            Response<CreateTeamResponseVo> response = mDashboardService.createTeam(requestData).execute();
            result = handleResponse(response, ShiftApiWrapper.CREATE_TEAM_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.CREATE_TEAM_PATH, ioe);
        }

        return result;
    }

    @Override
    public void deleteTeam(String teamId) throws ApiException {
        try {
            mDashboardService.deleteTeam(teamId).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.DELETE_TEAM_PATH, ioe);
        }
    }

    @Override
    public CreateProjectResponseVo createProject(CreateProjectRequestVo requestData, String teamId) throws ApiException {
        CreateProjectResponseVo result;

        try {
            Response<CreateProjectResponseVo> response = mDashboardService.createProject(requestData, teamId).execute();
            result = handleResponse(response, ShiftApiWrapper.CREATE_TEAM_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.CREATE_TEAM_PATH, ioe);
        }

        return result;
    }

    @Override
    public void deleteProject(String teamId, String projectId) throws ApiException {
        try {
            mDashboardService.deleteProject(teamId, projectId).execute();
        } catch (IOException ioe) {
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.DELETE_PROJECT_PATH, ioe);
        }
    }

    @Override
    public UpdateFinancialAccountResponseVo updateFinancialAccount(String accountId, UpdateFinancialAccountRequestVo card) throws ApiException {
        UpdateFinancialAccountResponseVo result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            Response<UpdateFinancialAccountResponseVo> response
                    = mFinancialAccountService.updateFinancialAccount(accountId, card).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_PATH, ioe);
        }

        return result;
    }

    @Override
    public UpdateFinancialAccountPinResponseVo updateFinancialAccountPin(String accountId, UpdateFinancialAccountPinRequestVo card) throws ApiException {
        UpdateFinancialAccountPinResponseVo result;
        try {
            // Setting VGS proxy only for this call
            this.setApiEndPoint(getVgsEndPoint(), mIsCertificatePinningEnabled, false);
            Response<UpdateFinancialAccountPinResponseVo> response
                    = mFinancialAccountService.updateFinancialAccountPin(accountId, card).execute();
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_PIN_PATH);
        } catch (IOException ioe) {
            this.setApiEndPoint(getApiEndPoint(), mIsCertificatePinningEnabled, mTrustSelfSignedCerts);
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_PIN_PATH, ioe);
        }

        return result;
    }

    @Override
    public TransactionListResponseVo getFinancialAccountTransactions(String accountId, int rows, String transactionId) throws ApiException {
        TransactionListResponseVo result;
        try {
            // TODO: using last transaction ID instead of page due to limitation on Card Backend
            Response<TransactionListResponseVo> response = mFinancialAccountService.getTransactions(accountId, rows, transactionId).execute();
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_TRANSACTIONS_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_TRANSACTIONS_PATH, ioe);
        }
        return result;
    }

    @Override
    public FundingSourceVo getFinancialAccountFundingSource(String accountId) throws ApiException {
        FundingSourceVo result;
        try {
            Response<FundingSourceVo> response = mFinancialAccountService.getFundingSource(accountId).execute();
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH, ioe);
        }
        return result;
    }

    @Override
    public FundingSourceVo setAccountFundingSource(String accountId, SetFundingSourceRequestVo setFundingSourceRequest) throws ApiException {
        FundingSourceVo result;
        try {
            Response<FundingSourceVo> response = mFinancialAccountService.setFundingSource(accountId, setFundingSourceRequest).execute();
            result = handleResponse(response, ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.FINANCIAL_ACCOUNT_FUNDING_SOURCE_PATH, ioe);
        }
        return result;
    }

    @Override
    public FundingSourceListVo getUserFundingSources(UnauthorizedRequestVo requestData) throws ApiException {
        FundingSourceListVo result;
        try {
            Response<FundingSourceListVo> response = mFinancialAccountService.getUserFundingSources().execute();
            result = handleResponse(response, ShiftApiWrapper.USER_FUNDING_SOURCES_PATH);
        } catch (IOException ioe) {
            result = null;
            throwApiException(new ApiErrorVo(), ShiftApiWrapper.USER_FUNDING_SOURCES_PATH, ioe);
        }
        return result;
    }
}
