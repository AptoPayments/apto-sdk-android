package com.shiftpayments.link.sdk.api.vos.datapoints;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Address extends DataPointVo implements Parcelable {
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

    protected Address(Parcel in) {
        address = in.readString();
        apUnit = in.readString();
        country = in.readString();
        city = in.readString();
        stateCode = in.readString();
        zip = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public void update(Address addressToCopy) {
        this.address = addressToCopy.address;
        this.apUnit = addressToCopy.apUnit;
        this.country = addressToCopy.country;
        this.city = addressToCopy.city;
        this.stateCode = addressToCopy.stateCode;
        this.zip = addressToCopy.zip;
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
        ArrayList<String> addressArray = new ArrayList<>();
        if (this.address != null && !this.address.isEmpty()) {
            addressArray.add(this.address);
        }
        if (this.city != null && !this.city.isEmpty()) {
            addressArray.add(this.city);
        }
        if (this.stateCode != null && !this.stateCode.isEmpty()) {
            addressArray.add(this.stateCode);
        }
        if (this.zip != null && !this.zip.isEmpty()) {
            addressArray.add(this.zip);
        }
        if (this.country != null && !this.country.isEmpty()) {
            addressArray.add(this.country);
        }
        return android.text.TextUtils.join(", ", addressArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
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
        int result = super.hashCode()+(address != null ? address.hashCode() : 0);
        result = 31 * result + (apUnit != null ? apUnit.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateCode != null ? stateCode.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(apUnit);
        parcel.writeString(country);
        parcel.writeString(city);
        parcel.writeString(stateCode);
        parcel.writeString(zip);
    }
}
