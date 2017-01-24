package me.ledge.link.sdk.ui.activities.financialaccountselector;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.AddBankAccountDelegate;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.AddBankAccountPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.financialaccountselector.AddBankAccountView;

import static me.ledge.link.sdk.sdk.LedgeLinkSdk.getApiWrapper;

/**
 * Wires up the MVP pattern for the plaid webview.
 * @author Adrian
 */

public class AddBankAccountActivity
        extends MvpActivity<AddBankAccountModel, AddBankAccountView, AddBankAccountPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddBankAccountView createView() {
        return (AddBankAccountView) View.inflate(this, R.layout.act_add_bank_account, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddBankAccountPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddBankAccountDelegate) {
            return new AddBankAccountPresenter(this, (AddBankAccountDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddBankAccountDelegate!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView plaidLinkWebview = (WebView) findViewById(R.id.webview);
        configureWebView(plaidLinkWebview);
        plaidLinkWebview.loadUrl(getPlaidURL(), getHTTPHeader());

        plaidLinkWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri parsedUri = Uri.parse(url);
                if (parsedUri.getScheme().equals("ledge")) {
                    String actionType = parsedUri.getHost();
                    switch (actionType) {
                        case "handlePublicToken":
                            String token = parsedUri.getFragment();
                            mPresenter.publicTokenReceived(token);
                            break;
                        case "closeLinkModal":
                            mPresenter.onBack();
                            break;
                        case "handleOnExit":
                            mPresenter.onBack();
                            break;
                    }

                    return true;
                }

                return false;
            }
        });
    }

    private void configureWebView(WebView plaidLinkWebview) {
        WebSettings webSettings = plaidLinkWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private String getPlaidURL() {
        return getApiWrapper().getApiEndPoint() + "/" + LinkApiWrapper.ADD_BANK_ACCOUNTS_PATH;
    }

    private HashMap<String, String> getHTTPHeader() {
        HashMap<String, String> additionalHttpHeaders = new HashMap<>();
        additionalHttpHeaders.put("Developer-Authorization", "Bearer=" + getApiWrapper().getDeveloperKey());
        additionalHttpHeaders.put("Project", "Bearer=" + getApiWrapper().getProjectToken());
        additionalHttpHeaders.put("Authorization", "Bearer=" + UserStorage.getInstance().getBearerToken());

        return additionalHttpHeaders;
    }
}
