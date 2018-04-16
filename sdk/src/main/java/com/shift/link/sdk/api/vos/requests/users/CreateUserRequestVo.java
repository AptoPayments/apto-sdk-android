package com.shift.link.sdk.api.vos.requests.users;

import com.shift.link.sdk.api.utils.users.EmploymentStatusId;
import com.shift.link.sdk.api.utils.users.HousingTypeId;
import com.shift.link.sdk.api.utils.users.SalaryFrequencyId;

import com.shift.link.sdk.api.utils.users.EmploymentStatusId;
import com.shift.link.sdk.api.utils.users.HousingTypeId;
import com.shift.link.sdk.api.utils.users.SalaryFrequencyId;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to create a new user.
 * @author Wijnand
 */
public class CreateUserRequestVo extends UnauthorizedRequestVo {

    /**
     * First name.
     */
    public String first_name;

    /**
     * Last name.
     */
    public String last_name;

    /**
     * Date of birth.<br />
     * Format: "MM-dd-yyyy".
     */
    public String birthdate;

    /**
     * Social Security Number.
     */
    public String ssn;

    /**
     * Email address.
     */
    public String email;

    /**
     * US phone number, formatted <a href="https://en.wikipedia.org/wiki/E.164">E.164</a>.
     */
    public String phone_number;

    /**
     * Annual pre-tax income in USD.
     */
    public long income;

    /**
     * Number and street name.
     */
    public String street;

    /**
     * Apartment or unit number.
     */
    public String apt;

    /**
     * City.
     */
    public String city;

    /**
     * State.
     */
    public String state;

    /**
     * Zip code.
     */
    public String zip_code;

    /**
     * Self reported credit score range.
     */
    public int credit_range;

    /**
     * Housing type.
     * @see HousingTypeId
     */
    public int housing_type;

    /**
     * Salary frequency.
     * @see SalaryFrequencyId
     */
    public int salary_frequency;

    /**
     * IncomeSource status.
     * @see EmploymentStatusId
     */
    public int employment_status;

    /**
     * Net monthly income.
     */
    public double monthly_net_income;

}
