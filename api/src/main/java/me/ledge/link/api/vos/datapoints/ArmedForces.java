package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class ArmedForces extends DataPointVo {
    public Boolean isMemberOfArmedForces;

    public ArmedForces() {
        this(null, false, false);
    }

    public ArmedForces(Boolean isMemberOfArmedForces, boolean verified, boolean notSpecified) {
        super(DataPointType.MemberOfArmedForces, verified, notSpecified);
        this.isMemberOfArmedForces = isMemberOfArmedForces;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "member_of_armed_forces");
        gsonObject.addProperty("member_of_armed_forces", isMemberOfArmedForces);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        ArmedForces that = (ArmedForces) o;

        return isMemberOfArmedForces == that.isMemberOfArmedForces;

    }

    @Override
    public int hashCode() {
        return super.hashCode()+isMemberOfArmedForces.hashCode();
    }
}