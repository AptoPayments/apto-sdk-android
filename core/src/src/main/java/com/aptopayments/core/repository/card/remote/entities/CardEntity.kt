package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.card.KycStatus
import com.aptopayments.core.extension.ColorParserImpl
import com.google.gson.annotations.SerializedName
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
        val nameOnCard: String? = null

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
            spendableAmount = spendableAmount?.toMoney(),
            nativeSpendableAmount = nativeSpendableAmount?.toMoney(),
            cardHolder = nameOnCard ?: "$cardholderFirstName $cardholderLastName",
            cardStyle = style?.toCardStyle(ColorParserImpl()),
            features = features?.toFeatures()
    )

    private fun parseCardNetwork(network: String?): Card.CardNetwork? {
        return network?.let {
            try {
                Card.CardNetwork.valueOf(it.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                Card.CardNetwork.UNKNOWN
            }
        }
    }

    private fun parseCardState(state: String): Card.CardState {
        return try {
            Card.CardState.valueOf(state.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            Card.CardState.UNKNOWN
        }
    }

    private fun parseKycStatus(status: String?): KycStatus? {
        return status?.let {
            try {
                KycStatus.valueOf(it.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                KycStatus.UNKNOWN
            }
        }
    }

    private fun parseOrderedStatus(status: String): Card.OrderedStatus {
        return try {
            Card.OrderedStatus.valueOf(status.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            Card.OrderedStatus.UNKNOWN
        }
    }
}
