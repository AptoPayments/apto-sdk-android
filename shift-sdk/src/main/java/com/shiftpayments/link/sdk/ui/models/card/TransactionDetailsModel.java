package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.AdjustmentVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        if(mTransaction.nativeBalance != null) {
            return new AmountVo(mTransaction.nativeBalance.amount, mTransaction.nativeBalance.currency).toString();
        }
        return UNAVAILABLE;
    }

    public String getCurrency() {
        return mTransaction.localAmount.currency;
    }

    public String getLocation() {
        if(mTransaction.store.address.country == null || mTransaction.store.address.country.isEmpty()) {
            return mTransaction.store.address.city;
        }
        else if(mTransaction.store.address.country.equalsIgnoreCase("USA")) {
            return mTransaction.store.address.city + ", " + mTransaction.store.address.state;
        }
        return mTransaction.store.address.city + ", " + mTransaction.store.address.country;
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

    public String getSettlementDate() {
        if(isStringFilled(mTransaction.settlementDate)) {
            return getFormattedDate(mTransaction.settlementDate);
        }
        return UNAVAILABLE;
    }

    public String getTransactionId() {
        return mTransaction.id;
    }

    public TransactionVo.TransactionType getTransactionType() {
        return mTransaction.type;
    }

    public AdjustmentVo[] getTransferList() {
        return mTransaction.adjustmentsList.data;
    }

    public String getDeclinedReason() {
        if(isStringFilled(mTransaction.declineReason)) {
            return mTransaction.declineReason;
        }
        return UNAVAILABLE;
    }

    public boolean hasFeeAmount() {
        return mTransaction.fee != null;
    }

    public String getFeeAmount() {
        return new AmountVo(mTransaction.fee.amount, mTransaction.fee.currency).toString();
    }

    public boolean hasCashbackAmount() {
        return mTransaction.cashBackAmount != null;
    }

    public String getCashbackAmount() {
        return new AmountVo(mTransaction.cashBackAmount.amount, mTransaction.cashBackAmount.currency).toString();
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