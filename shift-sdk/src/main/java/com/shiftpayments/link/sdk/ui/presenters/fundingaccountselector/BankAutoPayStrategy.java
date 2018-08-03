package com.shiftpayments.link.sdk.ui.presenters.fundingaccountselector;

import android.content.res.Resources;

import com.shiftpayments.link.sdk.api.vos.datapoints.BankAccount;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Created by adrian on 25/10/2017.
 */

public class BankAutoPayStrategy implements FinancialAccountStrategy {

    BankAccount mBankAccount;

    public BankAutoPayStrategy(BankAccount account) {
        mBankAccount=account;
    }

    @Override
    public AutoPayViewModel getViewModel(Resources resources) {
        AutoPayViewModel strategyModel = new AutoPayViewModel();
        strategyModel.showDescription = true;
        strategyModel.description = resources.getString(R.string.enable_auto_pay_bank, mBankAccount.bankName);

        strategyModel.showPrimaryButton = false;
        strategyModel.showSecondaryButton = false;

        return strategyModel;
    }
}
