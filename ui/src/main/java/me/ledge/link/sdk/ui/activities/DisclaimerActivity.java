package me.ledge.link.sdk.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

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

        WebView webview = (WebView) findViewById(R.id.pdf_webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(mPDFReader + DisclaimerUtil.disclaimerURL);
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
}
