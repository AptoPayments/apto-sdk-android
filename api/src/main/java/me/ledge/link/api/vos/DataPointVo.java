package me.ledge.link.api.vos;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class DataPointVo {
    public enum DataPointType{
        PersonalName,
        PhoneNumber,
        Email,
        BirthDate,
        SSN,
        Address,
        Housing,
        Employment,
        Income,
        CreditScore,
        FinancialAccount;
    }

    @SerializedName("type")
    private final DataPointType mDataPointType;
    @SerializedName("verified")
    private boolean mVerified;
    private VerificationVo mVerification;

    /**
     * Default constructor
     */
    public DataPointVo() {
        this(null, false);
    }

    /**
     * Creates a new {@link DataPointVo} instance.
     */
    public DataPointVo(DataPointType type, boolean verified) {
        mDataPointType = type;
        mVerified = verified;
    }

    public void invalidateVerification() {
        mVerification = null;
        mVerified = false;
    }

    /**
     * @return DataPoint Type.
     */
    public DataPointType getType() {
        return mDataPointType;
    }

    public boolean isVerified() {
        return mVerified || (hasVerification() && getVerification().isVerified());
    }

    public boolean hasVerification() {
        return mVerification!=null;
    }

    public VerificationVo getVerification() {
        return mVerification;
    }

    public void setVerification(VerificationVo verification) {
        mVerification = verification;
    }

    public JsonObject toJSON() {
        JsonObject gsonObject = new JsonObject();

        if(this.hasVerification()) {
            gsonObject.add("verification", mVerification.toJSON());
        }
        return gsonObject;
    }

    public static class PersonalName extends DataPointVo {
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

    public static class PhoneNumber extends DataPointVo {
        public Phonenumber.PhoneNumber phoneNumber;

        public PhoneNumber() {
            this(null, false);
        }

        public PhoneNumber(String phone, boolean verified) {
            super(DataPointType.PhoneNumber, verified);
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

        public String getPhoneAsString() {
            return "+" + String.valueOf(phoneNumber.getCountryCode()) + String.valueOf(phoneNumber.getNationalNumber());
        }

        /**
         * Parses and stores a valid phone number.
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
            // TODO: refactor
            return getPhoneAsString();
        }
    }

    public static class Email extends DataPointVo {
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

    public static class Birthdate extends DataPointVo {
        private String date;

        public Birthdate() {
            this(null, false);
        }

        public Birthdate(String date, boolean verified) {
            super(DataPointType.BirthDate, verified);
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public JsonObject toJSON() {
            JsonObject gsonObject = super.toJSON();
            gsonObject.addProperty("data_type", "birthdate");
            gsonObject.addProperty("date", date);
            return gsonObject;
        }

        @Override
        public String toString() {
            return date;
        }
    }

    public static class SSN extends DataPointVo {
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

    public static class Address extends DataPointVo {
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

    public static class Housing extends DataPointVo {
        public IdDescriptionPairDisplayVo housingType;

        public Housing() {
            this(-1, false);
        }

        public Housing(int housingType, boolean verified) {
            super(DataPointType.Housing, verified);
            this.housingType = new IdDescriptionPairDisplayVo(housingType, null);
        }

        @Override
        public JsonObject toJSON() {
            JsonObject gsonObject = super.toJSON();
            gsonObject.addProperty("data_type", "housing");
            gsonObject.addProperty("housing_type_id", housingType.getKey());
            return gsonObject;
        }
    }

    public static class Employment extends DataPointVo {
        public IdDescriptionPairDisplayVo employmentStatus;
        public IdDescriptionPairDisplayVo salaryFrequency;

        public Employment() {
            this(-1, -1, false);
        }

        public Employment(int employmentStatus, int salaryFrequency, boolean verified) {
            super(DataPointType.Employment, verified);
            this.employmentStatus = new IdDescriptionPairDisplayVo(employmentStatus, null);;
            this.salaryFrequency = new IdDescriptionPairDisplayVo(salaryFrequency, null);;
        }

        @Override
        public JsonObject toJSON() {
            JsonObject gsonObject = super.toJSON();
            gsonObject.addProperty("data_type", "employment");
            gsonObject.addProperty("employment_status_id", employmentStatus.getKey());
            gsonObject.addProperty("salary_frequency_id", salaryFrequency.getKey());
            return gsonObject;
        }
    }

    public static class Income extends DataPointVo {
        public double monthlyNetIncome;
        public long annualGrossIncome;

        public Income() {
            this(-1, -1, false);
        }

        public Income(double monthlyNetIncome, long annualGrossIncome, boolean verified) {
            super(DataPointType.Income, verified);
            this.monthlyNetIncome = monthlyNetIncome;
            this.annualGrossIncome = annualGrossIncome;
        }

        @Override
        public JsonObject toJSON() {
            JsonObject gsonObject = super.toJSON();
            gsonObject.addProperty("data_type", "financial_income");
            gsonObject.addProperty("gross_annual_income", annualGrossIncome);
            gsonObject.addProperty("net_monthly_income", monthlyNetIncome);
            return gsonObject;
        }
    }

    public static class CreditScore extends DataPointVo {
        public int creditScoreRange;

        public CreditScore() {
            this(-1, false);
        }

        public CreditScore(int creditScoreRange, boolean verified) {
            super(DataPointType.CreditScore, verified);
            this.creditScoreRange = creditScoreRange;
        }

        @Override
        public JsonObject toJSON() {
            JsonObject gsonObject = super.toJSON();
            gsonObject.addProperty("data_type", "financial_credit_score");
            gsonObject.addProperty("credit_range", creditScoreRange);
            return gsonObject;
        }
    }
}