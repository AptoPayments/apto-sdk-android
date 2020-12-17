package com.aptopayments.mobile.platform

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.RestrictTo
import androidx.annotation.VisibleForTesting
import com.aptopayments.mobile.data.AccessToken
import com.aptopayments.mobile.data.ListPagination
import com.aptopayments.mobile.data.PaginatedList
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.cardproduct.CardProduct
import com.aptopayments.mobile.data.cardproduct.CardProductSummary
import com.aptopayments.mobile.data.config.ContextConfiguration
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthCredential
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.data.paymentsources.NewPaymentSource
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.mobile.data.voip.Action
import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.data.workflowaction.WorkflowAction
import com.aptopayments.mobile.di.applicationModule
import com.aptopayments.mobile.di.repositoryModule
import com.aptopayments.mobile.di.useCasesModule
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiKeyProvider
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.AptoSdkEnvironment.PRD
import com.aptopayments.mobile.repository.PushTokenRepository
import com.aptopayments.mobile.repository.UserPreferencesRepository
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.usecases.*
import com.aptopayments.mobile.repository.cardapplication.usecases.AcceptDisclaimerUseCase
import com.aptopayments.mobile.repository.cardapplication.usecases.IssueCardUseCase.Params
import com.aptopayments.mobile.repository.cardapplication.usecases.SetBalanceStoreUseCase
import com.aptopayments.mobile.repository.cardapplication.usecases.StartCardApplicationParams
import com.aptopayments.mobile.repository.config.usecases.GetCardProductParams
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.mobile.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.payment.usecases.PushFundsUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.GetPaymentSourcesUseCase
import com.aptopayments.mobile.repository.statements.usecases.GetMonthlyStatementUseCase
import com.aptopayments.mobile.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.mobile.repository.transaction.TransactionListFilters
import com.aptopayments.mobile.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.mobile.repository.user.usecases.CreateUserUseCase
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallParams
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.lang.ref.WeakReference
import java.lang.reflect.Modifier
import java.util.Locale

@SuppressLint("VisibleForTests")
object AptoPlatform : AptoPlatformProtocol {

    var delegate: AptoPlatformDelegate? = null
        set(newValue) {
            weakDelegate = WeakReference(newValue)
        }

    private var weakDelegate = WeakReference<AptoPlatformDelegate?>(null)

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    lateinit var koin: Koin

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    internal lateinit var useCasesWrapper: UseCasesWrapper
    private val userSessionRepository: UserSessionRepository by lazy { koin.get() }

    @VisibleForTesting(otherwise = Modifier.PROTECTED)
    lateinit var application: Application

    private var uiModules: List<Module> = listOf()
    private val userPreferencesRepository: UserPreferencesRepository by lazy { koin.get() }

    fun setUiModules(list: List<Any>) {
        uiModules = list.filterIsInstance<Module>()
    }

    fun initializeWithApiKey(application: Application, apiKey: String, environment: AptoSdkEnvironment = PRD) {
        initialize(application)
        setApiKey(apiKey, environment)
    }

    fun initialize(application: Application) {
        this.application = application
        initKoin(application)
        AndroidThreeTen.init(application)
        useCasesWrapper = UseCasesWrapper()
    }

    fun setApiKey(apiKey: String, environment: AptoSdkEnvironment = PRD) {
        ApiKeyProvider.set(apiKey, environment)
        subscribeToSdkDeprecatedEvent()
    }

    override fun userTokenPresent(): Boolean = userSessionRepository.userToken.isNotEmpty()

    override fun setUserToken(userToken: String) {
        userSessionRepository.userToken = userToken
    }

    override fun currentToken(): AccessToken? {
        return if (userTokenPresent()) {
            AccessToken(userSessionRepository.userToken)
        } else {
            null
        }
    }

    private fun subscribeToSdkDeprecatedEvent() {
        koin.get<NetworkHandler>().subscribeDeprecatedSdkListener(this) { deprecated ->
            if (deprecated) {
                weakDelegate.get()?.sdkDeprecated()
            }
        }
    }

    fun registerFirebaseToken(firebaseToken: String) {
        koin.get<PushTokenRepository>().pushToken = firebaseToken
    }

    private fun initKoin(application: Application) {
        this.koin = startKoin {
            androidContext(application)
            modules(getModules())
        }.koin
    }

    private fun getModules(): List<Module> {
        val list = mutableListOf(applicationModule, repositoryModule, useCasesModule)
        list.addAll(uiModules)
        return list
    }

    override fun clearMonthlySpendingCache() = useCasesWrapper.clearMonthlySpendingCacheUseCase(Unit)

    override fun fetchContextConfiguration(
        forceRefresh: Boolean,
        callback: (Either<Failure, ContextConfiguration>) -> Unit
    ) = useCasesWrapper.getContextConfigurationUseCase(forceRefresh) { callback(it) }

    override fun fetchCardProduct(
        cardProductId: String,
        forceRefresh: Boolean,
        callback: (Either<Failure, CardProduct>) -> Unit
    ) = useCasesWrapper.getCardProductUseCase(
        GetCardProductParams(forceRefresh, cardProductId)
    ) { callback(it) }

    override fun fetchCardProducts(callback: (Either<Failure, List<CardProductSummary>>) -> Unit) =
        useCasesWrapper.getCardProductsUseCase(Unit) { callback(it) }

    override fun isShowDetailedCardActivityEnabled(): Boolean = userPreferencesRepository.showDetailedCardActivity

    override fun setIsShowDetailedCardActivityEnabled(enabled: Boolean) {
        userPreferencesRepository.showDetailedCardActivity = enabled
    }

    override fun createUser(
        userData: DataPointList,
        custodianUid: String?,
        metadata: String?,
        callback: (Either<Failure, User>) -> Unit
    ) =
        useCasesWrapper.createUserUseCase(CreateUserUseCase.Params(userData, custodianUid, metadata)) { result ->
            result.either({}, { user -> userSessionRepository.userToken = user.token })
            callback(result)
        }

    override fun loginUserWith(verifications: List<Verification>, callback: (Either<Failure, User>) -> Unit) =
        useCasesWrapper.loginUserUseCase(verifications) { result ->
            result.either({}, { user -> userSessionRepository.userToken = user.token })
            callback(result)
        }

    override fun updateUserInfo(userData: DataPointList, callback: (Either<Failure, User>) -> Unit) =
        useCasesWrapper.updateUserDataUseCase(userData) { callback(it) }

    override fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit) =
        userSessionRepository.subscribeSessionInvalidListener(instance, callback)

    override fun unsubscribeSessionInvalidListener(instance: Any) {
        userSessionRepository.unsubscribeSessionInvalidListener(instance)
    }

    override fun logout() {
        userSessionRepository.clearUserSession()
    }

    override fun startOauthAuthentication(
        balanceType: AllowedBalanceType,
        callback: (Either<Failure, OAuthAttempt>) -> Unit
    ) = useCasesWrapper.startOAuthAuthenticationUseCase(balanceType) { callback(it) }

    override fun verifyOauthAttemptStatus(
        oAuthAttempt: OAuthAttempt,
        callback: (Either<Failure, OAuthAttempt>) -> Unit
    ) = useCasesWrapper.getOAuthAttemptStatusUseCase(oAuthAttempt.id) { callback(it) }

    override fun saveOauthUserData(
        userData: DataPointList,
        allowedBalanceType: AllowedBalanceType,
        tokenId: String,
        callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit
    ) = useCasesWrapper.saveOAuthUserDataUseCase(
        SaveOAuthUserDataUseCase.Params(
            allowedBalanceType = allowedBalanceType,
            dataPointList = userData,
            tokenId = tokenId
        )
    ) { callback(it) }

    override fun fetchOAuthData(
        allowedBalanceType: AllowedBalanceType,
        tokenId: String,
        callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit
    ) =
        useCasesWrapper.retrieveOAuthUserDataUseCase(
            RetrieveOAuthUserDataUseCase.Params(allowedBalanceType = allowedBalanceType, tokenId = tokenId)
        ) { callback(it) }

    override fun startPhoneVerification(phoneNumber: PhoneNumber, callback: (Either<Failure, Verification>) -> Unit) =
        useCasesWrapper.startPhoneVerificationUseCase(phoneNumber) { callback(it) }

    override fun startEmailVerification(email: String, callback: (Either<Failure, Verification>) -> Unit) =
        useCasesWrapper.startEmailVerificationUseCase(email) { callback(it) }

    override fun startPrimaryVerification(callback: (Either<Failure, Verification>) -> Unit) {
        useCasesWrapper.startPrimaryVerificationUseCase(Unit) { callback(it) }
    }

    override fun completeVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
        useCasesWrapper.finishVerificationUseCase(verification) { callback(it) }

    override fun restartVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
        useCasesWrapper.restartVerificationUseCase(verification) { callback(it) }

    override fun applyToCard(
        cardProduct: CardProduct,
        callback: (Either<Failure, CardApplication>) -> Unit
    ) =
        useCasesWrapper.startCardApplicationUseCase(
            StartCardApplicationParams(
                cardProductId = cardProduct.id
            )
        ) { callback(it) }

    override fun fetchCardApplicationStatus(
        applicationId: String,
        callback: (Either<Failure, CardApplication>) -> Unit
    ) = useCasesWrapper.getCardApplicationUseCase(applicationId) { callback(it) }

    override fun setBalanceStore(
        applicationId: String,
        tokenId: String,
        callback: (Either<Failure, SelectBalanceStoreResult>) -> Unit
    ) = useCasesWrapper.setBalanceStoreUseCase(
        SetBalanceStoreUseCase.Params(applicationId, tokenId)
    ) { callback(it) }

    override fun acceptDisclaimer(
        workflowObjectId: String,
        workflowAction: WorkflowAction,
        callback: (Either<Failure, Unit>) -> Unit
    ) = useCasesWrapper.acceptDisclaimerUseCase(
        AcceptDisclaimerUseCase.Params(workflowObjectId, workflowAction.actionId)
    ) { callback(it) }

    override fun cancelCardApplication(applicationId: String, callback: (Either<Failure, Unit>) -> Unit) =
        useCasesWrapper.cancelCardApplicationUseCase(applicationId) { callback(it) }

    override fun issueCard(
        applicationId: String,
        additionalFields: Map<String, Any>?,
        metadata: String?,
        callback: (Either<Failure, Card>) -> Unit
    ) = useCasesWrapper.issueCardUseCase(Params(applicationId, additionalFields, metadata)) { callback(it) }

    override fun issueCard(
        cardProductId: String,
        credential: OAuthCredential?,
        additionalFields: Map<String, Any>?,
        initialFundingSourceId: String?,
        callback: (Either<Failure, Card>) -> Unit
    ) = useCasesWrapper.issueCardCardProductUseCase(
        IssueCardUseCase.Params(
            cardProductId = cardProductId,
            credential = credential,
            additionalFields = additionalFields,
            initialFundingSourceId = initialFundingSourceId
        )
    )

    override fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit) =
        fetchCards(null) { result -> callback(result.map { it.data }) }

    override fun fetchCards(pagination: ListPagination?, callback: (Either<Failure, PaginatedList<Card>>) -> Unit) {
        useCasesWrapper.getCardsUseCase(pagination) { callback(it) }
    }

    override fun fetchFinancialAccount(
        accountId: String,
        forceRefresh: Boolean,
        callback: (Either<Failure, Card>) -> Unit
    ) = fetchCard(accountId, forceRefresh, callback)

    override fun fetchCard(cardId: String, forceRefresh: Boolean, callback: (Either<Failure, Card>) -> Unit) {
        useCasesWrapper.getCardUseCase(GetCardParams(cardId, forceRefresh)) { callback(it) }
    }

    override fun fetchCardDetails(cardId: String, callback: (Either<Failure, CardDetails>) -> Unit) =
        useCasesWrapper.getCardDetailsUseCase(cardId) { callback(it) }

    override fun activatePhysicalCard(
        cardId: String,
        code: String,
        callback: (Either<Failure, ActivatePhysicalCardResult>) -> Unit
    ) = useCasesWrapper.activatePhysicalCardUseCase(ActivatePhysicalCardUseCase.Params(cardId, code)) { callback(it) }

    override fun unlockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
        useCasesWrapper.unlockCardUseCase(cardId) { callback(it) }

    override fun lockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
        useCasesWrapper.lockCardUseCase(cardId) { callback(it) }

    override fun changeCardPin(cardId: String, pin: String, callback: (Either<Failure, Card>) -> Unit) =
        useCasesWrapper.setPinUseCase(SetPinParams(cardId, pin)) { callback(it) }

    override fun fetchCardTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceRefresh: Boolean,
        clearCachedValues: Boolean,
        callback: (Either<Failure, List<Transaction>>) -> Unit
    ) = useCasesWrapper.getTransactionsUseCase(
        GetTransactionsUseCase.Params(
            cardId = cardId,
            filters = filters,
            forceApiCall = forceRefresh,
            clearCachedValues = clearCachedValues
        )
    ) { callback(it) }

    override fun cardMonthlySpending(
        cardId: String,
        month: String,
        year: String,
        callback: (Either<Failure, MonthlySpending>) -> Unit
    ) = useCasesWrapper.getMonthlySpendingUseCase(
        GetMonthlySpendingUseCase.Params(cardId = cardId, month = month, year = year)
    ) { callback(it) }

    override fun cardMonthlySpending(
        cardId: String,
        month: Int,
        year: Int,
        callback: (Either<Failure, MonthlySpending>) -> Unit
    ) {
        val date = LocalDate.of(year, month, 1)
        val monthName = date.month.getDisplayName(TextStyle.FULL, Locale.US)

        useCasesWrapper.getMonthlySpendingUseCase(
            GetMonthlySpendingUseCase.Params(cardId = cardId, month = monthName, year = year.toString())
        ) { callback(it) }
    }

    override fun fetchMonthlyStatement(month: Int, year: Int, callback: (Either<Failure, MonthlyStatement>) -> Unit) {
        useCasesWrapper.getMonthlyStatementUseCase(GetMonthlyStatementUseCase.Params(month, year)) { callback(it) }
    }

    override fun fetchMonthlyStatementPeriod(callback: (Either<Failure, MonthlyStatementPeriod>) -> Unit) =
        useCasesWrapper.getMonthlyStatementPeriodUseCase(Unit) { callback(it) }

    override fun fetchCardFundingSources(
        cardId: String,
        page: Int,
        rows: Int,
        forceRefresh: Boolean,
        callback: (Either<Failure, List<Balance>>) -> Unit
    ) = useCasesWrapper.getFundingSourcesUseCase(
        GetFundingSourcesUseCase.Params(cardId, forceRefresh, page, rows)
    ) { callback(it) }

    override fun fetchCardFundingSource(
        cardId: String,
        forceRefresh: Boolean,
        callback: (Either<Failure, Balance>) -> Unit
    ) = useCasesWrapper.getCardBalanceUseCase(GetCardBalanceParams(cardId, forceRefresh)) { callback(it) }

    override fun setCardFundingSource(
        fundingSourceId: String,
        cardId: String,
        callback: (Either<Failure, Balance>) -> Unit
    ) = useCasesWrapper.setFundingSourcesUseCase(SetCardBalanceParams(cardId, fundingSourceId)) { callback(it) }

    override fun addCardFundingSource(
        cardId: String,
        fundingSourceType: String,
        custodianType: String,
        credentialType: String,
        tokenId: String,
        callback: (Either<Failure, Balance>) -> Unit
    ) = useCasesWrapper.addCardBalanceUseCase(
        AddCardBalanceParams(cardId, fundingSourceType, custodianType, credentialType, tokenId)
    ) { callback(it) }

    override fun fetchNotificationPreferences(callback: (Either<Failure, NotificationPreferences>) -> Unit) =
        useCasesWrapper.getNotificationPreferencesUseCase(Unit) { callback(it) }

    override fun updateNotificationPreferences(
        preferences: NotificationPreferences,
        callback: (Either<Failure, NotificationPreferences>) -> Unit
    ) = useCasesWrapper.updateNotificationPreferencesUseCase(preferences.preferences!!) { callback(it) }

    override fun fetchVoIPToken(cardId: String, actionSource: Action, callback: (Either<Failure, VoipCall>) -> Unit) =
        useCasesWrapper.setupVoipCallUseCase(SetupVoipCallParams(cardId, actionSource)) { callback(it) }

    override fun fetchProvisioningData(
        cardId: String,
        clientAppId: String,
        clientDeviceId: String,
        walletId: String,
        callback: (Either<Failure, ProvisioningData>) -> Unit
    ) {
        return useCasesWrapper.getProvisioningDataUseCase(
            GetProvisioningDataUseCase.Params(
                cardId,
                clientAppId,
                clientDeviceId,
                walletId
            )
        ) { callback(it) }
    }

    override fun addPaymentSource(
        paymentSource: NewPaymentSource,
        callback: (Either<Failure, PaymentSource>) -> Unit
    ) {
        useCasesWrapper.addPaymentSourceUseCase(paymentSource) { callback(it) }
    }

    override fun getPaymentSources(
        callback: (Either<Failure, List<PaymentSource>>) -> Unit,
        limit: Int?,
        startingAfter: String?,
        endingBefore: String?
    ) {
        useCasesWrapper.getPaymentSourcesUseCase(
            GetPaymentSourcesUseCase.Params(
                startingAfter,
                endingBefore,
                limit
            )
        ) { callback(it) }
    }

    override fun deletePaymentSource(paymentSourceId: String, callback: (Either<Failure, Unit>) -> Unit) {
        useCasesWrapper.deletePaymentSourceUseCase(paymentSourceId)
    }

    override fun pushFunds(
        balanceId: String,
        paymentSourceId: String,
        amount: Money,
        callback: (Either<Failure, Payment>) -> Unit
    ) {
        useCasesWrapper.pushFundsUseCase(
            PushFundsUseCase.Params(
                balanceId = balanceId,
                paymentSourceId = paymentSourceId,
                amount = amount
            )
        ) { callback(it) }
    }

    override fun setCardPasscode(
        cardId: String,
        passcode: String,
        verificationId: String?,
        callback: (Either<Failure, Unit>) -> Unit
    ) {
        useCasesWrapper.setCardPasscodeUseCase(
            SetCardPasscodeUseCase.Params(
                cardId = cardId,
                passcode = passcode,
                verificationId = verificationId
            )
        ) { callback(it) }
    }
}
