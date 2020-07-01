package com.aptopayments.mobile.common

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.card.Card.CardNetwork.VISA
import com.aptopayments.mobile.data.card.Card.CardState.ACTIVE
import com.aptopayments.mobile.data.card.Card.OrderedStatus.RECEIVED
import com.aptopayments.mobile.data.card.KycStatus.PASSED
import com.aptopayments.mobile.repository.card.remote.entities.*
import java.net.URL

internal object ModelDataProvider {

    fun money(amount: Double = 1.0, currency: String = "GBP"): Money {
        return Money(amount = amount, currency = currency)
    }

    fun features(): Features {
        return Features(
            activation = activation(),
            getPin = getPin(),
            setPin = setPin(),
            ivrSupport = ivr(),
            selectBalanceStore = selectBalanceStore()
        )
    }

    fun cardStyle(): CardStyle {
        return CardStyle(
            background = cardBackgroundStyle(),
            textColor = 0,
            balanceSelectorAsset = URL("https://www.aptopayments.com")
        )
    }

    fun cardEntity(
        accountID: String = "TEST_ACCOUNT_ID",
        cardNetwork: String = "TEST_CARD_NETWORK",
        lastFourDigits: String = "TEST_LAST_FOUR_DIGITS",
        cardBrand: String = "TEST_CARD_BRAND",
        cardIssuer: String = "TEST_CARD_ISSUER",
        state: String = "TEST_STATE",
        isWaitlisted: Boolean = false,
        kycStatus: String = "TEST_KYC_STATUS",
        kycReason: List<String> = listOf("KYC_REASON"),
        orderedStatus: String = "TEST_ORDERED_STATUS",
        spendableAmount: MoneyEntity = moneyEntity(),
        nativeSpendableAmount: MoneyEntity = moneyEntity(),
        features: FeaturesEntity = featuresEntity(),
        style: CardStyleEntity = cardStyleEntity(),
        cardholderFirstName: String = "TEST_CARDHOLDER_FIRST_NAME",
        cardholderLastName: String = "TEST_CARDHOLDER_LAST_NAME",
        nameOnCard: String? = "TEST_NAME_ON_CARD"
    ): CardEntity {
        return CardEntity(
            accountID = accountID,
            cardNetwork = cardNetwork,
            lastFourDigits = lastFourDigits,
            cardBrand = cardBrand,
            cardIssuer = cardIssuer,
            state = state,
            isWaitlisted = isWaitlisted,
            kycStatus = kycStatus,
            kycReason = kycReason,
            orderedStatus = orderedStatus,
            spendableAmount = spendableAmount,
            nativeSpendableAmount = nativeSpendableAmount,
            features = features,
            style = style,
            cardholderFirstName = cardholderFirstName,
            cardholderLastName = cardholderLastName,
            nameOnCard = nameOnCard
        )
    }

    val card = Card(
        accountID = "account_id",
        cardProductID = "cardProductId",
        cardNetwork = VISA,
        lastFourDigits = "1234",
        cardBrand = "brand",
        cardIssuer = "Apto",
        state = ACTIVE,
        isWaitlisted = false,
        cardStyle = null,
        kycStatus = PASSED,
        kycReason = null,
        orderedStatus = RECEIVED,
        spendableAmount = Money(currency = "USD", amount = 1000.0),
        nativeSpendableAmount = Money(currency = "BTC", amount = 0.1),
        cardHolder = "Cardholder Name",
        features = null
    )

    private fun activation(): Activation {
        return Activation(ivrPhone = phone())
    }

    fun phone(countryCode: String = "1", phoneNumber: String = "9366666743"): PhoneNumber {
        return PhoneNumber(countryCode = countryCode, phoneNumber = phoneNumber)
    }

    private fun ivr(
        status: FeatureStatus = FeatureStatus.ENABLED,
        countryCode: String = "1",
        phoneNumber: String = "9366666743"
    ): Ivr {
        return Ivr(status = status, ivrPhone = phone(countryCode = countryCode, phoneNumber = phoneNumber))
    }

    fun setPin(status: FeatureStatus = FeatureStatus.ENABLED): SetPin {
        return SetPin(status = status)
    }

    fun getPin(status: FeatureStatus = FeatureStatus.ENABLED, type: FeatureType = FeatureType.Api()): GetPin {
        return GetPin(status = status, type = type)
    }

    private fun selectBalanceStore(): SelectBalanceStore {
        return SelectBalanceStore(allowedBalanceTypes = null)
    }

    private fun cardBackgroundStyle(url: URL = URL("http://www.google.es")): CardBackgroundStyle {
        return CardBackgroundStyle.Image(url = url)
    }

    private fun moneyEntity(amount: Double = 1.0, currency: String = "GBP"): MoneyEntity {
        return MoneyEntity(amount = amount, currency = currency)
    }

    private fun featuresEntity(): FeaturesEntity {
        return FeaturesEntity()
    }

    private fun cardStyleEntity(): CardStyleEntity {
        return CardStyleEntity(background = cardBackgroundStyleEntity())
    }

    private fun cardBackgroundStyleEntity(): CardBackgroundStyleEntity {
        return CardBackgroundStyleEntity()
    }
}
