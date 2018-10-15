package com.shiftpayments.link.sdk.api.vos.datapoints;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Address extends DataPointVo implements Parcelable {
    public String streetOne;
    public String streetTwo;
    public String country;
    public String locality;
    public String region;
    public String postalCode;

    public Address() {
        this(null, null, null, null, null, null, false, false);
    }

    public Address(String streetOne, String streetTwo, String country, String locality, String region,
                   String postalCode, boolean verified, boolean notSpecified) {
        super(DataPointType.Address, verified, notSpecified);
        this.streetOne = streetOne;
        this.streetTwo = streetTwo;
        this.country = country;
        this.locality = locality;
        this.region = region;
        this.postalCode = postalCode;
    }

    protected Address(Parcel in) {
        streetOne = in.readString();
        streetTwo = in.readString();
        country = in.readString();
        locality = in.readString();
        region = in.readString();
        postalCode = in.readString();
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
        this.streetOne = addressToCopy.streetOne;
        this.streetTwo = addressToCopy.streetTwo;
        this.country = addressToCopy.country;
        this.locality = addressToCopy.locality;
        this.region = addressToCopy.region;
        this.postalCode = addressToCopy.postalCode;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "address");
        gsonObject.addProperty("street_one", streetOne);
        gsonObject.addProperty("street_two", streetTwo);
        gsonObject.addProperty("locality", locality);
        gsonObject.addProperty("region", region);
        gsonObject.addProperty("postal_code", postalCode);
        return gsonObject;
    }

    @Override
    public String toString() {
        ArrayList<String> addressArray = new ArrayList<>();
        if (this.streetOne != null && !this.streetOne.isEmpty()) {
            addressArray.add(this.streetOne);
        }
        if (this.locality != null && !this.locality.isEmpty()) {
            addressArray.add(this.locality);
        }
        if (this.region != null && !this.region.isEmpty()) {
            addressArray.add(this.region);
        }
        if (this.postalCode != null && !this.postalCode.isEmpty()) {
            addressArray.add(this.postalCode);
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

        if (streetOne != null ? !streetOne.equals(address1.streetOne) : address1.streetOne != null)
            return false;
        if (streetTwo != null ? !streetTwo.equals(address1.streetTwo) : address1.streetTwo != null)
            return false;
        if (country != null ? !country.equals(address1.country) : address1.country != null)
            return false;
        if (locality != null ? !locality.equals(address1.locality) : address1.locality != null) return false;
        if (region != null ? !region.equals(address1.region) : address1.region != null)
            return false;
        return postalCode != null ? postalCode.equals(address1.postalCode) : address1.postalCode == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode()+(streetOne != null ? streetOne.hashCode() : 0);
        result = 31 * result + (streetTwo != null ? streetTwo.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(streetOne);
        parcel.writeString(streetTwo);
        parcel.writeString(country);
        parcel.writeString(locality);
        parcel.writeString(region);
        parcel.writeString(postalCode);
    }
}
