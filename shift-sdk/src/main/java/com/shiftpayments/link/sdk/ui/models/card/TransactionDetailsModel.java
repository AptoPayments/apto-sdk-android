package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.AdjustmentVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Concrete {@link Model} for the transaction details.
 * @author Adrian
 */
public class TransactionDetailsModel implements Model {

    private TransactionVo mTransaction;
    private static final String UNAVAILABLE = "Unavailable";

    public void setTransaction(TransactionVo transaction) {
        mTransaction = transaction;
    }

    public TransactionVo getTransaction() {
        return mTransaction;
    }

    public String getDescription() {
        return mTransaction.description;
    }

    public String getMerchantName() {
        return mTransaction.merchant.name;
    }

    public String getLocalAmount() {
        if(mTransaction.localAmount != null) {
            return new AmountVo(mTransaction.localAmount.amount, mTransaction.localAmount.currency).toString();
        }
        return UNAVAILABLE;
    }

    public String getNativeBalance() {
        if(mTransaction.nativeBalance != null && mTransaction.nativeBalance.hasAmount()) {
            return new AmountVo(mTransaction.nativeBalance.amount, mTransaction.nativeBalance.currency).toString();
        }
        return UNAVAILABLE;
    }

    public String getCurrency() {
        return mTransaction.localAmount.currency;
    }

    public String getLocation() {
        if(mTransaction.store.address != null) {
            return mTransaction.store.address.toString();
        }
        return UNAVAILABLE;
    }

    public String getCategory() {
        if(isStringFilled(mTransaction.merchant.mcc.merchantCategoryName)) {
            return mTransaction.merchant.mcc.merchantCategoryName;
        }
        return UNAVAILABLE;
    }

    public String getTransactionDate() {
        if(isStringFilled(mTransaction.creationTime)) {
            return getFormattedDate(mTransaction.creationTime);
        }
        return UNAVAILABLE;
    }

    public boolean hasSettlementDate() {
        return mTransaction.settlement != null && mTransaction.settlement.date != null;
    }

    public String getSettlementDate() {
        if(isStringFilled(mTransaction.settlement.date)) {
            return getFormattedDate(mTransaction.settlement.date);
        }
        return UNAVAILABLE;
    }

    public boolean hasTransactionId() {
        return isStringFilled(mTransaction.externalId);
    }

    public String getTransactionId() {
        return mTransaction.externalId;
    }

    public String getShiftId() {
        return mTransaction.id;
    }

    public TransactionVo.TransactionType getTransactionType() {
        return mTransaction.type;
    }

    public List<AdjustmentVo> getTransferList() {
        if(mTransaction.adjustmentsList != null) {
            return Arrays.asList(mTransaction.adjustmentsList.data);
        }
        return null;
    }

    public String getDeclinedReason() {
        if(isStringFilled(mTransaction.declineReason)) {
            return mTransaction.declineReason;
        }
        return UNAVAILABLE;
    }

    public boolean hasFeeAmount() {
        return mTransaction.fee != null && mTransaction.fee.hasAmount();
    }

    public String getFeeAmount() {
        return new AmountVo(mTransaction.fee.amount, mTransaction.fee.currency).toString();
    }

    public boolean hasCashbackAmount() {
        return mTransaction.cashBackAmount != null && mTransaction.cashBackAmount.hasAmount();
    }

    public String getCashbackAmount() {
        return new AmountVo(mTransaction.cashBackAmount.amount, mTransaction.cashBackAmount.currency).toString();
    }

    public boolean hasHoldAmount() {
        return mTransaction.holdAmount != null && mTransaction.holdAmount.hasAmount();
    }

    public String getHoldAmount() {
        return new AmountVo(mTransaction.holdAmount.amount, mTransaction.holdAmount.currency).toString();
    }

    private String getFormattedDate(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat expectedFormat = new SimpleDateFormat("EEE, MMM dd 'at' hh:mm a", Locale.US);
        return expectedFormat.format(date);
    }

    private boolean isStringFilled(String string) {
        return string != null && !string.isEmpty();
    }
}