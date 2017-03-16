package me.ledge.link.sdk.ui.activities.financialaccountselector;

import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.financialaccountselector.AddBankAccountModel;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.AddBankAccountDelegate;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.AddBankAccountPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
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
        plaidLinkWebview.loadUrl(getPlaidURL(), getApiWrapper().getHTTPHeaders());

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

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if(LedgeLinkUi.trustSelfSigned && error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            plaidLinkWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private String getPlaidURL() {
        return getApiWrapper().getApiEndPoint() + "/" + LinkApiWrapper.PLAID_WEB_URL;
    }
}
