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
        gsonObject.addProperty("data_type", "financial_credit_score");
        gsonObject.addProperty("credit_range", creditScoreRange);
        return gsonObject;
    }
}