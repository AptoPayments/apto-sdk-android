package com.aptopayments.core.platform

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.PhoneNumber
import com.aptopayments.core.data.card.*
import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.data.cardproduct.CardProductSummary
import com.aptopayments.core.data.config.ContextConfiguration
import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.oauth.OAuthCredential
import com.aptopayments.core.data.oauth.OAuthUserDataUpdate
import com.aptopayments.core.data.stats.MonthlySpending
import com.aptopayments.core.data.transaction.Transaction
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.core.data.voip.Action
import com.aptopayments.core.data.voip.VoipCall
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.data.workflowaction.WorkflowAction
import com.aptopayments.core.di.CoreApplicationComponent
import com.aptopayments.core.di.CoreApplicationModule
import com.aptopayments.core.di.DaggerCoreApplicationComponent
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.features.managecard.CardOptions
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.PushTokenRepository
import com.aptopayments.core.repository.UserSessionRepository
import com.aptopayments.core.repository.card.usecases.*
import com.aptopayments.core.repository.cardapplication.usecases.AcceptDisclaimerUseCase
import com.aptopayments.core.repository.cardapplication.usecases.SetBalanceStoreUseCase
import com.aptopayments.core.repository.config.usecases.GetCardProductParams
import com.aptopayments.core.repository.fundingsources.remote.usecases.GetFundingSourcesUseCase
import com.aptopayments.core.repository.oauth.usecases.RetrieveOAuthUserDataUseCase
import com.aptopayments.core.repository.oauth.usecases.SaveOAuthUserDataUseCase
import com.aptopayments.core.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.core.repository.transaction.TransactionListFilters
import com.aptopayments.core.repository.transaction.usecases.GetTransactionsUseCase
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallParams
import com.jakewharton.threetenabp.AndroidThreeTen
import java.io.File
import java.lang.ref.WeakReference
import java.lang.reflect.Modifier
import javax.inject.Inject

@SuppressLint("VisibleForTests")
object AptoPlatform : AptoPlatformProtocol {

    var delegate: AptoPlatformDelegate? = null
        set(newValue) {
            weakDelegate = WeakReference(newValue)
        }

    private var weakDelegate: WeakReference<AptoPlatformDelegate?>? = null
    private lateinit var networkHandlerWrapper: NetworkHandlerWrapper
    private lateinit var pushTokenRepositoryWrapper: PushTokenRepositoryWrapper
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    internal lateinit var useCasesWrapper: UseCasesWrapper
    @VisibleForTesting(otherwise = Modifier.PROTECTED)
    internal lateinit var appComponent: CoreApplicationComponent
    var cardOptions: CardOptions = CardOptions()
    internal var cacheDir: File? = null
    lateinit var application: Application

    fun initializeWithApiKey(application: Application,
                             apiKey: String,
                             environment: AptoSdkEnvironment) {
        this.application = application
        appComponent = DaggerCoreApplicationComponent
                .builder()
                .coreApplicationModule(CoreApplicationModule(application))
                .build()
        appComponent.inject(application)
        AndroidThreeTen.init(application)
        recreateAppComponent(application)
        networkHandlerWrapper = NetworkHandlerWrapper(appComponent)
        pushTokenRepositoryWrapper = PushTokenRepositoryWrapper(appComponent)
        useCasesWrapper = UseCasesWrapper(appComponent)

        ApiCatalog.set(apiKey, environment)
        cacheDir = application.cacheDir
        subscribeToSdkDeprecatedEvent()
    }

    fun userTokenPresent(context: Context): Boolean {
        val userSessionRepository = UserSessionRepository(context)
        return userSessionRepository.userToken.isNotEmpty()
    }

    private fun subscribeToSdkDeprecatedEvent() {
        networkHandlerWrapper.networkHandler.subscribeDeprecatedSdkListener(this) { deprecated ->
            if (deprecated) {
                weakDelegate?.get()?.sdkDeprecated()
            }
        }
    }

    fun registerFirebaseToken(firebaseToken: String) {
        pushTokenRepositoryWrapper.pushTokenRepository.pushToken = firebaseToken
    }

    fun recreateAppComponent(application: Application) {
        if (AptoPlatform::appComponent.isInitialized) return
        appComponent = DaggerCoreApplicationComponent
                .builder()
                .coreApplicationModule(CoreApplicationModule(application))
                .build()
        appComponent.inject(application)
    }

    fun clearMonthlySpendingCache() = useCasesWrapper.clearMonthlySpendingCacheUseCase(Unit)

    override fun fetchContextConfiguration(forceRefresh: Boolean,
                                           callback: (Either<Failure, ContextConfiguration>) -> Unit) =
            useCasesWrapper.getContextConfigurationUseCase(forceRefresh) { callback(it) }

    override fun fetchCardProduct(cardProductId: String, forceRefresh: Boolean,
                                  callback: (Either<Failure, CardProduct>) -> Unit) =
            useCasesWrapper.getCardProductUseCase(GetCardProductParams(
                    forceRefresh, cardProductId)) { callback(it) }

    override fun fetchCardProducts(callback: (Either<Failure, List<CardProductSummary>>) -> Unit) =
            useCasesWrapper.getCardProductsUseCase(Unit) { callback(it) }

    override fun createUser(userData: DataPointList, callback: (Either<Failure, User>) -> Unit) =
            useCasesWrapper.createUserUseCase(userData) { callback(it) }

    override fun loginUserWith(verifications: List<Verification>, callback: (Either<Failure, User>) -> Unit) =
            useCasesWrapper.loginUserUseCase(verifications) { callback(it) }

    override fun updateUserInfo(userData: DataPointList, callback: (Either<Failure, User>) -> Unit) =
            useCasesWrapper.updateUserDataUseCase(userData) { callback(it) }

    override fun startOauthAuthentication(balanceType: AllowedBalanceType,
                                          callback: (Either<Failure, OAuthAttempt>) -> Unit) =
            useCasesWrapper.startOAuthAuthenticationUseCase(balanceType) { callback(it) }

    override fun verifyOauthAttemptStatus(oAuthAttempt: OAuthAttempt,
                                          callback: (Either<Failure, OAuthAttempt>) -> Unit) =
            useCasesWrapper.getOAuthAttemptStatusUseCase(oAuthAttempt.id) { callback(it) }

    override fun saveOauthUserData(userData: DataPointList, allowedBalanceType: AllowedBalanceType, tokenId: String,
                                   callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit) =
            useCasesWrapper.saveOAuthUserDataUseCase(SaveOAuthUserDataUseCase.Params(
                    allowedBalanceType = allowedBalanceType,
                    dataPointList = userData,
                    tokenId = tokenId)) { callback(it) }

    override fun fetchOAuthData(allowedBalanceType: AllowedBalanceType, tokenId: String,
                                callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit) =
            useCasesWrapper.retrieveOAuthUserDataUseCase(RetrieveOAuthUserDataUseCase.Params(
                    allowedBalanceType = allowedBalanceType,
                    tokenId = tokenId)) { callback(it) }

    override fun startPhoneVerification(phoneNumber: PhoneNumber, callback: (Either<Failure, Verification>) -> Unit) =
            useCasesWrapper.startPhoneVerificationUseCase(phoneNumber) { callback(it) }

    override fun startEmailVerification(email: String, callback: (Either<Failure, Verification>) -> Unit) =
            useCasesWrapper.startEmailVerificationUseCase(email) { callback(it) }

    override fun completeVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
            useCasesWrapper.finishVerificationUseCase(verification) { callback(it) }

    override fun restartVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit) =
            useCasesWrapper.restartVerificationUseCase(verification) { callback(it) }

    override fun applyToCard(cardProduct: CardProduct, callback: (Either<Failure, CardApplication>) -> Unit) =
            useCasesWrapper.startCardApplicationUseCase(cardProduct.id) { callback(it) }

    override fun fetchCardApplicationStatus(applicationId: String,
                                            callback: (Either<Failure, CardApplication>) -> Unit) =
            useCasesWrapper.getCardApplicationUseCase(applicationId) { callback(it) }

    override fun setBalanceStore(applicationId: String, tokenId: String,
                                 callback: (Either<Failure, SelectBalanceStoreResult>) -> Unit) =
            useCasesWrapper.setBalanceStoreUseCase(SetBalanceStoreUseCase.Params(
                    applicationId, tokenId)) { callback(it) }

    override fun acceptDisclaimer(workflowObjectId: String, workflowAction: WorkflowAction,
                                  callback: (Either<Failure, Unit>) -> Unit) =
            useCasesWrapper.acceptDisclaimerUseCase(AcceptDisclaimerUseCase.Params(
                    workflowObjectId, workflowAction.actionId)) { callback(it) }

    override fun cancelCardApplication(applicationId: String, callback: (Either<Failure, Unit>) -> Unit) =
            useCasesWrapper.cancelCardApplicationUseCase(applicationId) { callback(it) }

    override fun issueCard(applicationId: String, callback: (Either<Failure, Card>) -> Unit) =
            useCasesWrapper.issueCardUseCase(applicationId) { callback(it) }

    override fun issueCard(cardProductId: String, credential: OAuthCredential?,
                           callback: (Either<Failure, Card>) -> Unit) = useCasesWrapper.issueCardCardProductUseCase(
            IssueCardUseCase.Params(
                    cardProductId = cardProductId,
                    credential = credential,
                    useBalanceV2 = cardOptions.useBalancesV2())
    )

    override fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit) =
            useCasesWrapper.getCardsUseCase(Unit) { callback(it) }

    override fun fetchFinancialAccount(accountId: String, forceRefresh: Boolean, showDetails: Boolean,
                                       callback: (Either<Failure, Card>) -> Unit) =
            useCasesWrapper.getCardUseCase(GetCardParams(accountId, showDetails, forceRefresh)) { callback(it) }

    override fun fetchCardDetails(cardId: String, callback: (Either<Failure, CardDetails>) -> Unit) =
            useCasesWrapper.getCardDetailsUseCase(cardId) { callback(it) }

    override fun activatePhysicalCard(cardId: String, code: String,
                                      callback: (Either<Failure, ActivatePhysicalCardResult>) -> Unit) =
            useCasesWrapper.activatePhysicalCardUseCase(ActivatePhysicalCardUseCase.Params(
                    cardId, code)) { callback(it) }

    override fun unlockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
            useCasesWrapper.unlockCardUseCase(cardId) { callback(it) }

    override fun lockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit) =
            useCasesWrapper.lockCardUseCase(cardId) { callback(it) }

    override fun changeCardPin(cardId: String, pin: String, callback: (Either<Failure, Card>) -> Unit) =
            useCasesWrapper.setPinUseCase(SetPinParams(cardId, pin)) { callback(it) }

    override fun fetchCardTransactions(cardId: String, filters: TransactionListFilters,
                                       forceRefresh: Boolean, clearCachedValues: Boolean,
                                       callback: (Either<Failure, List<Transaction>>) -> Unit) =
            useCasesWrapper.getTransactionsUseCase(GetTransactionsUseCase.Params(
                    cardId = cardId, filters = filters, forceApiCall = forceRefresh,
                    clearCachedValues = clearCachedValues)) { callback(it) }

    override fun cardMonthlySpending(cardId: String, month: String, year: String,
                                     callback: (Either<Failure, MonthlySpending>) -> Unit) =
            useCasesWrapper.getMonthlySpendingUseCase(GetMonthlySpendingUseCase.Params(
                    cardId = cardId, month = month, year = year)) { callback(it) }

    override fun fetchCardFundingSources(cardId: String, forceRefresh: Boolean,
                                         callback: (Either<Failure, List<Balance>>) -> Unit) =
            useCasesWrapper.getFundingSourcesUseCase(GetFundingSourcesUseCase.Params(
                    cardId, forceRefresh)) { callback(it) }

    override fun fetchCardFundingSource(cardId: String, forceRefresh: Boolean,
                                        callback: (Either<Failure, Balance>) -> Unit) =
            useCasesWrapper.getCardBalanceUseCase(GetCardBalanceParams(cardId, forceRefresh)) { callback(it) }

    override fun setCardFundingSource(fundingSourceId: String, cardId: String,
                                      callback: (Either<Failure, Balance>) -> Unit) =
            useCasesWrapper.setFundingSourcesUseCase(SetCardBalanceParams(cardId, fundingSourceId)) { callback(it) }

    override fun addCardFundingSource(cardId: String, fundingSourceType: String, custodianType: String,
                                      credentialType: String, tokenId: String,
                                      callback: (Either<Failure, Balance>) -> Unit) =
            useCasesWrapper.addCardBalanceUseCase(AddCardBalanceParams(cardId, fundingSourceType, custodianType,
                    credentialType, tokenId)) { callback(it) }

    override fun fetchNotificationPreferences(callback: (Either<Failure, NotificationPreferences>) -> Unit) =
            useCasesWrapper.getNotificationPreferencesUseCase(Unit) { callback(it) }

    override fun updateNotificationPreferences(preferences: NotificationPreferences,
                                               callback: (Either<Failure, Unit>) -> Unit) =
            useCasesWrapper.updateNotificationPreferencesUseCase(preferences.preferences!!) { callback(it) }

    override fun fetchVoIPToken(cardId: String, actionSource: Action, callback: (Either<Failure, VoipCall>) -> Unit) =
            useCasesWrapper.setupVoipCallUseCase(SetupVoipCallParams(cardId, actionSource)) { callback(it) }
}

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class NetworkHandlerWrapper @Inject constructor(appComponent: CoreApplicationComponent) {
    @Inject lateinit var networkHandler: NetworkHandler
    init {
        appComponent.inject(this)
    }
}

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class PushTokenRepositoryWrapper @Inject constructor(appComponent: CoreApplicationComponent) {
    @Inject lateinit var pushTokenRepository: PushTokenRepository
    init {
        appComponent.inject(this)
    }
}
