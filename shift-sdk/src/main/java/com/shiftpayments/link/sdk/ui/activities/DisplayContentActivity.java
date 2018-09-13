package com.shiftpayments.link.sdk.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.LanguageUtil;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.DisplayContentView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Activity that loads a view with the given content.
 * @author Adrian
 */

public class DisplayContentActivity extends BaseActivity implements DisplayContentView.ViewListener{

    public static final String EXTRA_CONTENT = "com.shiftpayments.link.sdk.ui.activities.CONTENT";
    protected DisplayContentView mView;
    private LoadingSpinnerManager mLoadingSpinnerManager;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();

        Intent intent = getIntent();
        ContentVo contentVo = intent.getParcelableExtra(EXTRA_CONTENT);

        String content = contentVo.value;
        switch(ContentVo.formatValues.valueOf(contentVo.format)) {
            case plain_text:
                showTextDisclaimer(content);
                break;
            case markdown:
                mView.loadMarkdown(content);
                break;
            case external_url:
                // TODO: move to caller activity
                content = parseUrl(content);
                String ext = content.substring(content.length() - 3);
                if(ext.equalsIgnoreCase("PDF")) {
                    // Only download content in case of PDF
                    downloadFile(content);
                }
                else {
                    loadUrl(content);
                }
                break;
        }
    }

    private String parseUrl(String url) {
        Address userAddress = (Address) UserStorage.getInstance().getUserData().getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        if(userAddress != null) {
            url = url.replace("[language]", LanguageUtil.getLanguage()).replace("[state]", userAddress.stateCode.toUpperCase());
        }
        return url;
    }

    private void loadUrl(String url) {
        mWebView = (WebView) findViewById(R.id.wb_pdf_webview);
        mWebView.clearCache(true);
        mWebView.clearHistory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mWebView.getSettings().setSafeBrowsingEnabled(false);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(url);
    }

    private void downloadFile(String url) {
        final DownloadFileTask downloadTask = new DownloadFileTask(DisplayContentActivity.this);
        downloadTask.execute(url);
    }

    private void setView() {
        mView = (DisplayContentView) View.inflate(this, R.layout.act_display_content, null);
        setContentView(mView);
        mView.setViewListener(this);
        mView.showButtons(false);

        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void acceptClickHandler() {
        // Not used
    }

    @Override
    public void cancelClickHandler() {
        // Not used
    }

    @Override
    public void onClose() {
        this.finish();
    }

    private class DownloadFileTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private final String mFileName = "disclaimer.pdf";

        DownloadFileTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // Expect HTTP 200 OK, so we don't mistakenly save error report instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK &&
                        connection.getResponseCode() != HttpURLConnection.HTTP_NOT_MODIFIED) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // Download the file
                input = connection.getInputStream();

                File file = new File(getFilesDir(), mFileName);
                if (file.getParentFile().mkdirs()) {
                    file.createNewFile();
                }
                output = new FileOutputStream(file);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ignored) {
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return "OK";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mLoadingSpinnerManager.showLoading(true);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mLoadingSpinnerManager.showLoading(false);
            if (result == null)
                ApiErrorUtil.showErrorMessage(getString(R.string.disclaimer_error),
                        DisplayContentActivity.this);
            else {
                mView.loadPdf(new File(getFilesDir(), mFileName));
            }
        }
    }

    public static Intent getDisplayContentIntent(Context context, ContentVo contentVo) {
        Intent intent = new Intent(context, DisplayContentActivity.class);
        intent.putExtra(EXTRA_CONTENT, contentVo);
        return intent;
    }

    private void showTextDisclaimer(String textDisclaimer) {
        String disclaimer = "";
        String lineBreak = "<br />";
        String partnerDivider = "<br /><br />";
        StringBuilder result = new StringBuilder();
        if (!TextUtils.isEmpty(textDisclaimer)) {
            result.append(textDisclaimer.replaceAll("\\r?\\n", lineBreak));
            result.append(partnerDivider);
        }
        disclaimer += result.substring(0, result.length() - partnerDivider.length());
        mView.loadPlainText(disclaimer);
    }
}
