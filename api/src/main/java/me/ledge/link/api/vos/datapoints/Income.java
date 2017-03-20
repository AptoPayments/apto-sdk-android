package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class Income extends DataPointVo {
    public double monthlyNetIncome;
    public long annualGrossIncome;

    public Income() {
        this(-1, -1, false);
    }

    public Income(double monthlyNetIncome, long annualGrossIncome, boolean verified) {
        super(DataPointType.Income, verified);
        this.monthlyNetIncome = monthlyNetIncome;
        this.annualGrossIncome = annualGrossIncome;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "financial_income");
        gsonObject.addProperty("gross_annual_income", annualGrossIncome);
        gsonObject.addProperty("net_monthly_income", monthlyNetIncome);
        return gsonObject;
    }
}