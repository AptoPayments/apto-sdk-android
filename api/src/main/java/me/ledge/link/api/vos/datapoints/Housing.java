package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;

public class Housing extends DataPointVo {
    public IdDescriptionPairDisplayVo housingType;

    public Housing() {
        this(-1, false, false);
    }

    public Housing(int housingType, boolean verified, boolean notSpecified) {
        super(DataPointType.Housing, verified, notSpecified);
        this.housingType = new IdDescriptionPairDisplayVo(housingType, null);
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "housing");
        gsonObject.addProperty("housing_type_id", housingType.getKey());
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Housing housing = (Housing) o;

        return housingType != null ? housingType.getKey().equals(housing.housingType.getKey()) : housing.housingType == null;

    }

    @Override
    public int hashCode() {
        return housingType != null ? housingType.hashCode() : 0;
    }
}