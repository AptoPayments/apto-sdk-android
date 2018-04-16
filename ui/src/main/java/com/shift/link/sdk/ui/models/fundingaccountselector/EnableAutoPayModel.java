package com.shift.link.sdk.ui.models.fundingaccountselector;

import android.content.res.Resources;

import com.shift.link.sdk.ui.models.AbstractActivityModel;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.AutoPayViewModel;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.BankAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.CardAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.DefaultAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.FinancialAccountStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.VirtualCardAutoPayStrategy;

import com.shift.link.sdk.api.vos.datapoints.BankAccount;
import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.AbstractActivityModel;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.AutoPayViewModel;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.BankAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.CardAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.DefaultAutoPayStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.FinancialAccountStrategy;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.VirtualCardAutoPayStrategy;

/**
 * Concrete {@link Model} for the auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayModel extends AbstractActivityModel
        implements Model {

    private FinancialAccountVo mFinancialAccount;

    public EnableAutoPayModel() {
        mFinancialAccount = null;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.enable_auto_pay_title;
    }

    public void setFinancialAccount(FinancialAccountVo financialAccount) {
        this.mFinancialAccount = financialAccount;
    }

    public AutoPayViewModel getEnableAutoPayViewModel(Resources resources) {
        return EnableAutoPayFactory.getStrategy(mFinancialAccount).getViewModel(resources);
    }

    private static class EnableAutoPayFactory {
        static FinancialAccountStrategy getStrategy(FinancialAccountVo account) {
            switch (account.mAccountType) {
                case VirtualCard:
                    return new VirtualCardAutoPayStrategy();
                case Bank:
                    return new BankAutoPayStrategy((BankAccount) account);
                case Card:
                    return new CardAutoPayStrategy((Card) account);
                default:
                    return new DefaultAutoPayStrategy();
            }
        }
    }
}
