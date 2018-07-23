package com.shiftpayments.link.sdk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.card.IssueVirtualCardActivity;
import com.shiftpayments.link.sdk.ui.views.IssueCardErrorView;

/**
 * Activity shown when there was an error during the issuance of the card
 * @author Adrian
 */

public class IssueCardErrorActivity extends BaseActivity
        implements IssueCardErrorView.ViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IssueCardErrorView view = (IssueCardErrorView) View.inflate(this, R.layout.act_issue_card_error, null);
        view.setViewListener(this);
        setContentView(view);
    }

    @Override
    public void tryAgainClickHandler() {
        Intent intent = new Intent(this, IssueVirtualCardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
