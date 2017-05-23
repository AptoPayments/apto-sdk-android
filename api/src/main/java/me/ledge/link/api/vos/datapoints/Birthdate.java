package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class Birthdate extends DataPointVo {
    private String date;

    public Birthdate() {
        this(null, false, false);
    }

    public Birthdate(String date, boolean verified, boolean notSpecified) {
        super(DataPointType.BirthDate, verified, notSpecified);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        Birthdate birthdate = (Birthdate) o;

        return date.equals(birthdate.date);

    }

    @Override
    public int hashCode() {
        return super.hashCode()+date.hashCode();
    }
}