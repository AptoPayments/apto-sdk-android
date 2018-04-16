package com.shift.link.sdk.ui.eventbus.utils;

import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.activities.link.LoanAmountActivity;
import com.shift.link.sdk.ui.activities.offers.OffersListActivity;
import com.shift.link.sdk.ui.activities.userdata.AddressActivity;
import com.shift.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import com.shift.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import com.shift.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import com.shift.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import com.shift.link.sdk.ui.activities.verification.EmailVerificationActivity;
import com.shift.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import com.shift.link.sdk.ui.eventbus.handlers.EventBusThreeResponseHandler;
import com.shift.link.sdk.ui.utils.HandlerConfigurator;

import java.util.ArrayList;

import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.activities.link.LoanAmountActivity;
import com.shift.link.sdk.ui.activities.offers.OffersListActivity;
import com.shift.link.sdk.ui.activities.userdata.AddressActivity;
import com.shift.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import com.shift.link.sdk.ui.activities.userdata.CreditScoreActivity;
import com.shift.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import com.shift.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import com.shift.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import com.shift.link.sdk.ui.activities.verification.EmailVerificationActivity;
import com.shift.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import com.shift.link.sdk.ui.eventbus.handlers.EventBusThreeResponseHandler;
import com.shift.link.sdk.ui.utils.HandlerConfigurator;

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
    public ApiResponseHandler getResponseHandler() {
        return new EventBusThreeResponseHandler();
    }
}
