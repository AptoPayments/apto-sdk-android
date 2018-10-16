package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneNumberVo extends DataPointVo {
    public Phonenumber.PhoneNumber phoneNumber;

    public PhoneNumberVo() {
        this(null, false, false);
    }

    public PhoneNumberVo(String phone, boolean verified, boolean notSpecified) {
        super(DataPointType.Phone, verified, notSpecified);
        init();
        try {
            Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse(phone, "US");
            setPhone(number);
        } catch (NumberParseException e) {
            this.phoneNumber = null;
        }
    }

    public void init() {
        phoneNumber = new Phonenumber.PhoneNumber();
    }

    /**
     * @return Phone number.
     */
    public Phonenumber.PhoneNumber getPhone() {
        return phoneNumber;
    }

    /**
     * Parses and stores a valid US phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone) {
        try {
            Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse(phone, "US");
            setPhone(number);
        } catch (NumberParseException npe) {
            this.phoneNumber = null;
        }
    }

    /**
     * Parses and stores a valid phone number.
     * @param phone Raw phone number.
     */
    public void setPhone(String phone, String countryCode) {
        try {
            Phonenumber.PhoneNumber number = PhoneNumberUtil.getInstance().parse(phone, countryCode);
            setPhone(number);
        } catch (NumberParseException npe) {
            this.phoneNumber = null;
        }
    }

    /**
     * Stores a valid phone number.
     * @param number Phone number object.
     */
    public void setPhone(Phonenumber.PhoneNumber number) {
        if (number != null && PhoneNumberUtil.getInstance().isValidNumber(number)) {
            this.phoneNumber = number;
        } else {
            this.phoneNumber = null;
        }
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "phone");
        gsonObject.addProperty("phone_number", String.valueOf(phoneNumber.getNationalNumber()));
        gsonObject.addProperty("country_code", String.valueOf(phoneNumber.getCountryCode()));
        return gsonObject;
    }

    @Override
    public String toString() {
        return "+" + String.valueOf(phoneNumber.getCountryCode()) + String.valueOf(phoneNumber.getNationalNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        PhoneNumberVo that = (PhoneNumberVo) o;

        return phoneNumber != null ? phoneNumber.equals(that.phoneNumber) : that.phoneNumber == null;

    }

    @Override
    public int hashCode() {
        return super.hashCode() + (phoneNumber != null ? phoneNumber.hashCode() : 0);
    }
}