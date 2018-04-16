package me.ledge.link.sdk.ui.models.fundingaccountselector;

import android.content.res.Resources;

import me.ledge.link.sdk.api.vos.datapoints.BankAccount;
import me.ledge.link.sdk.api.vos.Card;
import me.ledge.link.sdk.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.AbstractActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.AutoPayViewModel;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.BankAutoPayStrategy;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.CardAutoPayStrategy;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.DefaultAutoPayStrategy;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.FinancialAccountStrategy;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.VirtualCardAutoPayStrategy;

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
