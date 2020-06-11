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
import com.aptopayments.core.data.statements.MonthlyStatement
import com.aptopayments.core.data.statements.MonthlyStatementPeriod
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

/**
 * This SDK gives access to the Apto's mobile API, designed to be used from a mobile app. Using this SDK there's no need to integrate the API itself, all the API endpoints are exposed as simple to use methods, and the data returned by the API is properly encapsulated and easy to access.
 * With this SDK, you'll be able to onboard new users, issue cards, obtain card activity information and manage the card (set pin, freeze / unfreeze, etc.)
 * <p>In addition to this documentation, you may wish to take a look at
 * <a href="https://aptopayments.com/#/developers">the developer portal</a>.
 *
 */
interface AptoPlatformProtocol {

    /**
     * Fetches the project configuration, colors, sizes, etc
     * In addition, it gets the translations for the UI Sdk
     *
     * @param forceRefresh Boolean, forces an API call if true, otherwise gets the cached version if exists
     * @param callback Either<Failure, ContextConfiguration>
     */
    fun fetchContextConfiguration(forceRefresh: Boolean, callback: (Either<Failure, ContextConfiguration>) -> Unit)

    /**
     * Gets the details of a specific card program
     *
     * ```
     * Example:
     *
     * AptoPlatform.fetchCardProduct(cardProductId) {
     * it.either({ error ->
     *      // Do something with the error
     *  ),
     *  { cardProduct ->
     *      // cardProduct is a CardProduct object.
     *  })
     * }
     * ```
     *
     * @param cardProductId The card product that is being fetched
     * @param forceRefresh Forces the Sdk to get a new response from the API instead of the cached one (if exists)
     * @param callback Lambda called when the CardProduct response ended, Either<Failure, CardProduct>
     */
    fun fetchCardProduct(cardProductId: String, forceRefresh: Boolean, callback: (Either<Failure, CardProduct>) -> Unit)

    /**
     * Get a list of all the available card programs that can be used to issue cards
     *
     * ```
     * Example:
     *
     * AptoPlatform.fetchCardProducts {
     *  it.either({ error ->
     *      // Do something with the error
     *  ),
     *  { cardProducts ->
     *      // cardProducts is a list of CardProductSummary objects.
     *  })
     * }
     * ```
     *
     * @param callback Lambda called when the fetchCardProducts response is back, Either<Failure, List<CardProductSummary>>>
     */
    fun fetchCardProducts(callback: (Either<Failure, List<CardProductSummary>>) -> Unit)

    /**
     * Sets the configuration to get the list of transactions
     * This method persists the configuration
     *
     * @param enabled Boolean If false is given, when fetching the transaction list it will only get the Accepted
     * transactions otherwise all the transactions are going to be fetched
     */
    fun setIsShowDetailedCardActivityEnabled(enabled: Boolean)

    /**
     * Checks the current state of the configuration set in [setIsShowDetailedCardActivityEnabled],
     *
     * @return The result of the configuration previously set
     */
    fun isShowDetailedCardActivityEnabled(): Boolean

    /**
     * Use this method to specify a user session token to be used by the SDK.
     * This is optional, if a session token is not provided, the SDK will verify the user to obtain one.
     *
     * @param userToken String provided by the B2B API
     */
    fun setUserToken(userToken: String)

    /**
     * Checks if the SDK has a configured userToken
     * This token can be set by the {@link #setUserToken(String)} method or by using this SDK to login
     *
     * @return true if the user is logged in or the user token is set / false otherwise
     */
    fun userTokenPresent(): Boolean

    /**
     * Once the primary credential has been verified, you can use the following SDK method to create a new user
     *
     * ```
     * Example:
     *
     * AptoPlatform.createUser(userData) {
     *  it.either({ error ->
     *      // Do something with the error
     *  ),
     *  { user ->
     *      // user has been created
     *  })
     * }
     * ```
     *
     * @param userData a DataPointList withe the User Datapoints
     * @param custodianUid This parameter is optional
     * @param callback Lambda called when method ended, Either<Failure, User>
     */
    fun createUser(userData: DataPointList, custodianUid: String? = null, callback: (Either<Failure, User>) -> Unit)

    /**
     * Once the primary and secondary credentials have been verified, you can use the following SDK method to obtain
     * a user token for an existing use
     *
     * @param verifications List<Verification> containting the verifierd Credencials
     * @param callback Lambda called when method ended, Either<Failure, User>
     */
    fun loginUserWith(verifications: List<Verification>, callback: (Either<Failure, User>) -> Unit)

    /**
     * Updates the logged user info
     *
     * @param userData DataPointList containing the updated data
     * @param callback Lambda called when method ended, Either<Failure, User>
     */
    fun updateUserInfo(userData: DataPointList, callback: (Either<Failure, User>) -> Unit)

    /**
     * Method to subscribe to a Session Invalid listener fired when the API returns session invalid or user is logged out
     *
     * @param instance any object
     * @param callback Lambda called when session gets invalidated
     */
    fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit)

    /**
     * Method to unsubscribe to the Session Invalid listener,
     *
     * @param instance the same object you provided to subscribe
     */
    fun unsubscribeSessionInvalidListener(instance: Any)

    /**
     * Close the current user's session
     *
     */
    fun logout()

    // Oauth handling
    fun startOauthAuthentication(balanceType: AllowedBalanceType, callback: (Either<Failure, OAuthAttempt>) -> Unit)

    fun verifyOauthAttemptStatus(oAuthAttempt: OAuthAttempt, callback: (Either<Failure, OAuthAttempt>) -> Unit)

    fun saveOauthUserData(
        userData: DataPointList,
        allowedBalanceType: AllowedBalanceType,
        tokenId: String,
        callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit
    )

    fun fetchOAuthData(
        allowedBalanceType: AllowedBalanceType,
        tokenId: String,
        callback: (Either<Failure, OAuthUserDataUpdate>) -> Unit
    )

    // Verifications
    /**
     * Starts a Phone verification
     *
     * @param phoneNumber PhoneNumber with the user phone to send an SMS with the code
     * @param callback Lambda called when verification started
     */
    fun startPhoneVerification(phoneNumber: PhoneNumber, callback: (Either<Failure, Verification>) -> Unit)

    /**
     * Starts a Email verification
     *
     * @param email String with the user email to send the code
     * @param callback Lambda called when verification started
     */
    fun startEmailVerification(email: String, callback: (Either<Failure, Verification>) -> Unit)

    /**
     * This methods completes the verification
     *
     * @param verification Verification, if the status of the verification is Passed, means that the code was correct
     * if it contains a secondaryCredential value, means that it's an existing user and has to verify it's secondary credential
     * check {@link DataPoint.Type} for the different types
     * @param callback Lambda called when this step ended, it can be Failure when the code doesn't match or a Verification
     */
    fun completeVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit)

    /**
     * This method allows restarting a current verification
     *
     * @param verification Verification to restart
     * @param callback Lambda called when verification restarted, it can be
     */
    fun restartVerification(verification: Verification, callback: (Either<Failure, Verification>) -> Unit)

    // Card application handling
    fun applyToCard(cardProduct: CardProduct, callback: (Either<Failure, CardApplication>) -> Unit)

    fun fetchCardApplicationStatus(applicationId: String, callback: (Either<Failure, CardApplication>) -> Unit)

    fun setBalanceStore(
        applicationId: String,
        tokenId: String,
        callback: (Either<Failure, SelectBalanceStoreResult>) -> Unit
    )

    fun acceptDisclaimer(
        workflowObjectId: String,
        workflowAction: WorkflowAction,
        callback: (Either<Failure, Unit>) -> Unit
    )

    fun cancelCardApplication(applicationId: String, callback: (Either<Failure, Unit>) -> Unit)

    /**
     * This methods allows to issue a card
     *
     * @param applicationId String got from {@link fetchCardProducts((Either<Failure, List<CardProductSummary>>) -> Unit)}
     * or {@link fetchCardProduct(String, Boolean, (Either<Failure, CardProduct>) -> Unit)}
     * @param callback Lambda called when card has been issued returning Either Failure if was not successful or the Card if it was correct
     */
    fun issueCard(
        applicationId: String,
        additionalFields: Map<String, Any>? = null,
        callback: (Either<Failure, Card>) -> Unit
    )

    /**
     * This methods allows to issue a card providing different parameters
     *
     * @param cardProductIdString got from {@link fetchCardProducts((Either<Failure, List<CardProductSummary>>) -> Unit)}
     * or {@link fetchCardProduct(String, Boolean, (Either<Failure, CardProduct>) -> Unit)}
     * @param credential OAuthCredential
     * @param additionalFields can be used to send Apto additional data required to card issuance that is not captured
     * during the user creation process. For a list of allowed fields and values contact us
     * @param initialFundingSourceId specifies the id of the wallet that will be connected to the card when issued
     * @param callback Lambda called when card has been issued returning Either Failure if was not successful or the Card if it was correct
     */
    fun issueCard(
        cardProductId: String,
        credential: OAuthCredential?,
        additionalFields: Map<String, Any>? = null,
        initialFundingSourceId: String? = null,
        callback: (Either<Failure, Card>) -> Unit
    )

    // Card handling
    /**
     * This method is used to retrieve the list of the user cards
     *
     * @param callback Lambda called when the api call has been made returning Either Failure if there was an error
     * or a List of cards it it was successful
     */
    fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit)

    fun fetchFinancialAccount(accountId: String, forceRefresh: Boolean, callback: (Either<Failure, Card>) -> Unit)

    fun fetchCardDetails(cardId: String, callback: (Either<Failure, CardDetails>) -> Unit)

    /**
     * Physical cards need to be activated by providing an activation code that is sent to the cardholder.
     *
     * @param cardId String containing the Id of the card
     * @param code String with the provided code
     * @param callback Lambda called when the card has been activated returning Either Failure if there was an error
     * in the communication or an ActivatePhysicalCardResult with the response of the API
     */
    fun activatePhysicalCard(
        cardId: String,
        code: String,
        callback: (Either<Failure, ActivatePhysicalCardResult>) -> Unit
    )

    /**
     * Cards can be locked and unfreezed at any moment.
     * Transactions of a locked card will be rejected in the merchant's POS.
     *
     * @param cardId String containing the cardId of the card to be locked
     * @param callback Lambda called when call has been made returning Either Failure if there was an error
     * or Unit if the card was successfully Locked
     */
    fun lockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit)

    /**
     * Allows to unlock a previously locked card and to start accepting transactions again
     * if the transaction can go through
     *
     * @param cardId String containing the card to unlock
     * @param callback Lambda called when the call has been made returning Either Failure if there was an error
     * or the Card if the card was successfully unlocked
     */
    fun unlockCard(cardId: String, callback: (Either<Failure, Card>) -> Unit)

    /**
     * Changes the Card PIN
     *
     * @param cardId String containing the card to change the PIN to
     * @param pin String containing the new PIN
     * @param callback Lambda called when the call has been made returning Either Failure if there was an error
     * or the Card if the PIN was successfully changed
     */
    fun changeCardPin(cardId: String, pin: String, callback: (Either<Failure, Card>) -> Unit)

    /**
     * Gets a list of transactions made by the logged user with the given cardId
     *
     * @param cardId String containing the cardId of the transactions
     * @param filters TransactionListFilters with parameters about dates, mcc, amount of rows by page, etc.
     * @param forceRefresh If false is provided, then the SDK will get local transactions. Otherwise an API call will
     * be made and transactions will come from backend
     * @param clearCachedValues a Boolean that tells the SDK to clear the cached values after the call
     * @param callback Lambda called when the transactions has been fetched returning Either Failure if there was an error
     * or a list of Transactions if the fetching was correct
     */
    fun fetchCardTransactions(
        cardId: String,
        filters: TransactionListFilters,
        forceRefresh: Boolean,
        clearCachedValues: Boolean,
        callback: (Either<Failure, List<Transaction>>) -> Unit
    )

    /**
     * Obtains information about the monthly spendings of a given card, classified by Category
     *
     * @param cardId String containing the cardId
     * @param month String containing the month in English (i.e. April)
     * @param year String containing the year in numbers
     * @param callback Lambda called when the transactions has been fetched returning Either Failure if there was an error
     * or a MonthlySpending if the fetching was successful
     */
    fun cardMonthlySpending(
        cardId: String,
        month: String,
        year: String,
        callback: (Either<Failure, MonthlySpending>) -> Unit
    )

    /**
     * Get a monthly statement
     *
     * @param month Int of the desired month, starting in 1 for January
     * @param year Int of the desired year
     * @param callback Lambda called when the transactions has been fetched returning Either Failure if there was an error
     * or a MonthlyStatement if the fetching was successful
     */
    fun fetchMonthlyStatement(month: Int, year: Int, callback: (Either<Failure, MonthlyStatement>) -> Unit)

    /**
     * Fetches the range in which the monthly statements are processed
     *
     * @param callback Lambda called when the api call has been completed returning Either Failure if there was an error
     * or a MonthlyStatementPeriod containing the range data
     */
    fun fetchMonthlyStatementPeriod(callback: (Either<Failure, MonthlyStatementPeriod>) -> Unit)

    // Card funding sources handling
    fun fetchCardFundingSources(
        cardId: String,
        page: Int,
        rows: Int,
        forceRefresh: Boolean,
        callback: (Either<Failure, List<Balance>>) -> Unit
    )

    fun fetchCardFundingSource(cardId: String, forceRefresh: Boolean, callback: (Either<Failure, Balance>) -> Unit)

    fun setCardFundingSource(fundingSourceId: String, cardId: String, callback: (Either<Failure, Balance>) -> Unit)

    fun addCardFundingSource(
        cardId: String,
        fundingSourceType: String,
        custodianType: String,
        credentialType: String,
        tokenId: String,
        callback: (Either<Failure, Balance>) -> Unit
    )

    // Notification preferences handling

    /**
     * Users can be notified via push notifications regarding several events (transactions, card status changes, etc.)
     * This method fetches the user preferences
     *
     * @param callback
     */
    fun fetchNotificationPreferences(callback: (Either<Failure, NotificationPreferences>) -> Unit)

    /**
     * Users can be notified via push notifications regarding several events (transactions, card status changes, etc.)
     * This method updates the users preferences
     *
     * @param preferences
     * @param callback
     */
    fun updateNotificationPreferences(preferences: NotificationPreferences, callback: (Either<Failure, Unit>) -> Unit)

    // VoIP
    fun fetchVoIPToken(cardId: String, actionSource: Action, callback: (Either<Failure, VoipCall>) -> Unit)

    /**
     * Clears the local monthly spending Cache
     */
    fun clearMonthlySpendingCache()

    fun fetchProvisioningData(
        cardId: String,
        clientAppId: String,
        clientDeviceId: String,
        walletId: String,
        callback: (Either<Failure, ProvisioningData>) -> Unit
    )
}
