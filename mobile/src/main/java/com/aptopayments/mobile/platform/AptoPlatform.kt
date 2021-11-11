package com.aptopayments.mobile.platform

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.RestrictTo
import com.aptopayments.mobile.data.AccessToken
import com.aptopayments.mobile.data.ListPagination
import com.aptopayments.mobile.data.PaginatedList
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.cardproduct.CardProduct
import com.aptopayments.mobile.data.cardproduct.CardProductSummary
import com.aptopayments.mobile.data.config.ContextConfiguration
import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.data.paymentsources.NewPaymentSource
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.data.user.agreements.AgreementAction
import com.aptopayments.mobile.data.user.agreements.ReviewAgreementsInput
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
import com.aptopayments.mobile.repository.cardapplication.usecases.*
import com.aptopayments.mobile.repository.cardapplication.usecases.GetCardApplicationUseCase
import com.aptopayments.mobile.repository.cardapplication.usecases.IssueCardUseCase
import com.aptopayments.mobile.repository.cardapplication.usecases.IssueCardUseCase.Params
import com.aptopayments.mobile.repository.cardapplication.usecases.StartCardApplicationUseCase
import com.aptopayments.mobile.repository.config.usecases.GetCardProductParams
import com.aptopayments.mobile.repository.config.usecases.GetCardProductUseCase
import com.aptopayments.mobile.repository.config.usecases.GetCardProductsUseCase
import com.aptopayments.mobile.repository.config.usecases.GetContextConfigurationUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.AssignAchAccountToBalanceUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetAchAccountDetailsUseCase
import com.aptopayments.mobile.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.mobile.repository.oauth.usecases.GetOAuthAttemptStatusUseCase
import com.aptopayments.mobile.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.mobile.repository.oauth.usecases.StartOAuthAuthenticationUseCase
import com.aptopayments.mobile.repository.payment.usecases.PushFundsUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.AddPaymentSourceUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.DeletePaymentSourceUseCase
import com.aptopayments.mobile.repository.paymentsources.usecases.GetPaymentSourcesUseCase
import com.aptopayments.mobile.repository.statements.usecases.GetMonthlyStatementPeriodUseCase
import com.aptopayments.mobile.repository.statements.usecases.GetMonthlyStatementUseCase
import com.aptopayments.mobile.repository.stats.usecases.ClearMonthlySpendingCacheUseCase
import com.aptopayments.mobile.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.mobile.repository.transaction.TransactionListFilters
import com.aptopayments.mobile.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.mobile.repository.p2p.usecases.P2pFindRecipientUseCase
import com.aptopayments.mobile.repository.p2p.usecases.P2pMakeTransfer
import com.aptopayments.mobile.repository.user.usecases.*
import com.aptopayments.mobile.repository.user.usecases.CreateUserUseCase
import com.aptopayments.mobile.repository.user.usecases.GetNotificationPreferencesUseCase
import com.aptopayments.mobile.repository.user.usecases.LoginUserUseCase
import com.aptopayments.mobile.repository.user.usecases.ReviewAgreementsUseCase
import com.aptopayments.mobile.repository.user.usecases.UpdateUserDataUseCase
import com.aptopayments.mobile.repository.verification.usecase.FinishVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.RestartVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.StartEmailVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.StartPhoneVerificationUseCase
import com.aptopayments.mobile.repository.verification.usecase.StartPrimaryVerificationUseCase
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallParams
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallUseCase
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.lang.ref.WeakReference
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

    private val userSessionRepository: UserSessionRepository by lazy { koin.get() }

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
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

    override fun clearMonthlySpendingCache() = koin.get<ClearMonthlySpendingCacheUseCase>().invoke(Unit)

    override fun fetchContextConfiguration(
        forceRefresh: Boolean,
        callback: (Either<Failure, ContextConfiguration>) -> Unit
    ) = koin.get<GetContextConfigurationUseCase>().invoke(forceRefresh) { callback(it) }

    override fun fetchCardProduct(
        cardProductId: String,
        forceRefresh: Boolean,
        callback: (Either<Failure, CardProduct>) -> Unit
    ) = koin.get<GetCardProductUseCase>().invoke(GetCardProductParams(forceRefresh, cardProductId)) { callback(it) }

    override fun fetchCardProducts(callback: (Either<Failure, List<CardProductSummary>>) -> Unit) =
        koin.get<GetCardProductsUseCase>().invoke(Unit) { callback(it) }

    override fun isShowDetailedCardActivityEnabled(): Boolean = userPreferencesRepository.showDetailedCardActivity

    override fun setIsShowDetailedCardActivityEnabled(enabled: Boolean) {
        userPreferencesRepository.showDetailedCardActivity = enabled
    }

    override fun createUser(
        userData: DataPointList,
        custodianUid: String?,
        metadata: String?,
        callback: (Either<Failure, User>) -> Unit
    ) = koin.get<CreateUserUseCase>().invoke(CreateUserUseCase.Params(userData, custodianUid, metadata)) { result ->
        result.runIfRight { user -> userSessionRepository.userToken = user.token }
        callback(result)
    }

    override fun loginUserWith(verifications: List<Verification>, callback: (Either<Failure, User>) -> Unit) =
        koin.get<LoginUserUseCase>().invoke(verifications) { result ->
            result.runIfRight { user -> userSessionRepository.userToken = user.token }
            callback(result)
        }

    override fun updateUserInfo(userData: DataPointList, callback: (Either<Failure, User>) -> Unit) =
        koin.get<UpdateUserDataUseCase>().invoke(userData) { callback(it) }

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
    ) = koin.get<StartOAuthAuthenticationUseCase>().invoke(balanceType) { callback(it) }

    override fun verifyOauthAttemptStatus(
        oAuthAttempt: OAuthAttempt,
        callback: (Either<Failure, OAuthAttempt>) -> Unit
    ) = koin.get<GetOAuthAttemptStatusUseCase>().invoke(oAuthAttempt.id) { callback(it) }

    override fun saveOauthUserData(
        userData: DataPointList,
        allowedBalanceType: AllowedBalanceType,
        tokenId: String,
        callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit
    ) = koin.get<SaveOAuthUserDataUseCase>().invoke(
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
    ) = koin.get<RetrieveOAuthUserDataUseCase>().invoke(
        RetrieveOAuthUserDataUseCase.Params(allowedBalanceType = allowedBalanceType, tokenId = tokenId)
    ) { callback(it) }

    override fun startPhoneVerification(phoneNumber: PhoneNumber, callback: (Either<Failure, Verification>) -> Unit) =
        koin.get<StartPhoneVerificationUseCase>().invoke(phoneNumber) { callback(it) }

    override fun startEmailVerification(email: String, callback: (Either<Failure, Verification>) -> Unit) =
        koin.get<StartEmailVerificationUseCase>().invoke(email) { callback(it) }

    override fun startPrimaryVerification(callback: (Either<Failure, Verification>) -> Unit) {
        koin.get<StartPrimaryVerificationUseCase>().invoke(Unit) { callback(it) }
    }

    override fun completeVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
        koin.get<FinishVerificationUseCase>().invoke(verification) { callback(it) }

    override fun restartVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
        koin.get<RestartVerificationUseCase>().invoke(verification) { callback(it) }

    override fun applyToCard(cardProduct: CardProduct, callback: (Either<Failure, CardApplication>) -> Unit) =
        koin.get<StartCardApplicationUseCase>()
            .invoke(StartCardApplicationParams(cardProductId = cardProduct.id)) { callback(it) }

    override fun fetchCardApplicationStatus(
        applicationId: String,
        callback: (Either<Failure, CardApplication>) -> Unit
    ) = koin.get<GetCardApplicationUseCase>().invoke(applicationId) { callback(it) }

    override fun setBalanceStore(
        applicationId: String,
        tokenId: String,
        callback: (Either<Failure, SelectBalanceStoreResult>) -> Unit
    ) = koin.get<SetBalanceStoreUseCase>()
        .invoke(SetBalanceStoreUseCase.Params(applicationId, tokenId)) { callback(it) }

    override fun acceptDisclaimer(
        workflowObjectId: String,
        workflowAction: WorkflowAction,
        callback: (Either<Failure, Unit>) -> Unit
    ) = koin.get<AcceptDisclaimerUseCase>()
        .invoke(AcceptDisclaimerUseCase.Params(workflowObjectId, workflowAction.actionId)) { callback(it) }

    override fun cancelCardApplication(applicationId: String, callback: (Either<Failure, Unit>) -> Unit) =
        koin.get<CancelCardApplicationUseCase>()(applicationId) { callback(it) }

    override fun issueCard(
        applicationId: String,
        metadata: String?,
        design: IssueCardDesign?,
        callback: (Either<Failure, Card>) -> Unit
    ) = koin.get<IssueCardUseCase>().invoke(Params(applicationId, metadata, design)) { callback(it) }

    override fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit) =
        fetchCards(null) { result -> callback(result.map { it.data }) }

    override fun fetchCards(pagination: ListPagination?, callback: (Either<Failure, PaginatedList<Card>>) -> Unit) =
        koin.get<GetCardsUseCase>().invoke(pagination) { callback(it) }

    override fun fetchCard(cardId: String, forceRefresh: Boolean, callback: (Either<Failure, Card>) -> Unit) {
        koin.get<GetCardUseCase>().invoke(GetCardParams(cardId, forceRefresh)) { callback(it) }
    }

    override fun activatePhysicalCard(
        cardId: String,
        code: String,
        callback: (Either<Failure, ActivatePhysicalCardResult>) -> Unit
    ) = koin.get<ActivatePhysicalCardUseCase>()
        .invoke(ActivatePhysicalCardUseCase.Params(cardId, code)) { callback(it) }

    override fun unlockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
        koin.get<UnlockCardUseCase>().invoke(cardId) { callback(it) }

    override fun lockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
        koin.get<LockCardUseCase>().invoke(cardId) { callback(it) }

    override fun changeCardPin(cardId: String, pin: String, callback: (Either<Failure, Card>) -> Unit) =
        koin.get<SetPinUseCase>().invoke(SetPinParams(cardId, pin)) { callback(it) }

    override fun fetchCardTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceRefresh: Boolean,
        clearCachedValues: Boolean,
        callback: (Either<Failure, List<Transaction>>) -> Unit
    ) = koin.get<GetTransactionsUseCase>().invoke(
        GetTransactionsUseCase.Params(
            cardId = cardId,
            filters = filters,
            forceApiCall = forceRefresh,
            clearCachedValues = clearCachedValues
        )
    ) { callback(it) }

    override fun cardMonthlySpending(
        cardId: String,
        month: Int,
        year: Int,
        callback: (Either<Failure, MonthlySpending>) -> Unit
    ) {
        val date = LocalDate.of(year, month, 1)
        val monthName = date.month.getDisplayName(TextStyle.FULL, Locale.US)
        koin.get<GetMonthlySpendingUseCase>().invoke(
            GetMonthlySpendingUseCase.Params(
                cardId = cardId,
                month = monthName,
                year = year.toString()
            )
        ) { callback(it) }
    }

    override fun fetchMonthlyStatement(month: Int, year: Int, callback: (Either<Failure, MonthlyStatement>) -> Unit) {
        koin.get<GetMonthlyStatementUseCase>().invoke(GetMonthlyStatementUseCase.Params(month, year)) { callback(it) }
    }

    override fun fetchMonthlyStatementPeriod(callback: (Either<Failure, MonthlyStatementPeriod>) -> Unit) =
        koin.get<GetMonthlyStatementPeriodUseCase>().invoke(Unit) { callback(it) }

    override fun fetchCardFundingSources(
        cardId: String,
        page: Int,
        rows: Int,
        forceRefresh: Boolean,
        callback: (Either<Failure, List<Balance>>) -> Unit
    ) = koin.get<GetFundingSourcesUseCase>()
        .invoke(GetFundingSourcesUseCase.Params(cardId, forceRefresh, page, rows)) { callback(it) }

    override fun fetchCardFundingSource(
        cardId: String,
        forceRefresh: Boolean,
        callback: (Either<Failure, Balance>) -> Unit
    ) = koin.get<GetCardBalanceUseCase>().invoke(GetCardBalanceParams(cardId, forceRefresh)) { callback(it) }

    override fun setCardFundingSource(
        fundingSourceId: String,
        cardId: String,
        callback: (Either<Failure, Balance>) -> Unit
    ) = koin.get<SetCardFundingSourceUseCase>().invoke(SetCardBalanceParams(cardId, fundingSourceId)) { callback(it) }

    override fun addCardFundingSource(
        cardId: String,
        fundingSourceType: String,
        custodianType: String,
        credentialType: String,
        tokenId: String,
        callback: (Either<Failure, Balance>) -> Unit
    ) = koin.get<AddCardBalanceUseCase>().invoke(
        AddCardBalanceParams(cardId, fundingSourceType, custodianType, credentialType, tokenId)
    ) { callback(it) }

    override fun fetchNotificationPreferences(callback: (Either<Failure, NotificationPreferences>) -> Unit) =
        koin.get<GetNotificationPreferencesUseCase>().invoke(Unit) { callback(it) }

    override fun updateNotificationPreferences(
        preferences: NotificationPreferences,
        callback: (Either<Failure, NotificationPreferences>) -> Unit
    ) = koin.get<UpdateNotificationPreferencesUseCase>().invoke(preferences.preferences) { callback(it) }

    override fun fetchVoIPToken(cardId: String, actionSource: Action, callback: (Either<Failure, VoipCall>) -> Unit) =
        koin.get<SetupVoipCallUseCase>().invoke(SetupVoipCallParams(cardId, actionSource)) { callback(it) }

    override fun fetchProvisioningData(cardId: String, callback: (Either<Failure, ProvisioningData>) -> Unit) {
        return koin.get<GetProvisioningDataUseCase>()
            .invoke(GetProvisioningDataUseCase.Params(cardId)) { callback(it) }
    }

    override fun addPaymentSource(
        paymentSource: NewPaymentSource,
        callback: (Either<Failure, PaymentSource>) -> Unit
    ) {
        koin.get<AddPaymentSourceUseCase>().invoke(paymentSource) { callback(it) }
    }

    override fun getPaymentSources(
        callback: (Either<Failure, List<PaymentSource>>) -> Unit,
        limit: Int?,
        startingAfter: String?,
        endingBefore: String?
    ) {
        koin.get<GetPaymentSourcesUseCase>()
            .invoke(GetPaymentSourcesUseCase.Params(startingAfter, endingBefore, limit)) { callback(it) }
    }

    override fun deletePaymentSource(paymentSourceId: String, callback: (Either<Failure, Unit>) -> Unit) {
        koin.get<DeletePaymentSourceUseCase>().invoke(paymentSourceId) { callback(it) }
    }

    override fun pushFunds(
        balanceId: String,
        paymentSourceId: String,
        amount: Money,
        callback: (Either<Failure, Payment>) -> Unit
    ) {
        koin.get<PushFundsUseCase>().invoke(
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
        koin.get<SetCardPasscodeUseCase>().invoke(
            SetCardPasscodeUseCase.Params(
                cardId = cardId,
                passcode = passcode,
                verificationId = verificationId
            )
        ) { callback(it) }
    }

    override fun reviewAgreements(
        keys: List<String>,
        action: AgreementAction,
        callback: (Either<Failure, Unit>) -> Unit
    ) {
        koin.get<ReviewAgreementsUseCase>().invoke(ReviewAgreementsInput(keys, action)) { callback(it) }
    }

    override fun assignAchAccount(balanceId: String, callback: (Either<Failure, AchAccountDetails>) -> Unit) {
        koin.get<AssignAchAccountToBalanceUseCase>().invoke(balanceId) { callback(it) }
    }

    override fun getAchAccountDetails(balanceId: String, callback: (Either<Failure, AchAccountDetails>) -> Unit) {
        koin.get<GetAchAccountDetailsUseCase>().invoke(balanceId) { callback(it) }
    }

    override fun getOrderPhysicalCardConfig(cardId: String, callback: (Either<Failure, OrderPhysicalCardConfig>) -> Unit) {
        koin.get<GetOrderPhysicalCardConfigurationUseCase>().invoke(GetOrderPhysicalCardConfigurationUseCase.Params(cardId)) { callback(it) }
    }

    override fun orderPhysicalCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) {
        koin.get<OrderPhysicalCardUseCase>().invoke(OrderPhysicalCardUseCase.Params(cardId)) { callback(it) }
    }

    override fun p2pFindRecipient(
        phone: PhoneNumber?,
        email: String?,
        callback: (Either<Failure, CardHolderData>) -> Unit
    ) {
        koin.get<P2pFindRecipientUseCase>().invoke(P2pFindRecipientUseCase.Params(phone, email)) { callback(it) }
    }

    override fun p2pMakeTransfer(
        sourceId: String,
        recipientId: String,
        amount: Money,
        callback: (Either<Failure, P2pTransferResponse>) -> Unit
    ) {
        koin.get<P2pMakeTransfer>().invoke(P2pMakeTransfer.Params(sourceId, recipientId, amount)) { callback(it) }
    }
}
