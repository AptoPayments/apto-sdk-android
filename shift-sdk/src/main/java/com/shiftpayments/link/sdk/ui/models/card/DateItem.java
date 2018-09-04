package com.shiftpayments.link.sdk.ui.models.card;

public class DateItem extends TransactionListItem {

    public String date;

    public DateItem(String date) {
        this.date = date;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}
