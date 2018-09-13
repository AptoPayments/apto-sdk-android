package com.shiftpayments.link.sdk.ui.models.card;

public abstract class TransactionListItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_DATE = 1;
    public static final int TYPE_TRANSACTION = 2;

    abstract public int getType();
}