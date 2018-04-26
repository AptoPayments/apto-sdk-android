package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;

public class IncomeSource extends DataPointVo {
    public IdDescriptionPairDisplayVo incomeType;
    public IdDescriptionPairDisplayVo salaryFrequency;

    public IncomeSource() {
        this(-1, -1, false, false);
    }

    public IncomeSource(int incomeType, int salaryFrequency, boolean verified,
                        boolean notSpecified) {
        super(DataPointType.IncomeSource, verified, notSpecified);
        this.incomeType = new IdDescriptionPairDisplayVo(incomeType, null);;
        this.salaryFrequency = new IdDescriptionPairDisplayVo(salaryFrequency, null);;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "income_source");
        gsonObject.addProperty("income_type_id", incomeType.getKey());
        gsonObject.addProperty("salary_frequency_id", salaryFrequency.getKey());
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        IncomeSource that = (IncomeSource) o;

        if (incomeType != null ? !incomeType.getKey().equals(that.incomeType.getKey()) : that.incomeType != null)
            return false;
        return salaryFrequency != null ? salaryFrequency.getKey().equals(that.salaryFrequency.getKey()) : that.salaryFrequency == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode() + (incomeType != null ? incomeType.hashCode() : 0);
        result = 31 * result + (salaryFrequency != null ? salaryFrequency.hashCode() : 0);
        return result;
    }
}