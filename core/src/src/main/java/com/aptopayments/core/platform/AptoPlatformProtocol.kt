package com.aptopayments.core.platform

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
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.repository.transaction.TransactionListFilters

interface AptoPlatformProtocol {

    // Configuration handling
    fun fetchContextConfiguration(forceRefresh: Boolean,
                                  callback: (Either<Failure, ContextConfiguration>) -> Unit)

    fun fetchCardProduct(cardProductId: String, forceRefresh: Boolean,
                         callback: (Either<Failure, CardProduct>) -> Unit)

    fun fetchCardProducts(callback: (Either<Failure, List<CardProductSummary>>) -> Unit)

    fun isShowDetailedCardActivityEnabled(): Boolean
    fun setIsShowDetailedCardActivityEnabled(enabled: Boolean)

    // User handling
    fun userTokenPresent(): Boolean

    fun setUserToken(userToken: String)

    fun createUser(userData: DataPointList, custodianUid: String? = null, callback: (Either<Failure, User>) -> Unit)

    fun loginUserWith(verifications: List<Verification>, callback: (Either<Failure, User>) -> Unit)

    fun updateUserInfo(userData: DataPointList, callback: (Either<Failure, User>) -> Unit)

    fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit)

    fun unsubscribeSessionInvalidListener(instance: Any)

    fun logout()

    // Oauth handling
    fun startOauthAuthentication(balanceType: AllowedBalanceType, callback: (Either<Failure, OAuthAttempt>) -> Unit)

    // TODO: use other UseCase passing CustodianType (Pivotal #167395532)
    fun verifyOauthAttemptStatus(oAuthAttempt: OAuthAttempt, callback: (Either<Failure, OAuthAttempt>) -> Unit)

    // TODO: use other UseCase passing Custodian (Pivotal #167395532)
    fun saveOauthUserData(userData: DataPointList, allowedBalanceType: AllowedBalanceType, tokenId: String,
                          callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit)

    // TODO: use other UseCase passing Custodian (Pivotal #167395532)
    fun fetchOAuthData(allowedBalanceType: AllowedBalanceType, tokenId: String,
                       callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit)

    // Verifications
    fun startPhoneVerification(phoneNumber: PhoneNumber, callback: (Either<Failure, Verification>) -> Unit)

    fun startEmailVerification(email: String, callback: (Either<Failure, Verification>) -> Unit)

    fun completeVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit)

    fun restartVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit)

    // Card application handling
    fun applyToCard(cardProduct: CardProduct, callback: (Either<Failure, CardApplication>) -> Unit)

    fun fetchCardApplicationStatus(applicationId: String, callback: (Either<Failure, CardApplication>) -> Unit)

    // TODO: use other UseCase passing Custodian (Pivotal #167395532)
    fun setBalanceStore(applicationId: String, tokenId: String,
                        callback: (Either<Failure, SelectBalanceStoreResult>) -> Unit)

    // TODO: create WorkflowObject and pass it instead of ID
    fun acceptDisclaimer(workflowObjectId: String, workflowAction: WorkflowAction,
                         callback: (Either<Failure, Unit>) -> Unit)

    fun cancelCardApplication(applicationId: String, callback: (Either<Failure, Unit>) -> Unit)

    fun issueCard(applicationId: String, callback: (Either<Failure, Card>) -> Unit)

    fun issueCard(cardProductId: String, credential: OAuthCredential?, additionalFields: Map<String, Any>? = null,
                  initialFundingSourceId: String? = null, callback: (Either<Failure, Card>) -> Unit)

    // Card handling
    fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit)

    // TODO: remove showDetails param
    fun fetchFinancialAccount(accountId: String, forceRefresh: Boolean, showDetails: Boolean,
                              callback: (Either<Failure, Card>) -> Unit)

    fun fetchCardDetails(cardId: String, callback: (Either<Failure, CardDetails>) -> Unit)

    fun activatePhysicalCard(cardId: String, code: String,
                             callback: (Either<Failure, ActivatePhysicalCardResult>) -> Unit)

    fun unlockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit)

    fun lockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit)

    fun changeCardPin(cardId: String, pin: String, callback: (Either<Failure, Card>) -> Unit)

    fun fetchCardTransactions(cardId: String, filters: TransactionListFilters,
                              forceRefresh: Boolean, clearCachedValues: Boolean,
                              callback: (Either<Failure, List<Transaction>>) -> Unit)

    fun cardMonthlySpending(cardId: String, month: String, year: String,
                            callback: (Either<Failure, MonthlySpending>) -> Unit)

    // Card funding sources handling
    fun fetchCardFundingSources(cardId: String, page: Int, rows: Int, forceRefresh: Boolean,
                                callback: (Either<Failure, List<Balance>>) -> Unit)

    fun fetchCardFundingSource(cardId: String, forceRefresh: Boolean, callback: (Either<Failure, Balance>) -> Unit)

    fun setCardFundingSource(fundingSourceId: String, cardId: String, callback: (Either<Failure, Balance>) -> Unit)

    // TODO: use other UseCase passing Custodian (Pivotal #167395532)
    fun addCardFundingSource(cardId: String, fundingSourceType: String, custodianType: String, credentialType: String,
                             tokenId: String, callback: (Either<Failure, Balance>) -> Unit)

    // Notification preferences handling
    fun fetchNotificationPreferences(callback: (Either<Failure, NotificationPreferences>) -> Unit)

    fun updateNotificationPreferences(preferences: NotificationPreferences,
                                      callback: (Either<Failure, Unit>) -> Unit)

    // VoIP
    fun fetchVoIPToken(cardId: String, actionSource: Action,
                       callback: (Either<Failure, VoipCall>) -> Unit)
}
