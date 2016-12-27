package me.ledge.link.sdk.handlers.eventbus.utils;

import java.util.ArrayList;

import me.ledge.link.sdk.handlers.eventbus.activities.loanapplication.EventBusLoanApplicationsListActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.offers.EventBusOffersListActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusAddressActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusAnnualIncomeActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusIdentityVerificationActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusLoanAmountActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.userdata.EventBusTermsActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.verifications.EventBusEmailVerificationActivity;
import me.ledge.link.sdk.handlers.eventbus.activities.verifications.EventBusPhoneVerificationActivity;
import me.ledge.link.sdk.handlers.eventbus.tasks.handlers.EventBusThreeResponseHandler;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;

/**
 * EventBus specific handler configuration.
 * @author Wijnand
 */
public class EventBusHandlerConfigurator implements HandlerConfigurator {

    /** {@inheritDoc} */
    @Override
    public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        ArrayList<Class<? extends MvpActivity>> process = new ArrayList<>(10);
        process.add(EventBusTermsActivity.class);
        process.add(EventBusLoanAmountActivity.class);
        process.add(PersonalInformationActivity.class);
        process.add(EventBusPhoneVerificationActivity.class);
        process.add(EventBusEmailVerificationActivity.class);
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
