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
        this(null, null, null, null, null, null, false, false);
    }

    public Address(String address, String apUnit, String country, String city, String stateCode,
                   String zip, boolean verified, boolean notSpecified) {
        super(DataPointType.Address, verified, notSpecified);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;

        if (address != null ? !address.equals(address1.address) : address1.address != null)
            return false;
        if (apUnit != null ? !apUnit.equals(address1.apUnit) : address1.apUnit != null)
            return false;
        if (country != null ? !country.equals(address1.country) : address1.country != null)
            return false;
        if (city != null ? !city.equals(address1.city) : address1.city != null) return false;
        if (stateCode != null ? !stateCode.equals(address1.stateCode) : address1.stateCode != null)
            return false;
        return zip != null ? zip.equals(address1.zip) : address1.zip == null;

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (apUnit != null ? apUnit.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }
}
