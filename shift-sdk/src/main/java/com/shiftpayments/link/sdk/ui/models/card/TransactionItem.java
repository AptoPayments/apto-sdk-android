package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.TransactionVo;

public class TransactionItem extends TransactionListItem {
    public TransactionVo transaction;

    public TransactionItem(TransactionVo transaction) {
        this.transaction = transaction;
    }

    @Override
    public int getType() {
        return TYPE_TRANSACTION;
    }
}
