package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class Birthdate extends DataPointVo {
    private String date;

    public Birthdate() {
        this(null, false);
    }

    public Birthdate(String date, boolean verified) {
        super(DataPointType.BirthDate, verified);
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "birthdate");
        gsonObject.addProperty("date", date);
        return gsonObject;
    }

    @Override
    public String toString() {
        return date;
    }
}