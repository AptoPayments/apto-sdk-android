package com.shiftpayments.link.sdk.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.presenters.card.KycStatusDelegate;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.KycStatusView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import org.greenrobot.eventbus.Subscribe;

/**
 * Activity shown when the KYC status is not passed
 * @author Adrian
 */

public class KycStatusActivity extends BaseActivity implements KycStatusView.ViewListener {

    private KycStatusView mView;
    private KycStatusDelegate mDelegate;

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
        if (ModuleManager.getInstance().getCurrentModule() instanceof KycStatusDelegate) {
            mDelegate = (KycStatusDelegate) ModuleManager.getInstance().getCurrentModule();
        } else {
            throw new NullPointerException("Received Module does not implement KycStatusDelegate!");
        }
    }

    @Override
    public void refresh() {
        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccount(CardStorage.getInstance().getCard().mAccountId);
    }

    /**
     * Called when the get financial account response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(Card card) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);
        if(card.kycStatus.equals(KycStatus.passed)) {
            mDelegate.onKycPassed();
            finish();
        }
        else {
            ApiErrorUtil.showErrorMessage("KYC Status: " + card.kycStatus.name(), this);
        }
    }

    @Subscribe
    public void handleResponse(ApiErrorVo error) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        ApiErrorUtil.showErrorMessage(error, this);
    }

    @Override
    public void onBackPressed() {
        // Disabled
    }

    private void setView() {
        mView = (KycStatusView) View.inflate(this, R.layout.act_kyc_status, null);
        setContentView(mView);
        mView.getToolbar().setTitle(getString(R.string.kyc_status_title));
        mView.setViewListener(this);
        if(UIStorage.getInstance().isEmbeddedMode()) {
            mView.getToolbar().setNavigationOnClickListener(v -> {
                mDelegate.onKycClosed();
            });
            mView.showCloseButton();
        }
    }
}
