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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Income income = (Income) o;

        if (Double.compare(income.monthlyNetIncome, monthlyNetIncome) != 0) return false;
        return annualGrossIncome == income.annualGrossIncome;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(monthlyNetIncome);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (annualGrossIncome ^ (annualGrossIncome >>> 32));
        return result;
    }
}