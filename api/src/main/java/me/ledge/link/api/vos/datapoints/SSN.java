package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;

public class SSN extends DataPointVo {
    private String ssn;
    private static final int EXPECTED_SSN_LENGTH = 9;

    public SSN() {
        this(null, false);
    }

    public SSN(String ssn, boolean verified) {
        super(DataPointType.SSN, verified);
        setSocialSecurityNumber(ssn);
    }

    public String getSocialSecurityNumber() {
        return ssn;
    }

    /**
     * Tries to store the SSN based on a raw String.
     * @param ssn Raw social security number.
     */
    public void setSocialSecurityNumber(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "ssn");
        gsonObject.addProperty("ssn", ssn);
        return gsonObject;
    }

    @Override
    public String toString() {
        StringBuilder outputBuffer = new StringBuilder(EXPECTED_SSN_LENGTH);
        for (int i = 0; i < EXPECTED_SSN_LENGTH; i++){
            outputBuffer.append("*");
        }
        return outputBuffer.toString();
    }
}