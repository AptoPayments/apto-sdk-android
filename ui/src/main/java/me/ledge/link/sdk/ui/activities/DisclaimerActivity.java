package me.ledge.link.sdk.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.ledge.link.api.vos.responses.config.DisclaimerVo;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.utils.DisclaimerUtil;
import me.ledge.link.sdk.ui.views.DisclaimerView;

/**
 * Activity that loads a webview with the given URL.
 * @author Adrian
 */

public class DisclaimerActivity extends AppCompatActivity implements DisclaimerView.ViewListener {

    private DisclaimerView mView;
    private static final String mPDFReader = "http://drive.google.com/viewerng/viewer?embedded=true&url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();

        switch(DisclaimerVo.formatValues.valueOf(DisclaimerUtil.disclaimer.format)) {
            case plain_text:
                mView.loadPlainText(DisclaimerUtil.disclaimer.value);
                break;
            case markdown:
                mView.loadMarkdown(DisclaimerUtil.disclaimer.value);
            case external_url:
                loadUrl(DisclaimerUtil.disclaimer.value);
                break;
        }
        LedgeBaseModule currentModule = ModuleManager.getInstance().getCurrentModule();
        if(currentModule != null) {
            currentModule.setActivity(this);
        }
    }

    private void loadUrl(String url) {
        WebView webview = (WebView) findViewById(R.id.wb_pdf_webview);
        webview.clearCache(true);
        webview.clearHistory();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setVisibility(View.VISIBLE);
        webview.loadUrl(mPDFReader + url);
    }

    private void setView() {
        mView = (DisclaimerView) View.inflate(this, R.layout.act_disclaimer, null);
        mView.setViewListener(this);
        setContentView(mView);
    }

    @Override
    public void acceptClickHandler() {
        DisclaimerUtil.onAccept.execute();
    }

    @Override
    public void cancelClickHandler() {
        this.finish();
    }
}
