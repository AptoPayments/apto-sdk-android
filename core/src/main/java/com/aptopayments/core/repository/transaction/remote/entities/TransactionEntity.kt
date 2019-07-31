package com.aptopayments.core.repository.transaction.remote.entities

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.transaction.DeclineCode
import com.aptopayments.core.data.transaction.Transaction
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

internal data class TransactionEntity (

        @SerializedName("id")
        val transactionId: String = "",

        @SerializedName("transaction_type")
        val transactionType: String? = null,

        @SerializedName("created_at")
        val createdAt: Double,

        @SerializedName("description")
        val transactionDescription: String?,

        @SerializedName("last_message")
        val lastMessage: String?,

        @SerializedName("decline_code")
        val declineCode: String?,

        @SerializedName("merchant")
        val merchant: MerchantEntity? = null,

        @SerializedName("store")
        val store: StoreEntity? = null,

        @SerializedName("local_amount")
        val localAmount: MoneyEntity? = null,

        @SerializedName("billing_amount")
        val billingAmount: MoneyEntity? = null,

        @SerializedName("hold_amount")
        val holdAmount: MoneyEntity? = null,

        @SerializedName("cashback_amount")
        val cashbackAmount: MoneyEntity? = null,

        @SerializedName("fee_amount")
        val feeAmount: MoneyEntity? = null,

        @SerializedName("native_balance")
        val nativeBalance: MoneyEntity? = null,

        @SerializedName("settlement")
        val settlement: TransactionSettlementEntity? = null,

        @SerializedName("ecommerce")
        val ecommerce: Boolean?,

        @SerializedName("international")
        val international: Boolean?,

        @SerializedName("card_present")
        val cardPresent: Boolean?,

        @SerializedName("emv")
        val emv: Boolean?,

        @SerializedName("network")
        val cardNetwork: String? = null,

        @SerializedName("state")
        val state: String? = null,

        @SerializedName("adjustments")
        val adjustments: ListEntity<TransactionAdjustmentEntity>?,

        @SerializedName("funding_source_name")
        val fundingSourceName: String? = null

) : Serializable {

    fun toTransaction() = Transaction (
            transactionId = transactionId,
            transactionType = parseTransactionType(transactionType),
            createdAt = Date((createdAt * 1000.0).toLong()),
            transactionDescription = transactionDescription,
            lastMessage = lastMessage,
            declineCode = DeclineCode.from(declineCode),
            merchant = merchant?.toMerchant(),
            store = store?.toStore(),
            localAmount = localAmount?.toMoney(),
            billingAmount = billingAmount?.toMoney(),
            holdAmount = holdAmount?.toMoney(),
            cashbackAmount = cashbackAmount?.toMoney(),
            feeAmount = feeAmount?.toMoney(),
            nativeBalance = nativeBalance?.toMoney(),
            settlement = settlement?.toTransactionSettlement(),
            ecommerce = ecommerce,
            international = international,
            cardPresent = cardPresent,
            emv = emv,
            cardNetwork = parseCardNetwork(cardNetwork),
            state = parseTransactionState(state),
            adjustments = if (adjustments?.data?.isEmpty() == false) adjustments.data?.map { it.toTransactionAdjustment() } else null,
            fundingSourceName = fundingSourceName
    )

    private fun parseTransactionType(transactionType: String?): Transaction.TransactionType {
        return transactionType?.let {
            try {
                Transaction.TransactionType.valueOf(it.toUpperCase())
            } catch (exception: Throwable) {
                Transaction.TransactionType.OTHER
            }
        } ?: Transaction.TransactionType.OTHER
    }

    private fun parseCardNetwork(cardNetwork: String?): Card.CardNetwork? {
        return cardNetwork?.let {
            try {
                Card.CardNetwork.valueOf(it.toUpperCase())
            } catch (exception: Throwable) {
                null
            }
        }
    }

    private fun parseTransactionState(state: String?): Transaction.TransactionState {
        return state?.let {
            try {
                Transaction.TransactionState.valueOf(it.toUpperCase())
            } catch (exception: Throwable) {
                Transaction.TransactionState.OTHER
            }
        } ?: Transaction.TransactionState.OTHER
    }
}
