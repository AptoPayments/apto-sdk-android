package me.ledge.link.sdk.ui.vos;

import com.google.i18n.phonenumbers.Phonenumber;

/**
 * User data.<br />
 * Use this class to pre-fill any forms of the user data collection flow.<br />
 * For security reasons birthday and SSN are not stored, ever.
 *
 * @author Wijnand
 */
public class UserDataVo {

    public int loanAmount;
    public IdDescriptionPairDisplayVo loanPurpose;

    public String firstName;
    public String lastName;
    public String emailAddress;
    public Phonenumber.PhoneNumber phoneNumber;

    public String address;
    public String apartmentNumber; // Or unit.
    public String city;
    public String state;
    public String zip;
    public IdDescriptionPairDisplayVo housingType;

    public int income;
    public IdDescriptionPairDisplayVo employmentStatus;
    public IdDescriptionPairDisplayVo salaryFrequency;

    public int creditScoreRange;

    /**
     * Creates a new {@link UserDataVo} instance.
     */
    public UserDataVo() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        loanAmount = -1;
        loanPurpose = null;

        firstName = null;
        lastName = null;
        emailAddress = null;
        phoneNumber = null;

        address = null;
        apartmentNumber = null;
        city = null;
        state = null;
        zip = null;
        housingType = null;

        income = -1;
        employmentStatus = null;

        creditScoreRange = -1;
    }
}
