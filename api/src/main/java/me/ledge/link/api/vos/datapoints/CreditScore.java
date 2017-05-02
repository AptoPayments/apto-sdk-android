package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class CreditScore extends DataPointVo {
    public int creditScoreRange;

    public CreditScore() {
        this(-1, false);
    }

    public CreditScore(int creditScoreRange, boolean verified) {
        super(DataPointType.CreditScore, verified);
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

        CreditScore that = (CreditScore) o;

        return creditScoreRange == that.creditScoreRange;

    }

    @Override
    public int hashCode() {
        return creditScoreRange;
    }
}