package com.shiftpayments.link.sdk.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.KycStatusView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Activity shown when the KYC status is not passed
 * @author Adrian
 */

public class KycStatusActivity extends BaseActivity implements KycStatusView.ViewListener {

    private KycStatusView mView;

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

    @Override
    public void refresh() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccount(CardStorage.getInstance().getCard().mAccountId);
    }

    /**
     * Called when the get financial account response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(Card card) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);
        if(card.kycStatus.equals(KycStatus.passed)) {
            startActivity(new Intent(this, ManageCardActivity.class));
            finish();
        }
        else {
            ApiErrorUtil.showErrorMessage("KYC Status: " + card.kycStatus.name(), this);
        }
    }

    @Subscribe
    public void handleResponse(ApiErrorVo error) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ApiErrorUtil.showErrorMessage(error, this);
    }

    private void setView() {
        mView = (KycStatusView) View.inflate(this, R.layout.act_kyc_status, null);
        setContentView(mView);
        mView.getToolbar().setTitle(getString(R.string.kyc_status_title));
        mView.setViewListener(this);
    }
}
