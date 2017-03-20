package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class Address extends DataPointVo {
    public String address;
    public String apUnit;
    public String country;
    public String city;
    public String stateCode;
    public String zip;

    public Address() {
        this(null, null, null, null, null, null, false);
    }

    public Address(String address, String apUnit, String country, String city, String stateCode,
                   String zip, boolean verified) {
        super(DataPointType.Address, verified);
        this.address = address;
        this.apUnit = apUnit;
        this.country = country;
        this.city = city;
        this.stateCode = stateCode;
        this.zip = zip;
    }

    public void update(Address addressToCopy) {
        this.address = addressToCopy.address;
        this.apUnit = addressToCopy.apUnit;
        this.country = addressToCopy.country;
        this.city = addressToCopy.city;
        this.stateCode = addressToCopy.stateCode;
        this.zip = addressToCopy.zip;
    }

    public String addressDescription() {
        if( "US".equalsIgnoreCase(this.country)) {
            String retVal = "";
            if (this.address != null) {
                retVal += this.address;
            }
            if (this.city != null) {
                retVal += ", " + this.city;
            }
            if (this.stateCode != null) {
                retVal += ", " + this.stateCode;
            }
            if (this.zip != null) {
                retVal += ", " + this.zip;
            }
            return retVal;
        }
        return null;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "address");
        gsonObject.addProperty("address", address);
        gsonObject.addProperty("apt", apUnit);
        gsonObject.addProperty("city", city);
        gsonObject.addProperty("state", stateCode);
        gsonObject.addProperty("zip", zip);
        return gsonObject;
    }

    @Override
    public String toString() {
        return addressDescription();
    }
}
