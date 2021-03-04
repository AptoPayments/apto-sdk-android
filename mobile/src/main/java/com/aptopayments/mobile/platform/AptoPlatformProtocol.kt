package com.aptopayments.mobile.platform

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
import com.aptopayments.mobile.data.user.agreements.AgreementAction
import com.aptopayments.mobile.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.mobile.data.voip.Action
import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.data.workflowaction.WorkflowAction
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.repository.transaction.TransactionListFilters

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

    fun currentToken(): AccessToken?

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
     * @param userData a DataPointList withe the User Datapoints.
     * @param custodianUid This parameter is optional.
     * @param metadata Metadata that will be saved with the user (Optional Parameter).
     * @param callback Lambda called when method ended, Either<Failure, User>
     */
    fun createUser(
        userData: DataPointList,
        custodianUid: String? = null,
        metadata: String? = null,
        callback: (Either<Failure, User>) -> Unit
    )

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
     * Starts a verification with the primary credential
     *
     * @param callback Lambda called when verification started
     */
    fun startPrimaryVerification(callback: (Either<Failure, Verification>) -> Unit)

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
    fun applyToCard(
        cardProduct: CardProduct,
        callback: (Either<Failure, CardApplication>) -> Unit
    )

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
     * @param additionalFields Map<String, Any> (Optional)(Deprecated)
     * @param metadata String
     * @param callback Lambda called when card has been issued returning Either Failure if was not successful or the Card if it was correct
     */
    fun issueCard(
        applicationId: String,
        additionalFields: Map<String, Any>? = null,
        metadata: String? = null,
        callback: (Either<Failure, Card>) -> Unit
    )

    /**
     * This methods allows to issue a card providing different parameters
     *
     * @param cardProductId String got from {@link fetchCardProducts((Either<Failure, List<CardProductSummary>>) -> Unit)}
     * or {@link fetchCardProduct(String, Boolean, (Either<Failure, CardProduct>) -> Unit)}
     * @param credential OAuthCredential
     * @param additionalFields can be used to send Apto additional data required to card issuance that is not captured
     * during the user creation process. For a list of allowed fields and values contact us
     * @param initialFundingSourceId specifies the id of the wallet that will be connected to the card when issued
     * @param callback Lambda called when card has been issued returning Either Failure if was not successful or the Card if it was correct
     */
    @Deprecated("Please use issueCard with applicationId")
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
    @Deprecated("To obtain the list of cards please use fetchCards with pagination")
    fun fetchCards(callback: (Either<Failure, List<Card>>) -> Unit)

    /**
     * This method is used to retrieve the list of the user cards
     *
     * @param pagination [ListPagination] Indicates the API how to filter the results (Optional Parameter).
     * @param callback Lambda called when the api call has been made returning Either Failure if there was an error.
     * or a List of cards it it was successful
     */
    fun fetchCards(pagination: ListPagination? = null, callback: (Either<Failure, PaginatedList<Card>>) -> Unit)

    @Deprecated("To obtain card data, please use fetchCard")
    fun fetchFinancialAccount(accountId: String, forceRefresh: Boolean, callback: (Either<Failure, Card>) -> Unit)

    fun fetchCard(cardId: String, forceRefresh: Boolean, callback: (Either<Failure, Card>) -> Unit)

    @Deprecated(message = "To show the card data to your users, please use the Apto PCI SDK")
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
    @Deprecated(message = "Use the version with Integer parameters")
    fun cardMonthlySpending(
        cardId: String,
        month: String,
        year: String,
        callback: (Either<Failure, MonthlySpending>) -> Unit
    )

    /**
     * Obtains information about the monthly spendings of a given card, classified by Category
     *
     * @param cardId String containing the cardId
     * @param month Int containing the month number, being 01 for January
     * @param year Int containing the year in numbers, i.e. 2020
     * @param callback Lambda called when the transactions has been fetched returning Either Failure if there was an error
     * or a MonthlySpending if the fetching was successful
     */
    fun cardMonthlySpending(
        cardId: String,
        month: Int,
        year: Int,
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
    fun updateNotificationPreferences(
        preferences: NotificationPreferences,
        callback: (Either<Failure, NotificationPreferences>) -> Unit
    )

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

    /**
     * Adds a payment source for Loading funds into the account
     *
     * @param paymentSource [NewPaymentSource] to be added to the list
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or a PaymentSource if the task was successful
     */
    fun addPaymentSource(paymentSource: NewPaymentSource, callback: (Either<Failure, PaymentSource>) -> Unit)

    /**
     * Gets a list with all the payment sources already added
     *
     * @param startingAfter Optional Parameter. Return the page starting right after the specified element. Optional Parameter.
     * @param endingBefore Optional Parameter. Return the page ending before the specified element
     * @param limit Optional Parameter. A limit on the number of objects to be returned. Default value is 25. Max allowed value is 50.
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or a List<PaymentSource> if the task was successful
     */
    fun getPaymentSources(
        callback: (Either<Failure, List<PaymentSource>>) -> Unit,
        limit: Int? = null,
        startingAfter: String? = null,
        endingBefore: String? = null
    )

    /**
     * Deletes a payment source
     *
     * @param paymentSourceId String Id of the payment source that will be deleted
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or Unit if the task was successful
     */
    fun deletePaymentSource(
        paymentSourceId: String,
        callback: (Either<Failure, Unit>) -> Unit
    )

    /**
     * Pushes money from a payment source to an Apto card,
     * the provided paymentSourceId will be selected as preferred when the operation ends
     *
     * @param balanceId String containing the Id of the balance that will be funded
     * @param paymentSourceId String containing the Id of the source that the money will be taken from
     * @param amount Money that represents the amount and currency of the funds that will be taken
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or the Payment if the task was successful
     */
    fun pushFunds(
        balanceId: String,
        paymentSourceId: String,
        amount: Money,
        callback: (Either<Failure, Payment>) -> Unit
    )

    /**
     * Sets a passcode to the card
     *
     * @param cardId String containing the Id of the card that will have the passcode
     * @param passcode String containing the passcode, it should be numeric with 4 digits
     * @param verificationId String (Optional) Verification_id provided by api-call if needed
     */
    fun setCardPasscode(
        cardId: String,
        passcode: String,
        verificationId: String? = null,
        callback: (Either<Failure, Unit>) -> Unit
    )

    /**
     * Allows send the user decision on a certain agreement
     * A certain Agreement can be Accepted or Rejected
     *
     * @param keys The agreement keys that is being reviewed
     * @param action The action that the user has taken over the agreement
     */
    fun reviewAgreements(
        keys: List<String>,
        action: AgreementAction,
        callback: (Either<Failure, Unit>) -> Unit
    )

    /**
     * Assigns an Ach Account to the provided balance
     * If the agreements have not been approved then Failure will be returned
     * If an account has already been assigned, then Failure will be returned
     *
     * @param balanceId the id of the balance that the Ach Account will be created to
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or AchAccountDetails if the task was successful
     */
    fun assignAchAccount(balanceId: String, callback: (Either<Failure, AchAccountDetails>) -> Unit)

    /**
     * Gets the Ach Account details for the balanceId provided
     * If the balanceId has no assigned account, then Failure will be returned
     *
     * @param balanceId the id of the balance that the Ach Account will be created to
     * @param callback Lambda called when the task has ended returning Either Failure if there was an error
     * or AchAccountDetails if the task was successful
     */
    fun getAchAccountDetails(balanceId: String, callback: (Either<Failure, AchAccountDetails>) -> Unit)
}
