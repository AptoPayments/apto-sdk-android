package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class Email extends DataPointVo {
    public String email;

    public Email() {
        this(null, false);
    }

    public Email(String email, boolean verified) {
        super(DataPointType.Email, verified);
        this.email = email;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "email");
        gsonObject.addProperty("email", email);
        return gsonObject;
    }

    @Override
    public String toString() {
        return email;
    }
}