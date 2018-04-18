package com.shift.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;

public class CreditScore extends DataPointVo {
    public int creditScoreRange;

    public CreditScore() {
        this(-1, false, false);
    }

    public CreditScore(int creditScoreRange, boolean verified, boolean notSpecified) {
        super(DataPointType.CreditScore, verified, notSpecified);
        this.creditScoreRange = creditScoreRange;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "credit_score");
        gsonObject.addProperty("credit_range", creditScoreRange);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        CreditScore that = (CreditScore) o;

        return creditScoreRange == that.creditScoreRange;

    }

    @Override
    public int hashCode() {
        return super.hashCode()+creditScoreRange;
    }
}