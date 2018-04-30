package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;
import com.shiftpayments.link.sdk.ui.models.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Concrete {@link Model} for the transaction details.
 * @author Adrian
 */
public class TransactionDetailsModel implements Model {

    private TransactionVo mTransaction;

    public void setTransaction(TransactionVo transaction) {
        mTransaction = transaction;
    }

    public TransactionVo getTransaction() {
        return mTransaction;
    }

    public String getDescription() {
        return mTransaction.description;
    }

    public String getLocalAmount() {
        return String.valueOf(mTransaction.localAmount);
    }

    public String getUsdAmount() {
        return "$" + String.valueOf(mTransaction.usdAmount);
    }

    public String getCurrency() {
        return mTransaction.localCurrency;
    }

    public String getLocation() {
        return mTransaction.merchantCity + ", " + mTransaction.merchantState;
    }

    public String getCategory() {
        return mTransaction.merchantCategoryName;
    }

    public String getTransactionDate() {
        return getFormattedDate(mTransaction.creationTime);
    }

    public String getSettlementDate() {
        // TODO: return correct date
        return getFormattedDate(mTransaction.creationTime);
    }

    public String getTransactionId() {
        return mTransaction.id;
    }

    private String getFormattedDate(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat expectedFormat = new SimpleDateFormat("EEE, MMM dd 'at' hh:mm a", Locale.US);
        return expectedFormat.format(date);
    }
}