package com.shiftpayments.link.sdk.ui.activities.card;

import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.BaseActivity;
import com.shiftpayments.link.sdk.ui.presenters.card.CardWelcomeDelegate;
import com.shiftpayments.link.sdk.ui.views.card.CardWelcomeView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

public class CardWelcomeActivity extends BaseActivity {
    private CardWelcomeView mView;
    private CardWelcomeDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = (CardWelcomeView) View.inflate(this, R.layout.act_card_welcome, null);
        setContentView(mView);
        if(ModuleManager.getInstance().getCurrentModule() instanceof CardWelcomeDelegate) {
            mDelegate = (CardWelcomeDelegate) ModuleManager.getInstance().getCurrentModule();
        }
        else {
            throw new NullPointerException("Received Module does not implement CardWelcomeDelegate!");
        }
    }
}
