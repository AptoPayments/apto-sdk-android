package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.KycStatus
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeParseException
import java.util.Locale

internal data class CardEntity(

    @SerializedName("account_id")
    val accountID: String = "",

    @SerializedName("card_product_id")
    val cardProductID: String? = null,

    @SerializedName("card_network")
    val cardNetwork: String? = null,

    @SerializedName("last_four")
    val lastFourDigits: String = "",

    @SerializedName("card_brand")
    val cardBrand: String? = null,

    @SerializedName("card_issuer")
    val cardIssuer: String? = null,

    @SerializedName("state")
    val state: String = "",

    @SerializedName("wait_list")
    val isWaitlisted: Boolean? = false,

    @SerializedName("kyc_status")
    val kycStatus: String? = null,

    @SerializedName("kyc_reason")
    val kycReason: List<String>? = null,

    @SerializedName("ordered_status")
    val orderedStatus: String = "",

    @SerializedName("format")
    val format: String = "",

    @SerializedName("spendable_today")
    val spendableAmount: MoneyEntity? = null,

    @SerializedName("native_spendable_today")
    val nativeSpendableAmount: MoneyEntity? = null,

    @SerializedName("features")
    val features: FeaturesEntity? = null,

    @SerializedName("card_style")
    val style: CardStyleEntity? = null,

    @SerializedName("cardholder_first_name")
    val cardholderFirstName: String? = null,

    @SerializedName("cardholder_last_name")
    val cardholderLastName: String? = null,

    @SerializedName("name_on_card")
    val nameOnCard: String? = null,

    @SerializedName("metadata")
    val metadata: String? = null,

    @SerializedName("issued_at")
    val issuedAt: String? = null,

) {
    fun toCard() = Card(
        accountID = accountID,
        cardProductID = cardProductID,
        cardNetwork = parseCardNetwork(cardNetwork),
        lastFourDigits = lastFourDigits,
        cardBrand = cardBrand,
        cardIssuer = cardIssuer,
        state = parseCardState(state),
        isWaitlisted = isWaitlisted,
        kycStatus = parseKycStatus(kycStatus),
        kycReason = kycReason,
        orderedStatus = parseOrderedStatus(orderedStatus),
        format = parseFormat(format),
        spendableAmount = spendableAmount?.toMoney(),
        nativeSpendableAmount = nativeSpendableAmount?.toMoney(),
        cardHolder = nameOnCard ?: "$cardholderFirstName $cardholderLastName",
        cardStyle = style?.toCardStyle(),
        features = features?.toFeatures(),
        metadata = metadata,
        issuedAt = issuedAt?.let {
            try {
                ZonedDateTime.parse(issuedAt)
            } catch (e: DateTimeParseException) {
                null
            }
        }
    )

    private fun parseCardNetwork(network: String?): Card.CardNetwork {
        return network?.let {
            try {
                Card.CardNetwork.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                Card.CardNetwork.UNKNOWN
            }
        } ?: Card.CardNetwork.UNKNOWN
    }

    private fun parseCardState(state: String): Card.CardState {
        return try {
            Card.CardState.valueOf(state.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            Card.CardState.UNKNOWN
        }
    }

    private fun parseKycStatus(status: String?): KycStatus? {
        return status?.let {
            try {
                KycStatus.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                KycStatus.UNKNOWN
            }
        }
    }

    private fun parseOrderedStatus(status: String): Card.OrderedStatus {
        return try {
            Card.OrderedStatus.valueOf(status.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            Card.OrderedStatus.UNKNOWN
        }
    }

    private fun parseFormat(format: String): Card.Format {
        return try {
            Card.Format.valueOf(format.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            Card.Format.UNKNOWN
        }
    }

    companion object {
        fun from(card: Card): CardEntity {
            return CardEntity(
                accountID = card.accountID,
                cardProductID = card.cardProductID,
                cardNetwork = card.cardNetwork.toString(),
                lastFourDigits = card.lastFourDigits,
                cardBrand = card.cardBrand,
                cardIssuer = card.cardIssuer,
                state = card.state.toString(),
                isWaitlisted = card.isWaitlisted,
                kycStatus = card.kycStatus.toString(),
                kycReason = card.kycReason,
                orderedStatus = card.orderedStatus.toString(),
                format = card.format.toString(),
                spendableAmount = MoneyEntity.from(card.spendableAmount),
                nativeSpendableAmount = MoneyEntity.from(card.nativeSpendableAmount),
                features = FeaturesEntity.from(features = card.features),
                style = CardStyleEntity.from(card.cardStyle),
                nameOnCard = card.cardHolder,
                metadata = card.metadata,
                issuedAt = card.issuedAt.toString()
            )
        }
    }
}
