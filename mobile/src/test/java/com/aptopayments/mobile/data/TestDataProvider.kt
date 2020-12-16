package com.aptopayments.mobile.data

import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.cardproduct.CardProduct
import com.aptopayments.mobile.data.config.Branding
import com.aptopayments.mobile.data.config.ContextConfiguration
import com.aptopayments.mobile.data.config.ProjectConfiguration
import com.aptopayments.mobile.data.geo.Country
import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.oauth.OAuthAttemptStatus
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdateResult
import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.data.user.DataPoint
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.platform.AptoSdkEnvironment
import com.aptopayments.mobile.repository.transaction.local.entities.TransactionLocalEntity
import org.mockito.Mockito
import org.threeten.bp.ZonedDateTime
import java.net.URL

internal class TestDataProvider {

    companion object {
        fun provideOAuthAttempt() = OAuthAttempt(
            id = "",
            url = URL("http://www.google.es"),
            status = OAuthAttemptStatus.PENDING,
            userData = DataPointList(),
            tokenId = "",
            error = null,
            errorMessage = null
        )

        fun provideContextConfiguration() = ContextConfiguration(
            projectConfiguration = ProjectConfiguration(
                name = "",
                summary = "",
                branding = Branding.createDefault(),
                labels = hashMapOf(),
                allowedCountries = arrayListOf(Country("US")),
                supportEmailAddress = "",
                trackerAccessToken = "",
                isTrackerActive = false,
                primaryAuthCredential = DataPoint.Type.PHONE
            )
        )

        fun provideContextConfigurationEmail() = ContextConfiguration(
            projectConfiguration = ProjectConfiguration(
                name = "",
                summary = "",
                branding = Branding.createDefault(),
                labels = hashMapOf(),
                allowedCountries = arrayListOf(Country("US")),
                supportEmailAddress = "",
                trackerAccessToken = "",
                isTrackerActive = false,
                primaryAuthCredential = DataPoint.Type.EMAIL
            )
        )

        fun provideCardProduct() = CardProduct(
            cardholderAgreement = null,
            privacyPolicy = null,
            termsAndConditions = null,
            faq = null,
            waitlistBackgroundImage = null,
            waitlistBackgroundColor = null,
            waitlistAsset = null
        )

        fun provideAllowedBalanceType() = AllowedBalanceType(
            balanceType = "type",
            baseUri = URL("http://www.aptopayments.com")
        )

        fun provideOAuthUserData() = OAuthUserDataUpdate(
            result = OAuthUserDataUpdateResult.VALID,
            userData = DataPointList()
        )

        fun provideCardApplicationId() = "bestCardApplicationIdEver"

        fun provideCardId() = "crd_thebestcardId"

        fun provideVerification() = Verification("", "")

        fun <T> anyObject(): T = Mockito.any<T>()

        fun provideCard(
            accountID: String = "",
            cardProductID: String = "",
            cardNetwork: Card.CardNetwork = Card.CardNetwork.UNKNOWN,
            lastFourDigits: String = "",
            cardBrand: String = "",
            cardIssuer: String = "",
            state: Card.CardState = Card.CardState.UNKNOWN,
            isWaitlisted: Boolean? = false,
            cardStyle: CardStyle? = null,
            kycStatus: KycStatus? = KycStatus.UNKNOWN,
            kycReason: List<String>? = null,
            orderedStatus: Card.OrderedStatus = Card.OrderedStatus.UNKNOWN,
            spendableAmount: Money? = null,
            nativeSpendableAmount: Money? = null,
            cardHolder: String = "",
            features: Features? = null
        ) = Card(
            accountID = accountID,
            cardProductID = cardProductID,
            cardNetwork = cardNetwork,
            lastFourDigits = lastFourDigits,
            cardBrand = cardBrand,
            cardIssuer = cardIssuer,
            state = state,
            isWaitlisted = isWaitlisted,
            cardStyle = cardStyle,
            kycStatus = kycStatus,
            kycReason = kycReason,
            orderedStatus = orderedStatus,
            spendableAmount = spendableAmount,
            nativeSpendableAmount = nativeSpendableAmount,
            cardHolder = cardHolder,
            features = features,
            metadata = null
        )

        fun provideUser(userData: DataPointList?) = User(userId = "user_id", token = "token", userData = userData)

        fun provideCorrectOAuthAttemptEntityJsonDataPoint() = "{\n" +
            "    \"type\": \"oauth_attempt\",\n" +
            "    \"id\": \"entity_6b1621b46e7fb330\",\n" +
            "    \"status\": \"passed\",\n" +
            "    \"tokens\": {\n" +
            "        \"access\": \"<<TOKEN>>\",\n" +
            "        \"refresh\": null\n" +
            "    },\n" +
            "    \"oauth_token_id\": \"entity_b54fae8eba90ad74\",\n" +
            "    \"user_data\": {\n" +
            "        \"type\": \"list\",\n" +
            "        \"data\": [\n" +
            "            {\n" +
            "                \"type\": \"name\",\n" +
            "                \"first_name\": \"John\",\n" +
            "                \"last_name\": \"Doe\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"id_document\",\n" +
            "                \"doc_type\": \"ssn\",\n" +
            "                \"value\": \"111119999\",\n" +
            "                \"country\": \"US\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"email\",\n" +
            "                \"email\": \"john@doe.com\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false,\n" +
            "                \"verification\": null\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"birthdate\",\n" +
            "                \"date\": \"1977-11-22\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false,\n" +
            "                \"verification\": null\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"address\",\n" +
            "                \"address_id\": null,\n" +
            "                \"street_one\": \"Carrer Asd 160\",\n" +
            "                \"street_two\": null,\n" +
            "                \"locality\": \"Barcelona\",\n" +
            "                \"region\": \"B\",\n" +
            "                \"postal_code\": \"08036\",\n" +
            "                \"country\": \"ES\",\n" +
            "                \"primary\": true,\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false\n" +
            "            }\n" +
            "        ],\n" +
            "        \"page\": 0,\n" +
            "        \"rows\": 5,\n" +
            "        \"has_more\": false,\n" +
            "        \"total_count\": 5\n" +
            "    },\n" +
            "    \"error\": null,\n" +
            "    \"error_message\": null\n" +
            "}"

        fun provideOAuthAttemptEntityJsonWithUnknownDataPoint() = "{\n" +
            "    \"type\": \"oauth_attempt\",\n" +
            "    \"id\": \"entity_6b1621b46e7fb330\",\n" +
            "    \"status\": \"passed\",\n" +
            "    \"tokens\": {\n" +
            "        \"access\": \"<<TOKEN>>\",\n" +
            "        \"refresh\": null\n" +
            "    },\n" +
            "    \"oauth_token_id\": \"entity_b54fae8eba90ad74\",\n" +
            "    \"user_data\": {\n" +
            "        \"type\": \"list\",\n" +
            "        \"data\": [\n" +
            "            {\n" +
            "                \"type\": \"birthdate\",\n" +
            "                \"date\": \"1977-11-22\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false,\n" +
            "                \"verification\": null\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"UNKNOWN1\",\n" +
            "                \"first_name\": \"John\",\n" +
            "                \"last_name\": \"Doe\",\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false\n" +
            "            },\n" +
            "            {\n" +
            "                \"type\": \"address\",\n" +
            "                \"address_id\": null,\n" +
            "                \"street_one\": \"Carrer Asd 160\",\n" +
            "                \"street_two\": null,\n" +
            "                \"locality\": \"Barcelona\",\n" +
            "                \"region\": \"B\",\n" +
            "                \"postal_code\": \"08036\",\n" +
            "                \"country\": \"ES\",\n" +
            "                \"primary\": true,\n" +
            "                \"verified\": false,\n" +
            "                \"not_specified\": false\n" +
            "            }\n" +
            "        ],\n" +
            "        \"page\": 0,\n" +
            "        \"rows\": 3,\n" +
            "        \"has_more\": false,\n" +
            "        \"total_count\": 3\n" +
            "    },\n" +
            "    \"error\": null,\n" +
            "    \"error_message\": null\n" +
            "}"

        fun provideCorrectIdDocumentDataPointEntity() = "{\n" +
            "\"type\": \"id_document\",\n" +
            "\"doc_type\": \"ssn\",\n" +
            "\"value\": \"111119999\",\n" +
            "\"country\": \"US\",\n" +
            "\"verified\": false,\n" +
            "\"not_specified\": false\n" +
            "}"

        fun provideEnvironment() = AptoSdkEnvironment.STG

        fun provideMoney(amount: Double = 10.0, currency: String = "USD") = Money(currency, amount)

        fun provideAPiKey() = "api_key_123"

        fun provideTransaction(
            transactionId: String = "transaction_1234",
            createdAt: ZonedDateTime = ZonedDateTime.now()
        ) = Transaction(
            transactionId,
            transactionType = Transaction.TransactionType.PURCHASE,
            createdAt = createdAt,
            transactionDescription = null,
            lastMessage = null,
            declineCode = null,
            merchant = null,
            store = null,
            localAmount = provideMoney(),
            billingAmount = provideMoney(),
            holdAmount = provideMoney(0.0),
            cashbackAmount = provideMoney(0.0),
            feeAmount = provideMoney(0.0),
            nativeBalance = provideMoney(),
            settlement = null,
            ecommerce = null,
            international = null,
            cardPresent = null,
            emv = null,
            cardNetwork = Card.CardNetwork.VISA,
            state = Transaction.TransactionState.COMPLETE,
            adjustments = null,
            fundingSourceName = null
        )

        fun provideTransactionLocalEntity(
            transactionId: String = "transaction_1234",
            createdAt: ZonedDateTime = ZonedDateTime.now(),
            cardId: String = "card_1234"
        ) = TransactionLocalEntity.fromTransaction(provideTransaction(transactionId, createdAt), cardId)
    }
}
