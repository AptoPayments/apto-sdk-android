package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

import java.io.File;

import br.tiagohm.markdownview.MarkdownView;

/**
 * Displays the disclaimer webview.
 * @author Adrian
 */
public class DisclaimerView extends RelativeLayout implements View.OnClickListener, ViewWithIndeterminateLoading {

    private TextView mAcceptButton;
    private TextView mCancelButton;
    private MarkdownView mMarkdownView;
    private TextView mTextView;
    private ViewListener mListener;
    private PDFView mPdfView;
    private LoadingView mLoadingView;

    public DisclaimerView(Context context) {
        this(context, null);
    }
    public DisclaimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void acceptClickHandler();
        void cancelClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_accept_pdf) {
            mListener.acceptClickHandler();
        }
        else if(id == R.id.tv_cancel_pdf) {
            mListener.cancelClickHandler();
        }
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void loadMarkdown(String markDown) {
        mMarkdownView.setVisibility(VISIBLE);
        mMarkdownView.setBackgroundColor(Color.TRANSPARENT);
        mMarkdownView.loadMarkdown(markDown);
    }

    public void loadPlainText(String text) {
        mTextView.setVisibility(VISIBLE);
        mTextView.setText(text);
    }

    public void loadPdf(File pdfFile) {
        mPdfView.setVisibility(VISIBLE);
        mPdfView.fromFile(pdfFile).load();
    }

    private void findAllViews() {
        mAcceptButton = findViewById(R.id.tv_accept_pdf);
        mCancelButton = findViewById(R.id.tv_cancel_pdf);
        mMarkdownView = findViewById(R.id.md_disclaimer_markdown);
        mTextView = findViewById(R.id.tv_disclaimer_text);
        mPdfView = findViewById(R.id.pdfView);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    private void setupListeners() {
        if (mAcceptButton != null) {
            mAcceptButton.setOnClickListener(this);
        }
        if (mCancelButton != null) {
            mCancelButton.setOnClickListener(this);
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        mAcceptButton.setTextColor(primaryColor);
    }
}
