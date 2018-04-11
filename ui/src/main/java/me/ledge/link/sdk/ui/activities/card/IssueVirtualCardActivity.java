package me.ledge.link.sdk.ui.activities.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.CustodianVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.KYCStatus;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import me.ledge.link.sdk.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.ShiftUi;
import me.ledge.link.sdk.ui.activities.KYCStatusActivity;
import me.ledge.link.sdk.ui.storages.CardStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.card.IssueVirtualCardView;

public class IssueVirtualCardActivity extends AppCompatActivity {

    private IssueVirtualCardView mView;
    static final int KYC_STATUS_INTENT = 1;

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
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        IssueVirtualCardRequestVo virtualCardRequestVo = new IssueVirtualCardRequestVo();
        virtualCardRequestVo.cardIssuer = "SHIFT";
        OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(UserStorage.getInstance().getCoinbaseAccessToken(), UserStorage.getInstance().getCoinbaseRefreshToken());
        virtualCardRequestVo.custodian = new CustodianVo("coinbase", coinbaseCredentials);
        ShiftUi.issueVirtualCard(virtualCardRequestVo);
    }

    @Override
    public void onStop() {
        super.onStop();
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
    }

    @Subscribe
    public void handleVirtualCard(Card card) {
        mView.showLoading(false);

        if (card != null) {
            CardStorage.getInstance().setCard(card);
            if(card.kycStatus.equals(KYCStatus.passed)) {
                this.startActivity(new Intent(this, ManageCardActivity.class));
            }
            else {
                Intent intent = new Intent(this, KYCStatusActivity.class);
                intent.putExtra("KYC_STATUS", card.kycStatus.toString());
                if(card.kycReason != null) {
                    intent.putExtra("KYC_REASON", card.kycReason[0]);
                }
                this.startActivityForResult(intent, KYC_STATUS_INTENT);
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
}
