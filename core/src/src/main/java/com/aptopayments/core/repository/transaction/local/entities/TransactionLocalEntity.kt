package com.aptopayments.core.repository.transaction.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.data.transaction.*
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.transaction.remote.entities.MerchantEntity
import com.aptopayments.core.repository.transaction.remote.entities.StoreEntity
import com.aptopayments.core.repository.transaction.remote.entities.TransactionSettlementEntity
import com.google.gson.reflect.TypeToken
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "transaction")
class TransactionLocalEntity(

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "transaction_id")
        val transactionId: String,

        @ColumnInfo(name = "account_id")
        val accountId: String,

        @ColumnInfo(name = "transaction_type")
        val transactionType: Transaction.TransactionType,

        @ColumnInfo(name = "created_at")
        val createdAt: ZonedDateTime,

        @ColumnInfo(name = "description")
        val transactionDescription: String?,

        @ColumnInfo(name = "last_message")
        val lastMessage: String?,

        @ColumnInfo(name = "decline_code")
        val declineCode: DeclineCode?,

        @ColumnInfo(name = "merchant")
        val merchant: Merchant?,

        @ColumnInfo(name = "store")
        val store: Store?,

        @ColumnInfo(name = "local_amount")
        val localAmount: Money?,

        @ColumnInfo(name = "billing_amount")
        val billingAmount: Money?,

        @ColumnInfo(name = "hold_amount")
        val holdAmount: Money?,

        @ColumnInfo(name = "cashback_amount")
        val cashbackAmount: Money?,

        @ColumnInfo(name = "fee_amount")
        val feeAmount: Money?,

        @ColumnInfo(name = "native_balance")
        val nativeBalance: Money?,

        @ColumnInfo(name = "settlement")
        val settlement: TransactionSettlement?,

        @ColumnInfo(name = "ecommerce")
        val ecommerce: Boolean?,

        @ColumnInfo(name = "international")
        val international: Boolean?,

        @ColumnInfo(name = "card_present")
        val cardPresent: Boolean?,

        @ColumnInfo(name = "emv")
        val emv: Boolean?,

        @ColumnInfo(name = "network")
        val cardNetwork: Card.CardNetwork?,

        @ColumnInfo(name = "state")
        val state: Transaction.TransactionState,

        @ColumnInfo(name = "adjustments")
        val adjustments: List<TransactionAdjustment>?,

        @ColumnInfo(name = "funding_source_name")
        val fundingSourceName: String?

) {
    fun toTransaction() = Transaction (
            transactionId = transactionId,
            transactionType = transactionType,
            createdAt = createdAt,
            transactionDescription = transactionDescription,
            lastMessage = lastMessage,
            declineCode = declineCode,
            merchant = merchant,
            store = store,
            localAmount = localAmount,
            billingAmount = billingAmount,
            holdAmount = holdAmount,
            cashbackAmount = cashbackAmount,
            feeAmount = feeAmount,
            nativeBalance = nativeBalance,
            settlement = settlement,
            ecommerce = ecommerce,
            international = international,
            cardPresent = cardPresent,
            emv = emv,
            cardNetwork = cardNetwork,
            state = state,
            adjustments = adjustments,
            fundingSourceName = fundingSourceName
    )

    companion object {
        fun fromTransaction(transaction: Transaction, accountId: String): TransactionLocalEntity {
            return TransactionLocalEntity(
                    transactionId = transaction.transactionId,
                    accountId = accountId,
                    transactionType = transaction.transactionType,
                    createdAt = transaction.createdAt,
                    transactionDescription = transaction.transactionDescription,
                    lastMessage = transaction.lastMessage,
                    declineCode = transaction.declineCode,
                    merchant = transaction.merchant,
                    store = transaction.store,
                    localAmount = transaction.localAmount,
                    billingAmount = transaction.billingAmount,
                    holdAmount = transaction.holdAmount,
                    cashbackAmount = transaction.cashbackAmount,
                    feeAmount = transaction.feeAmount,
                    nativeBalance =  transaction.nativeBalance,
                    settlement = transaction.settlement,
                    ecommerce = transaction.ecommerce,
                    international = transaction.international,
                    cardPresent = transaction.cardPresent,
                    emv = transaction.emv,
                    cardNetwork = transaction.cardNetwork,
                    state = transaction.state,
                    adjustments = transaction.adjustments,
                    fundingSourceName = transaction.fundingSourceName
            )
        }
    }

    class Converters {

        @TypeConverter
        fun stringToTransactionType(value: String?): Transaction.TransactionType? =
                ApiCatalog.gson().fromJson(value, Transaction.TransactionType::class.java)

        @TypeConverter
        fun transactionTypeToString(transactionType: Transaction.TransactionType?): String? =
                ApiCatalog.gson().toJson(transactionType)

        @TypeConverter
        fun stringToMerchant(value: String?): Merchant? =
                ApiCatalog.gson().fromJson(value, MerchantEntity::class.java).toMerchant()

        @TypeConverter
        fun merchantToString(merchant: Merchant?): String? =
                ApiCatalog.gson().toJson(MerchantEntity.from(merchant))

        @TypeConverter
        fun stringToStore(value: String?): Store? =
                ApiCatalog.gson().fromJson(value, StoreEntity::class.java)?.toStore()

        @TypeConverter
        fun storeToString(store: Store?): String? =
                ApiCatalog.gson().toJson(StoreEntity.from(store))

        @TypeConverter
        fun stringToTransactionSettlement(value: String?): TransactionSettlement? =
                ApiCatalog.gson().fromJson(value, TransactionSettlementEntity::class.java).toTransactionSettlement()

        @TypeConverter
        fun transactionSettlementToString(transactionSettlement: TransactionSettlement?): String? =
                ApiCatalog.gson().toJson(transactionSettlement)

        @TypeConverter
        fun stringToTransactionState(value: String?): Transaction.TransactionState? =
                ApiCatalog.gson().fromJson(value, Transaction.TransactionState::class.java)

        @TypeConverter
        fun transactionStateToString(transactionState: Transaction.TransactionState?): String? =
                ApiCatalog.gson().toJson(transactionState)

        @TypeConverter
        fun stringToAdjustmentsList(value: String?): List<TransactionAdjustment>? =
            ApiCatalog.gson().fromJson(value, genericType<List<TransactionAdjustment>>())

        @TypeConverter
        fun adjustmentsListToString(stringList: List<TransactionAdjustment>?): String? =
                ApiCatalog.gson().toJson(stringList)

        @TypeConverter
        fun stringToDeclineCode(value: String?): DeclineCode? =
                ApiCatalog.gson().fromJson(value, DeclineCode::class.java)

        @TypeConverter
        fun declineCodeToString(declineCode: DeclineCode?): String? =
                ApiCatalog.gson().toJson(declineCode)

        @TypeConverter
        fun stringToCardNetwork(value: String?): Card.CardNetwork? =
            ApiCatalog.gson().fromJson(value, Card.CardNetwork::class.java)

        @TypeConverter
        fun cardNetworkToString(cardNetwork: Card.CardNetwork?): String? =
            ApiCatalog.gson().toJson(cardNetwork)

        @TypeConverter
        fun zonedDateTimeToString(dateTime: ZonedDateTime?): String? = dateTime?.toString()

        @TypeConverter
        fun stringToZonedDateTime(value: String?): ZonedDateTime? = value?.let { ZonedDateTime.parse(value) }
    }
}

inline fun <reified T> genericType() = object: TypeToken<T>() {}.type
