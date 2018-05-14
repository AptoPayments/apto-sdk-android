package com.shiftpayments.link.sdk.ui.activities.custodianselector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.coinbase.android.sdk.OAuth;
import com.coinbase.api.entity.OAuthCodeRequest;
import com.coinbase.api.entity.OAuthTokensResponse;
import com.coinbase.api.exception.CoinbaseException;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CoinbaseDelegate;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import org.joda.money.Money;

/**
 * Created by adrian on 20/02/2018.
 */

public class CoinbaseActivity extends Activity {

    private CoinbaseDelegate mCurrentModule;
    private String CLIENT_ID;
    private String CLIENT_SECRET;

    @Override
    protected void onNewIntent(final Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("android.intent.action.VIEW")) {
            new CompleteAuthorizationTask(this::finish).execute(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String REDIRECT_URI = getString(R.string.coinbase_redirect_uri);
        CLIENT_ID = ConfigStorage.getInstance().getCoinbaseClientId();
        CLIENT_SECRET = ConfigStorage.getInstance().getCoinbaseClientSecret();
        if(ModuleManager.getInstance().getCurrentModule() instanceof CoinbaseDelegate) {
            mCurrentModule = (CoinbaseDelegate) ModuleManager.getInstance().getCurrentModule();
        }
        else {
            throw new NullPointerException("Received Module does not implement CoinbaseDelegate!");
        }

        // Launch the web browser or Coinbase app to authenticate the user.
        try {
            OAuthCodeRequest.Meta meta = new OAuthCodeRequest.Meta();
            meta.setSendLimitAmount(Money.parse("USD 1"));
            meta.setSendLimitPeriod(OAuthCodeRequest.Meta.Period.DAILY);
            OAuth.beginAuthorization(this, CLIENT_ID, "wallet:user:read,wallet:user:read,wallet:accounts:read,wallet:transactions:read,wallet:transactions:send,wallet:buys:create", REDIRECT_URI, meta);
        } catch (CoinbaseException e) {
            mCurrentModule.onCoinbaseException(e);
        }
    }

    public class CompleteAuthorizationTask extends AsyncTask<Intent, Integer, OAuthTokensResponse> {
        Command onFinish;
        public CompleteAuthorizationTask(Command onFinishCallback) {
            onFinish = onFinishCallback;
        }

        @Override
        protected OAuthTokensResponse doInBackground(Intent... intents) {
            try {
                return OAuth.completeAuthorization(CoinbaseActivity.this, CLIENT_ID, CLIENT_SECRET, intents[0].getData());
            } catch (Exception e) {
                mCurrentModule.onCoinbaseException(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(OAuthTokensResponse oAuthTokensResponse) {
            super.onPostExecute(oAuthTokensResponse);
            if(oAuthTokensResponse != null) {
                mCurrentModule.coinbaseTokensRetrieved(
                        oAuthTokensResponse.getAccessToken(),
                        oAuthTokensResponse.getRefreshToken());
            }
            onFinish.execute();
        }
    }
}
