package me.ledge.link.sdk.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.ledge.link.api.vos.responses.config.ContentVo;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.utils.DisclaimerUtil;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.DisclaimerView;

/**
 * Activity that loads a view with the given disclaimer.
 * @author Adrian
 */

public class DisclaimerActivity extends AppCompatActivity implements DisclaimerView.ViewListener {

    private DisclaimerView mView;
    private boolean mDisclosureLoaded = false;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();

        String disclaimer = DisclaimerUtil.disclaimer.value;
        switch(ContentVo.formatValues.valueOf(DisclaimerUtil.disclaimer.format)) {
            case plain_text:
                mView.loadPlainText(disclaimer);
                break;
            case markdown:
                mView.loadMarkdown(disclaimer);
                mDisclosureLoaded = true;
                break;
            case external_url:
                String ext = disclaimer.substring(disclaimer.length() - 3);
                if(ext.equalsIgnoreCase("PDF")) {
                    // Only download disclaimer in case of PDF
                    downloadFile(disclaimer);
                }
                else {
                    loadUrl(disclaimer);
                }
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
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mDisclosureLoaded = true;
            }
        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.setVisibility(View.VISIBLE);
        webview.loadUrl(url);
    }

    private void downloadFile(String url) {
        final DownloadFileTask downloadTask = new DownloadFileTask(DisclaimerActivity.this);
        downloadTask.execute(url);
    }

    private void setView() {
        mView = (DisclaimerView) View.inflate(this, R.layout.act_disclaimer, null);
        mView.setViewListener(this);
        setContentView(mView);

        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
    }

    @Override
    public void acceptClickHandler() {
        if(mDisclosureLoaded) {
            DisclaimerUtil.onAccept.execute();
            this.finish();
        }
    }

    @Override
    public void cancelClickHandler() {
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
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK ||
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
            return null;
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
                mView.displayErrorMessage(getString(R.string.disclaimer_error));
            else {
                mDisclosureLoaded = true;
                mView.loadPdf(new File(getFilesDir(), mFileName));
            }
        }
    }
}
