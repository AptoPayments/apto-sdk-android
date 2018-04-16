package com.shift.link.sdk.ui.models.loanapplication;

import android.content.res.Resources;

import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.storages.LinkStorage;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.vos.LoanDataVo;

import java.util.LinkedList;

import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shift.link.sdk.api.vos.responses.offers.OfferVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.images.GenericImageLoader;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.storages.LinkStorage;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.vos.LoanDataVo;

/**
 * Loan summary {@link Model}.
 * @author Adrian
 */
public class LoanApplicationSummaryModel extends LoanAgreementModel implements ActivityModel, Model {

    private LinkedList<RequiredDataPointVo> mRequiredDataPointsList;

    /**
     * Creates a new {@link LoanApplicationSummaryModel} instance.
     * @param offer The selected loan offer.
     * @param imageLoader Image loader.
     */
    public LoanApplicationSummaryModel(OfferVo offer, GenericImageLoader imageLoader) {
        super(offer, imageLoader);

    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.loan_confirmation_label;
    }

    public void setRequiredData(LinkedList<RequiredDataPointVo> requiredDataPointsList) {
        mRequiredDataPointsList = requiredDataPointsList;
    }

    public LinkedList<RequiredDataPointVo> getRequiredData() {
        return mRequiredDataPointsList;
    }

    public DataPointList getApplicationInfo() {
        return UserStorage.getInstance().getUserData();
    }

    public String getLoanPurpose() {
        LoanDataVo loanData = LinkStorage.getInstance().getLoanData();
        return loanData.loanPurpose.toString();

    }

    public String getLoanPurposeLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_loan_purpose);
    }

    public String getFirstNameLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_first_name);
    }

    public String getLastNameLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_last_name);
    }

    public String getPhoneLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_phone);
    }

    public String getEmailLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_email);
    }

    public String getBirthdayLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_birthday);
    }

    public String getSsnLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_ssn);
    }

    public String getAddressLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_address);
    }

    public String getAptUnitLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_apt_unit);
    }

    public String getZipCodeLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_zip_code);
    }

    public String getCityLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_city);
    }

    public String getStateLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_state);
    }

    public String getHousingStatusLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_housing_status);
    }

    public String getIncomeTypeLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_income_type);
    }

    public String getSalaryFrequencyLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_salary_frequency);
    }

    public String getAnnualIncomeLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_annual_pretax_income);
    }

    public String getMonthlyIncomeLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_monthly_net_income);
    }

    public String getCreditScoreLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_credit_score);
    }

    public String getTimeAtAddressLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_time_at_address);
    }

    public String getPayDayLoanLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_payday_loan);
    }

    public String getArmedForcesLabel(Resources resources) {
        return resources.getString(R.string.loan_application_summary_armed_forces);
    }
}
