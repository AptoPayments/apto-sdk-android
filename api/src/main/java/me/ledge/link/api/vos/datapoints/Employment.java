package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;

public class Employment extends DataPointVo {
    public IdDescriptionPairDisplayVo employmentStatus;
    public IdDescriptionPairDisplayVo salaryFrequency;

    public Employment() {
        this(-1, -1, false);
    }

    public Employment(int employmentStatus, int salaryFrequency, boolean verified) {
        super(DataPointType.Employment, verified);
        this.employmentStatus = new IdDescriptionPairDisplayVo(employmentStatus, null);;
        this.salaryFrequency = new IdDescriptionPairDisplayVo(salaryFrequency, null);;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "employment");
        gsonObject.addProperty("employment_status_id", employmentStatus.getKey());
        gsonObject.addProperty("salary_frequency_id", salaryFrequency.getKey());
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employment that = (Employment) o;

        if (employmentStatus != null ? !employmentStatus.equals(that.employmentStatus) : that.employmentStatus != null)
            return false;
        return salaryFrequency != null ? salaryFrequency.equals(that.salaryFrequency) : that.salaryFrequency == null;

    }

    @Override
    public int hashCode() {
        int result = employmentStatus != null ? employmentStatus.hashCode() : 0;
        result = 31 * result + (salaryFrequency != null ? salaryFrequency.hashCode() : 0);
        return result;
    }
}