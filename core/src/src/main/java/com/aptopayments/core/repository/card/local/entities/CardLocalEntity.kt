package com.aptopayments.core.repository.card.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.aptopayments.core.data.card.*
import com.aptopayments.core.extension.ColorParserImpl
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.card.remote.entities.CardStyleEntity
import com.aptopayments.core.repository.card.remote.entities.FeaturesEntity
import com.google.gson.reflect.TypeToken

@Entity(tableName = "card")
class CardLocalEntity(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "account_id")
        val accountID: String,

        @ColumnInfo(name = "card_product_id")
        val cardProductID: String?,

        @ColumnInfo(name = "card_network")
        val cardNetwork: Card.CardNetwork?,

        @ColumnInfo(name = "last_four")
        val lastFourDigits: String,

        @ColumnInfo(name = "card_brand")
        val cardBrand: String?,

        @ColumnInfo(name = "card_issuer")
        val cardIssuer: String?,

        @ColumnInfo(name = "state")
        val state: Card.CardState,

        @ColumnInfo(name = "wait_list")
        val isWaitlisted: Boolean?,

        @ColumnInfo(name = "kyc_status")
        val kycStatus: KycStatus?,

        @ColumnInfo(name = "kyc_reason")
        val kycReason: List<String>?,

        @ColumnInfo(name = "ordered_status")
        val orderedStatus: Card.OrderedStatus,

        @ColumnInfo(name = "spendable_today")
        val spendableAmount: Money?,

        @ColumnInfo(name = "native_spendable_today")
        val nativeSpendableAmount: Money?,

        @ColumnInfo(name = "features")
        val features: Features?,

        @ColumnInfo(name = "card_style")
        val style: CardStyle?,

        @ColumnInfo(name = "cardholder")
        val cardHolder:  String? = null
) {
    fun toCard() = Card (
            accountID = accountID,
            cardProductID = cardProductID,
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
            cardHolder = cardHolder,
            cardStyle = style,
            features = features
    )

    companion object {
        fun fromCard(card: Card): CardLocalEntity {
            return CardLocalEntity(
                    accountID = card.accountID,
                    cardProductID = card.cardProductID,
                    cardNetwork = card.cardNetwork,
                    lastFourDigits = card.lastFourDigits,
                    cardBrand = card.cardBrand,
                    cardIssuer = card.cardIssuer,
                    state = card.state,
                    isWaitlisted = card.isWaitlisted,
                    kycStatus = card.kycStatus,
                    kycReason = card.kycReason,
                    orderedStatus = card.orderedStatus,
                    spendableAmount = card.spendableAmount,
                    nativeSpendableAmount = card.nativeSpendableAmount,
                    features = card.features,
                    style = card.cardStyle,
                    cardHolder = card.cardHolder
            )
        }
    }

    class Converters {

        @TypeConverter
        fun stringToCardState(value: String?): Card.CardState? =
                ApiCatalog.gson().fromJson(value, Card.CardState::class.java)

        @TypeConverter
        fun cardStateToString(cardState: Card.CardState?): String? =
                ApiCatalog.gson().toJson(cardState)

        @TypeConverter
        fun stringToCardStateOrderedStatus(value: String?): Card.OrderedStatus? =
                ApiCatalog.gson().fromJson(value, Card.OrderedStatus::class.java)

        @TypeConverter
        fun orderedStatusToString(orderedStatus: Card.OrderedStatus?): String? =
                ApiCatalog.gson().toJson(orderedStatus)

        @TypeConverter
        fun stringToKycStatus(value: String?): KycStatus? =
                ApiCatalog.gson().fromJson(value, KycStatus::class.java)

        @TypeConverter
        fun kycStatusToString(kycStatus: KycStatus?): String? =
                ApiCatalog.gson().toJson(kycStatus)

        @TypeConverter
        fun stringToCardNetwork(value: String?): Card.CardNetwork? =
                ApiCatalog.gson().fromJson(value, Card.CardNetwork::class.java)

        @TypeConverter
        fun cardNetworkToString(cardNetwork: Card.CardNetwork?): String? =
                ApiCatalog.gson().toJson(cardNetwork)

        @TypeConverter
        fun stringToCardStyle(value: String?): CardStyle? =
                ApiCatalog.gson().fromJson(value, CardStyleEntity::class.java)?.toCardStyle(ColorParserImpl())

        @TypeConverter
        fun cardStyleToString(cardStyle: CardStyle?): String? =
                ApiCatalog.gson().toJson(CardStyleEntity.from(cardStyle))

        @TypeConverter
        fun stringToCardStringList(value: String?): List<String>? =
                ApiCatalog.gson().fromJson(value, genericType<List<String>>())

        @TypeConverter
        fun stringListToString(stringList: List<String>?): String? =
                ApiCatalog.gson().toJson(stringList)

        @TypeConverter
        fun stringToFeatures(value: String?): Features? =
                ApiCatalog.gson().fromJson(value, FeaturesEntity::class.java)?.toFeatures()

        @TypeConverter
        fun featuresToString(features: Features?): String? =
                ApiCatalog.gson().toJson(FeaturesEntity.from(features))
    }
}

inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
