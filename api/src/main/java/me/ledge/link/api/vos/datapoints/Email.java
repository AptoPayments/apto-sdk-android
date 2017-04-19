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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;

        return email != null ? email.equals(email1.email) : email1.email == null;

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}