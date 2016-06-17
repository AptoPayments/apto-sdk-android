package me.ledge.link.sdk.handlers.otto.utils;

import me.ledge.link.sdk.handlers.otto.activities.loanapplication.OttoLoanApplicationsListActivity;
import me.ledge.link.sdk.handlers.otto.activities.offers.OttoOffersListActivity;
import me.ledge.link.sdk.handlers.otto.activities.userdata.OttoAddressActivity;
import me.ledge.link.sdk.handlers.otto.activities.userdata.OttoAnnualIncomeActivity;
import me.ledge.link.sdk.handlers.otto.activities.userdata.OttoIdentityVerificationActivity;
import me.ledge.link.sdk.handlers.otto.activities.userdata.OttoLoanAmountActivity;
import me.ledge.link.sdk.handlers.otto.tasks.handlers.OttoResponseHandler;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.userdata.TermsActivity;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;

import java.util.ArrayList;

/**
 * Otto specific handler configuration.
 * @author Wijnand
 */
public class OttoHandlerConfigurator implements HandlerConfigurator {

    private ApiResponseHandler mHandler;

    /**
     * Creates a new {@link OttoHandlerConfigurator} instance.
     */
    public OttoHandlerConfigurator() {
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        mHandler = new OttoResponseHandler();
    }

    /** {@inheritDoc} */
    @Override
    public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        ArrayList<Class<? extends MvpActivity>> process = new ArrayList<>(8);
        process.add(TermsActivity.class);
        process.add(OttoLoanAmountActivity.class);
        process.add(PersonalInformationActivity.class);
        process.add(OttoAddressActivity.class);
        process.add(OttoAnnualIncomeActivity.class);
        process.add(MonthlyIncomeActivity.class);
        process.add(CreditScoreActivity.class);
        process.add(OttoIdentityVerificationActivity.class);
        process.add(OttoOffersListActivity.class);

        return process;
    }

    /** {@inheritDoc} */
    @Override
    public Class<? extends LoanApplicationsListActivity> getApplicationsListActivity() {
        return OttoLoanApplicationsListActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public ApiResponseHandler getResponseHandler() {
        return mHandler;
    }
}
