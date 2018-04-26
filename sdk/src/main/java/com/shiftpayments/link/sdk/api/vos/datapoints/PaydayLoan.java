package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;

public class PaydayLoan extends DataPointVo {
    public Boolean hasUsedPaydayLoan;

    public PaydayLoan() {
        this(null, false, false);
    }

    public PaydayLoan(Boolean hasUsedPaydayLoan, boolean verified, boolean notSpecified) {
        super(DataPointType.PayDayLoan, verified, notSpecified);
        this.hasUsedPaydayLoan = hasUsedPaydayLoan;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "payday_loan");
        gsonObject.addProperty("payday_loan", hasUsedPaydayLoan);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        PaydayLoan that = (PaydayLoan) o;

        return hasUsedPaydayLoan == that.hasUsedPaydayLoan;

    }

    @Override
    public int hashCode() {
        return super.hashCode()+hasUsedPaydayLoan.hashCode();
    }
}