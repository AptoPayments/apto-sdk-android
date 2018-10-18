package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.CardWelcomeDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.CardWelcomePresenter;
import com.shiftpayments.link.sdk.ui.views.card.CardWelcomeView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

public class CardWelcomeActivity extends FragmentMvpActivity<ManageCardModel, CardWelcomeView, CardWelcomePresenter> {

    @Override
    protected CardWelcomeView createView() {
        return (CardWelcomeView) View.inflate(this, R.layout.act_card_welcome, null);
    }

    @Override
    protected CardWelcomePresenter createPresenter(BaseDelegate delegate) {
        if(ModuleManager.getInstance().getCurrentModule() instanceof CardWelcomeDelegate) {
            return new CardWelcomePresenter((CardWelcomeDelegate) ModuleManager.getInstance().getCurrentModule());
        }
        else {
            throw new NullPointerException("Received Module does not implement CardWelcomeDelegate!");
        }
    }
}
