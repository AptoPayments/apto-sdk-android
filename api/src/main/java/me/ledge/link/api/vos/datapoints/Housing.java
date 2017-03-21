package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;

public class Housing extends DataPointVo {
    public IdDescriptionPairDisplayVo housingType;

    public Housing() {
        this(-1, false);
    }

    public Housing(int housingType, boolean verified) {
        super(DataPointType.Housing, verified);
        this.housingType = new IdDescriptionPairDisplayVo(housingType, null);
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "housing");
        gsonObject.addProperty("housing_type_id", housingType.getKey());
        return gsonObject;
    }
}