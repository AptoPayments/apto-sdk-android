package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class TimeAtAddress extends DataPointVo {
    public int timeAtAddressRange;

    public TimeAtAddress() {
        this(-1, false, false);
    }

    public TimeAtAddress(int timeAtAddressRange, boolean verified, boolean notSpecified) {
        super(DataPointType.TimeAtAddress, verified, notSpecified);
        this.timeAtAddressRange = timeAtAddressRange;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "time_at_address");
        gsonObject.addProperty("time_at_address_id", timeAtAddressRange);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        TimeAtAddress that = (TimeAtAddress) o;

        return timeAtAddressRange == that.timeAtAddressRange;

    }

    @Override
    public int hashCode() {
        return super.hashCode() + timeAtAddressRange;
    }
}