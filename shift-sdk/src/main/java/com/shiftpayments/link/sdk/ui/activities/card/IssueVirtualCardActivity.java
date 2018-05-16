package com.shiftpayments.link.sdk.ui.activities.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.CustodianVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.KycStatusActivity;
import com.shiftpayments.link.sdk.ui.presenters.card.IssueVirtualCardDelegate;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.views.card.IssueVirtualCardView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import org.greenrobot.eventbus.Subscribe;

public class IssueVirtualCardActivity extends AppCompatActivity {

    private IssueVirtualCardView mView;
    static final int KYC_STATUS_INTENT = 1;
    public static final String EXTRA_KYC_STATUS = "com.shiftpayments.link.sdk.ui.activities.card.KYC_STATUS";
    public static final String EXTRA_KYC_REASON = "com.shiftpayments.link.sdk.ui.activities.card.KYC_REASON";

    @Override
    public void onBackPressed() {
        if(ModuleManager.getInstance().getCurrentModule() instanceof IssueVirtualCardDelegate) {
            IssueVirtualCardDelegate delegate = (IssueVirtualCardDelegate) ModuleManager.getInstance().getCurrentModule();
            delegate.onBack();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = (IssueVirtualCardView) View.inflate(this, R.layout.act_issue_virtual_card, null);
        setContentView(mView);
        mView.showLoading(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        IssueVirtualCardRequestVo virtualCardRequestVo = new IssueVirtualCardRequestVo();
        virtualCardRequestVo.cardIssuer = "SHIFT";
        OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(UserStorage.getInstance().getCoinbaseAccessToken(), UserStorage.getInstance().getCoinbaseRefreshToken());
        virtualCardRequestVo.custodian = new CustodianVo("coinbase", coinbaseCredentials);
        ShiftPlatform.issueVirtualCard(virtualCardRequestVo);
    }

    @Override
    public void onStop() {
        super.onStop();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
    }

    @Subscribe
    public void handleVirtualCard(Card card) {
        mView.showLoading(false);

        if (card != null) {
            CardStorage.getInstance().setCard(card);
            if(card.kycStatus.equals(KycStatus.passed)) {
                this.startActivity(new Intent(this, ManageCardActivity.class));
            }
            else {
                startKycStatusActivity(card.kycStatus.toString(), card.kycReason != null ? card.kycReason[0] : null);
            }
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mView.showLoading(false);
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KYC_STATUS_INTENT) {
            if (resultCode == RESULT_OK) {
                this.startActivity(new Intent(this, ManageCardActivity.class));
            }
        }
    }

    private void startKycStatusActivity(String kycStatus, String kycReason) {
        Intent intent = new Intent(this, KycStatusActivity.class);
        intent.putExtra(EXTRA_KYC_STATUS, kycStatus);
        if(kycReason != null) {
            intent.putExtra(EXTRA_KYC_REASON, kycReason);
        }
        this.startActivityForResult(intent, KYC_STATUS_INTENT);
    }
}
