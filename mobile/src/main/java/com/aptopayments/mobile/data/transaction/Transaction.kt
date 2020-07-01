package com.aptopayments.mobile.data.transaction

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.extension.localized
import org.threeten.bp.ZonedDateTime
import java.io.Serializable

data class Transaction(
    val transactionId: String,
    val transactionType: TransactionType,
    val createdAt: ZonedDateTime,
    val transactionDescription: String?,
    val lastMessage: String?,
    val declineCode: DeclineCode?,
    val merchant: Merchant?,
    val store: Store?,
    val localAmount: Money?,
    val billingAmount: Money?,
    val holdAmount: Money?,
    val cashbackAmount: Money?,
    val feeAmount: Money?,
    val nativeBalance: Money?,
    val settlement: TransactionSettlement?,
    val ecommerce: Boolean?,
    val international: Boolean?,
    val cardPresent: Boolean?,
    val emv: Boolean?,
    val cardNetwork: Card.CardNetwork?,
    val state: TransactionState,
    val adjustments: List<TransactionAdjustment>?,
    val fundingSourceName: String?
) : Serializable {

    fun transactionClass(): TransactionClass {
        return when (this.transactionType) {
            TransactionType.WITHDRAWAL -> TransactionClass.ATM
            TransactionType.DECLINE -> TransactionClass.DECLINED
            TransactionType.PENDING -> TransactionClass.PREAUTHORISED
            TransactionType.REVERSAL -> TransactionClass.REVERSED
            else -> TransactionClass.AUTHORISED
        }
    }

    fun deviceType(): TransactionDeviceType {
        if (ecommerce == true) return TransactionDeviceType.ECOMMERCE
        if (cardPresent == true) return TransactionDeviceType.CARDPRESENT
        if (international == true) return TransactionDeviceType.INTERNATIONAL
        if (emv == true) return TransactionDeviceType.EMV
        return TransactionDeviceType.OTHER
    }

    enum class TransactionType {
        PENDING,
        REVERSAL,
        PURCHASE,
        PIN_PURCHASE,
        REFUND,
        DECLINE,
        BALANCE_INQUIRY,
        WITHDRAWAL,
        CREDIT,
        OTHER;

        fun isCredit(): Boolean {
            return when (this) {
                REVERSAL, REFUND, CREDIT -> true
                else -> false
            }
        }

        fun isDebit(): Boolean {
            return !isCredit()
        }

        fun toLocalizedString() =
            when (this) {
                PENDING -> "transaction_details.details.transaction_type.pending"
                REVERSAL -> "transaction_details.details.transaction_type.reversal"
                PURCHASE -> "transaction_details.details.transaction_type.purchase"
                PIN_PURCHASE -> "transaction_details.details.transaction_type.pin_purchase"
                REFUND -> "transaction_details.details.transaction_type.refund"
                DECLINE -> "transaction_details.details.transaction_type.decline"
                BALANCE_INQUIRY -> "transaction_details.details.transaction_type.balance_inquiry"
                WITHDRAWAL -> "transaction_details.details.transaction_type.atm_withdrawal"
                CREDIT -> "transaction_details.details.transaction_type.credit"
                OTHER -> "transaction_details.details.transaction_type.other"
            }.localized()
    }

    enum class TransactionState {
        PENDING,
        DECLINED,
        COMPLETE,
        OTHER;

        fun toLocalizedString() =
            when (this) {
                PENDING -> "transaction_details.basic_info.transaction_status.pending"
                DECLINED -> "transaction_details.basic_info.transaction_status.declined"
                COMPLETE -> "transaction_details.basic_info.transaction_status.complete"
                OTHER -> "transaction_details.basic_info.transaction_status.other"
            }.localized()
    }

    enum class TransactionClass {
        ATM,
        AUTHORISED,
        PREAUTHORISED,
        DECLINED,
        REVERSED;

        fun toLocalizedString() =
            when (this) {
                ATM -> "transaction_details.details.transaction_type.atm_withdrawal"
                AUTHORISED -> "transaction_details.details.transaction_type.authorised"
                PREAUTHORISED -> "transaction_details.details.transaction_type.pending"
                DECLINED -> "transaction_details.details.transaction_type.declined"
                REVERSED -> "transaction_details.details.transaction_type.reversed"
            }.localized()
    }

    enum class TransactionDeviceType {
        ECOMMERCE,
        CARDPRESENT,
        INTERNATIONAL,
        EMV,
        OTHER;

        fun toLocalizedString(): String {
            return when (this) {
                ECOMMERCE -> "transaction_details.details.device_type.ecommerce".localized()
                CARDPRESENT -> "transaction_details.details.device_type.pos".localized()
                INTERNATIONAL -> "transaction_details.details.device_type.international".localized()
                EMV -> "transaction_details.details.device_type.emv".localized()
                OTHER -> ""
            }
        }
    }

    fun getAmountPrefix(): String {
        if (transactionType.isCredit()) {
            return "+ "
        }
        return ""
    }

    fun getLocalAmountRepresentation(): String {
        localAmount?.let {
            return getAmountPrefix() + localAmount.toAbsString()
        } ?: return "-"
    }

    fun getNativeBalanceRepresentation(): String {
        nativeBalance?.let {
            return getAmountPrefix() + nativeBalance.toAbsString()
        } ?: return "-"
    }
}
