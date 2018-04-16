package com.shift.link.sdk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.shift.link.sdk.ui.ShiftUi;
import com.shift.link.sdk.ui.views.KYCStatusView;

import org.greenrobot.eventbus.Subscribe;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.requests.financialaccounts.KYCStatus;
import com.shift.link.sdk.sdk.LedgeLinkSdk;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.ShiftUi;
import com.shift.link.sdk.ui.storages.CardStorage;
import com.shift.link.sdk.ui.views.KYCStatusView;

/**
 * Activity shown when the KYC status is not passed
 * @author Adrian
 */

public class KYCStatusActivity extends AppCompatActivity implements KYCStatusView.ViewListener {

    private KYCStatusView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String kycStatus = extras.getString("KYC_STATUS");
            String kycReason = extras.getString("KYC_REASON") == null ? ""
                    : extras.getString("KYC_REASON");
            // TODO: set different texts depending on KYC status
            mView.setStatusText(getString(R.string.kyc_status_application_under_review));
        }
    }

    private void setView() {
        mView = (KYCStatusView) View.inflate(this, R.layout.act_kyc_status, null);
        setContentView(mView);
        mView.getToolbar().setTitle(getString(R.string.kyc_status_title));
        mView.setViewListener(this);
    }

    @Override
    public void refresh() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        ShiftUi.getFinancialAccount(CardStorage.getInstance().getCard().mAccountId);;
    }

    /**
     * Called when the get financial account response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(Card card) {
        Log.d("ADRIAN", "KYCStatusActivity handleResponse: " + card.kycStatus.toString());
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);
        if(card.kycStatus.equals(KYCStatus.passed)) {
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }
}
