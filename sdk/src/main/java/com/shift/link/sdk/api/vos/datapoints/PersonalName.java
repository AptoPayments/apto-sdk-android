package com.shift.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;

public class PersonalName extends DataPointVo {

    public String firstName;
    public String lastName;

    public PersonalName() {
        this(null, null, false, false);
    }

    public PersonalName(String firstName, String lastName, boolean verified, boolean notSpecified) {
        super(DataPointType.PersonalName, verified, notSpecified);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        PersonalName that = (PersonalName) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        return lastName != null ? lastName.equals(that.lastName) : that.lastName == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result += firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}