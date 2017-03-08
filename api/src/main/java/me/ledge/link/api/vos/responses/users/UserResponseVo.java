package me.ledge.link.api.vos.responses.users;

/**
 * User details.
 * @author Wijnand
 */
public class UserResponseVo {

    /**
     * First name.
     */
    public String first_name;

    /**
     * Last name.
     */
    public String last_name;

    /**
     * Birthday.<br />
     * Format: "mm/dd/yyyy"
     */
    public String birth_date;

    /**
     * Income.
     */
    public long income;

    /**
     * Self reported credit score range.<br />
     * @see me.ledge.link.api.utils.CreditScoreRange
     */
    public int self_reported_credit_score_range;

    /**
     * Email address.
     */
    public String email;

    /**
     * Phone number in E164 format.
     */
    public String phone_number;

    /**
     * Address details.
     */
    public AddressVo address;

}
