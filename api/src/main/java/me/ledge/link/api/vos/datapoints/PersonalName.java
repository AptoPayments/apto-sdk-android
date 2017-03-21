package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class PersonalName extends DataPointVo {

    public String firstName;
    public String lastName;

    public PersonalName() {
        this(null, null, false);
    }

    public PersonalName(String firstName, String lastName, boolean verified) {
        super(DataPointType.PersonalName, verified);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "name");
        gsonObject.addProperty("first_name", firstName);
        gsonObject.addProperty("last_name", lastName);
        return gsonObject;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}