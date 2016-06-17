package me.ledge.link.sdk.handlers.eventbus.utils;

import me.ledge.link.sdk.handlers.eventbus.activities.loanapplication.EventBusLoanApplicationsListActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.offers.EventBusOffersListActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusAddressActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusAnnualIncomeActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusIdentityVerificationActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusLoanAmountActivity;
import me.ledge.link.sdk.handlers.eventbus.tasks.handlers.EventBusThreeResponseHandler;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.userdata.TermsActivity;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;

import java.util.ArrayList;

/**
 * EventBus specific handler configuration.
 * @author Wijnand
 */
public class EventBusHandlerConfigurator implements HandlerConfigurator {

    /** {@inheritDoc} */
    @Override
    public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        ArrayList<Class<? extends MvpActivity>> process = new ArrayList<>(8);
        process.add(TermsActivity.class);
        process.add(EventBusLoanAmountActivity.class);
        process.add(PersonalInformationActivity.class);
        process.add(EventBusAddressActivity.class);
        process.add(EventBusAnnualIncomeActivity.class);
        process.add(MonthlyIncomeActivity.class);
        process.add(CreditScoreActivity.class);
        process.add(EventBusIdentityVerificationActivity.class);
        process.add(EventBusOffersListActivity.class);

        return process;
    }

    /** {@inheritDoc} */
    @Override
    public Class<? extends LoanApplicationsListActivity> getApplicationsListActivity() {
        return EventBusLoanApplicationsListActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public ApiResponseHandler getResponseHandler() {
        return new EventBusThreeResponseHandler();
    }
}
