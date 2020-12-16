package com.aptopayments.mobile.common

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.card.Card.CardNetwork.MASTERCARD
import com.aptopayments.mobile.data.card.Card.CardNetwork.VISA
import com.aptopayments.mobile.data.card.Card.CardState.ACTIVE
import com.aptopayments.mobile.data.card.Card.OrderedStatus.RECEIVED
import com.aptopayments.mobile.data.card.KycStatus.PASSED
import com.aptopayments.mobile.data.statements.MonthlyStatement
import com.aptopayments.mobile.data.statements.MonthlyStatementPeriod
import com.aptopayments.mobile.data.statements.StatementMonth
import com.aptopayments.mobile.repository.card.remote.entities.*
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
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
            selectBalanceStore = selectBalanceStore(),
            funding = fundingFeature(),
            passcode = passcodeFeature()
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
        features = null,
        metadata = null
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

    private fun fundingFeature(): FundingFeature {
        val fundingLimits = fundingLimits()
        return FundingFeature(true, listOf(VISA, MASTERCARD), fundingLimits, "soft descriptor")
    }

    private fun passcodeFeature(): CardPasscodeFeature {
        return CardPasscodeFeature(isEnabled = true, isPasscodeSet = true)
    }

    private fun fundingLimits(): FundingLimits {
        val singleLimit = FundingSingleLimit(money())
        return FundingLimits(singleLimit)
    }

    fun fundingLimitsEntity(): FundingLimitsEntity {
        val singleLimit = FundingSingleLimitEntity(moneyEntity())
        return FundingLimitsEntity(singleLimit)
    }

    fun monthlyStatement(): MonthlyStatement {
        return MonthlyStatement(
            "monthly_statement_12345",
            12,
            2019,
            "https://www.aptopayments.com/statement.pdf",
            ZonedDateTime.of(2020, 9, 9, 10, 0, 30, 0, ZoneOffset.UTC)
        )
    }

    fun monthlyStatementsPeriod(): MonthlyStatementPeriod {
        return MonthlyStatementPeriod(StatementMonth(1, 2019), StatementMonth(12, 2019))
    }

    private fun selectBalanceStore(): SelectBalanceStore {
        return SelectBalanceStore(allowedBalanceTypes = null)
    }

    private fun cardBackgroundStyle(url: URL = URL("http://www.google.es")): CardBackgroundStyle {
        return CardBackgroundStyle.Image(url = url, logo = null)
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
