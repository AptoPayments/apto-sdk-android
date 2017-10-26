package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import android.content.res.Resources;

import me.ledge.link.sdk.ui.R;

/**
 * Created by adrian on 25/10/2017.
 */

public class DefaultAutoPayStrategy implements FinancialAccountStrategy {

    @Override
    public AutoPayViewModel getViewModel(Resources resources) {
        AutoPayViewModel strategyModel = new AutoPayViewModel();
        strategyModel.showDescription = true;
        strategyModel.description = resources.getString(R.string.enable_auto_pay_error);

        strategyModel.showPrimaryButton = false;
        strategyModel.showSecondaryButton = false;

        return strategyModel;
    }
}
