package com.aptopayments.core.data.transaction

import android.content.Context
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.extension.localized
import java.io.Serializable
import java.util.*

data class Transaction (
        val transactionId: String,
        val transactionType: TransactionType,
        val createdAt: Date,
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

        fun toString(context: Context): String {
            context.let {
                return when (this) {
                    PENDING -> "transaction_details.details.transaction_type.pending".localized(it)
                    REVERSAL -> "transaction_details.details.transaction_type.reversal".localized(it)
                    PURCHASE -> "transaction_details.details.transaction_type.purchase".localized(it)
                    PIN_PURCHASE -> "transaction_details.details.transaction_type.pin_purchase".localized(it)
                    REFUND -> "transaction_details.details.transaction_type.refund".localized(it)
                    DECLINE -> "transaction_details.details.transaction_type.decline".localized(it)
                    BALANCE_INQUIRY -> "transaction_details.details.transaction_type.balance_inquiry".localized(it)
                    WITHDRAWAL -> "transaction_details.details.transaction_type.atm_withdrawal".localized(it)
                    CREDIT -> "transaction_details.details.transaction_type.credit".localized(it)
                    OTHER -> "transaction_details.details.transaction_type.other".localized(it)
                }
            }
        }
    }

    enum class TransactionState {
        PENDING,
        DECLINED,
        COMPLETE,
        OTHER;

        fun toString(context: Context): String {
            context.let {
                return when (this) {
                    PENDING -> "transaction_details.basic_info.transaction_status.pending".localized(it)
                    DECLINED -> "transaction_details.basic_info.transaction_status.declined".localized(it)
                    COMPLETE -> "transaction_details.basic_info.transaction_status.complete".localized(it)
                    OTHER -> "transaction_details.basic_info.transaction_status.other".localized(it)
                }
            }
        }
    }

    enum class TransactionClass {
        ATM,
        AUTHORISED,
        PREAUTHORISED,
        DECLINED,
        REVERSED;

        fun toString(context: Context): String {
            context.let {
                return when (this) {
                    ATM -> "transaction_details.details.transaction_type.atm_withdrawal".localized(it)
                    AUTHORISED -> "transaction_details.details.transaction_type.authorised".localized(it)
                    PREAUTHORISED -> "transaction_details.details.transaction_type.pending".localized(it)
                    DECLINED -> "transaction_details.details.transaction_type.declined".localized(it)
                    REVERSED -> "transaction_details.details.transaction_type.reversed".localized(it)
                }
            }
        }
    }

    enum class TransactionDeviceType {
        ECOMMERCE,
        CARDPRESENT,
        INTERNATIONAL,
        EMV,
        OTHER;

        fun toString(context: Context): String? {
            context.let {
                return when (this) {
                    ECOMMERCE -> "transaction_details.details.device_type.ecommerce".localized(it)
                    CARDPRESENT -> "transaction_details.details.device_type.pos".localized(it)
                    INTERNATIONAL -> "transaction_details.details.device_type.international".localized(it)
                    EMV -> "transaction_details.details.device_type.emv".localized(it)
                    OTHER -> null
                }
            }
        }
    }

    fun getAmountPrefix(): String {
        if(transactionType.isCredit()) {
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
