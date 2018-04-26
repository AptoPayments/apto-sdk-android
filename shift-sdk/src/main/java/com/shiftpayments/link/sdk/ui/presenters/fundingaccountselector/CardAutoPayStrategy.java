package com.shiftpayments.link.sdk.ui.presenters.fundingaccountselector;

import android.content.res.Resources;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.ui.R;

/**
 * Created by adrian on 25/10/2017.
 */

public class CardAutoPayStrategy implements FinancialAccountStrategy {

    Card mCard;

    public CardAutoPayStrategy(Card card) {
        mCard = card;
    }

    @Override
    public AutoPayViewModel getViewModel(Resources resources) {
        AutoPayViewModel strategyModel = new AutoPayViewModel();
        strategyModel.showDescription = true;
        strategyModel.description = resources.getString(R.string.enable_auto_pay_card, mCard.lastFourDigits);

        strategyModel.showPrimaryButton = true;
        strategyModel.primaryButtonText = resources.getString(R.string.enable_auto_pay_card_primary_button);

        strategyModel.showSecondaryButton = false;

        return strategyModel;
    }
}
