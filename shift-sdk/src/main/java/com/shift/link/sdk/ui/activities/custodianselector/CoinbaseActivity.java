package com.shift.link.sdk.ui.activities.custodianselector;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.coinbase.android.sdk.OAuth;
import com.coinbase.api.entity.OAuthTokensResponse;
import com.coinbase.api.exception.CoinbaseException;
import com.shift.link.sdk.sdk.storages.ConfigStorage;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.presenters.custodianselector.CoinbaseDelegate;
import com.shift.link.sdk.ui.workflow.Command;
import com.shift.link.sdk.ui.workflow.ModuleManager;

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
            OAuth.beginAuthorization(this, CLIENT_ID, "user", REDIRECT_URI, null);
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