package me.ledge.link.sdk.ui.eventbus.utils;

import java.util.ArrayList;

import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.activities.link.LoanAmountActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.eventbus.handlers.EventBusThreeResponseHandler;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;

/**
 * EventBus specific handler configuration.
 * @author Wijnand
 */
public class EventBusHandlerConfigurator implements HandlerConfigurator {

    /** {@inheritDoc} */
    @Override
    public ArrayList<Class<? extends MvpActivity>> getProcessOrder() {
        ArrayList<Class<? extends MvpActivity>> process = new ArrayList<>(11);
        // TODO: remove this
        process.add(LoanAmountActivity.class);
        process.add(PersonalInformationActivity.class);
        process.add(PhoneVerificationActivity.class);
        process.add(EmailVerificationActivity.class);
        process.add(AddressActivity.class);
        process.add(AnnualIncomeActivity.class);
        process.add(MonthlyIncomeActivity.class);
        process.add(CreditScoreActivity.class);
        process.add(IdentityVerificationActivity.class);
        process.add(OffersListActivity.class);

        return process;
    }

    /** {@inheritDoc} */
    @Override
    public Class<? extends LoanApplicationsListActivity> getApplicationsListActivity() {
        return LoanApplicationsListActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public ApiResponseHandler getResponseHandler() {
        return new EventBusThreeResponseHandler();
    }
}
