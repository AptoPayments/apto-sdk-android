package com.shiftpayments.link.sdk.ui.activities.financialaccountselector;

import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddBankAccountDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddBankAccountPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.financialaccountselector.AddBankAccountView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddBankAccountDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddBankAccountPresenter;

import java.util.HashMap;

import static com.shiftpayments.link.sdk.sdk.ShiftLinkSdk.getApiWrapper;

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
        plaidLinkWebview.loadUrl(getPlaidURL(), getApiWrapper().getHTTPHeaders());

        plaidLinkWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri parsedUri = Uri.parse(url);
                if (parsedUri.getScheme().equals("plaidlink")) {
                    String action = parsedUri.getHost();
                    HashMap<String, String> linkData = parseLinkUriData(parsedUri);

                    if (action.equals("connected")) {
                        // User successfully linked
                        mPresenter.publicTokenReceived(linkData.get("public_token"));
                    } else if (action.equals("exit")) {
                        mPresenter.onBack();
                    }
                    // Override URL loading
                    return true;
                } else {
                    // Unknown case - do not override URL loading
                    return false;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if(ShiftPlatform.trustSelfSigned && error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                }
                else {
                    super.onReceivedSslError(view, handler, error);
                }
            }
        });
    }

    private void configureWebView(WebView plaidLinkWebview) {
        WebSettings webSettings = plaidLinkWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private String getPlaidURL() {
        return getApiWrapper().getApiEndPoint() + "/" + ShiftApiWrapper.PLAID_WEB_URL;
    }

    // Parse a Link redirect URL querystring into a HashMap for easy manipulation and access
    public HashMap<String,String> parseLinkUriData(Uri linkUri) {
        HashMap<String,String> linkData = new HashMap<String,String>();
        for(String key : linkUri.getQueryParameterNames()) {
            linkData.put(key, linkUri.getQueryParameter(key));
        }
        return linkData;
    }
}
