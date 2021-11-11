package com.aptopayments.mobile.repository.transaction.remote.entities

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.transaction.DeclineCode
import com.aptopayments.mobile.data.transaction.Transaction
import com.aptopayments.mobile.extension.toZonedDateTime
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Locale

internal data class TransactionEntity(

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

    fun toTransaction() = Transaction(
        transactionId = transactionId,
        transactionType = parseTransactionType(transactionType),
        createdAt = (createdAt * 1000).toLong().toZonedDateTime(),
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
        adjustments = calculateAdjustments(adjustments),
        fundingSourceName = fundingSourceName
    )

    private fun calculateAdjustments(adjustments: ListEntity<TransactionAdjustmentEntity>?) =
        if (adjustments?.data?.isEmpty() == false) adjustments.data.map { it.toTransactionAdjustment() } else null

    private fun parseTransactionType(transactionType: String?): Transaction.TransactionType {
        return transactionType?.let {
            try {
                Transaction.TransactionType.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                Transaction.TransactionType.OTHER
            }
        } ?: Transaction.TransactionType.OTHER
    }

    private fun parseCardNetwork(cardNetwork: String?): Card.CardNetwork? {
        return cardNetwork?.let {
            try {
                Card.CardNetwork.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                null
            }
        }
    }

    private fun parseTransactionState(state: String?): Transaction.TransactionState {
        return state?.let {
            try {
                Transaction.TransactionState.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                Transaction.TransactionState.OTHER
            }
        } ?: Transaction.TransactionState.OTHER
    }
}
