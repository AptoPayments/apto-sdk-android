package com.shiftpayments.link.sdk.ui.eventbus.utils;

import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.activities.link.LoanAmountActivity;
import com.shiftpayments.link.sdk.ui.activities.offers.OffersListActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.AddressActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.CreditScoreActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import com.shiftpayments.link.sdk.ui.activities.verification.EmailVerificationActivity;
import com.shiftpayments.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import com.shiftpayments.link.sdk.ui.eventbus.handlers.EventBusThreeResponseHandler;
import com.shiftpayments.link.sdk.ui.utils.HandlerConfigurator;

import java.util.ArrayList;

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
