package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
public class DisplayContentView extends RelativeLayout implements View.OnClickListener,
        ViewWithIndeterminateLoading, ViewWithToolbar {

    private ViewListener mListener;
    private TextView mAcceptButton;
    private TextView mCancelButton;
    private RelativeLayout mButtonsHolder;
    private MarkdownView mMarkdownContent;
    private TextView mTextContent;
    private PDFView mPdfContent;
    private LoadingView mLoadingView;
    private Toolbar mToolbar;
    private android.support.design.widget.AppBarLayout mToolbarHolder;

    public DisplayContentView(Context context) {
        this(context, null);
    }
    public DisplayContentView(Context context, AttributeSet attrs) {
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
        void onClose();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
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
        } else if (id == R.id.toolbar) {
            mListener.onClose();
        }

    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void loadMarkdown(String markDown) {
        mMarkdownContent.setVisibility(VISIBLE);
        mMarkdownContent.setBackgroundColor(Color.TRANSPARENT);
        mMarkdownContent.loadMarkdown(markDown);
    }

    public void loadPlainText(String text) {
        mTextContent.setVisibility(VISIBLE);
        mTextContent.setText(text);
    }

    public void loadPdf(File pdfFile) {
        mPdfContent.setVisibility(VISIBLE);
        mPdfContent.fromFile(pdfFile).load();
    }

    public void showButtons(boolean show) {
        if(show) {
            mButtonsHolder.setVisibility(VISIBLE);
            mAcceptButton.setVisibility(VISIBLE);
            mCancelButton.setVisibility(VISIBLE);
        }
        else {
            mButtonsHolder.setVisibility(INVISIBLE);
        }
    }

    public void showToolbar(boolean show) {
        if(show) {
            mToolbar.setVisibility(VISIBLE);
            mToolbarHolder.setExpanded(true);
        }
        else {
            mToolbar.setVisibility(GONE);
            mToolbarHolder.setExpanded(false);
        }
    }

    private void findAllViews() {
        mAcceptButton = findViewById(R.id.tv_accept_pdf);
        mCancelButton = findViewById(R.id.tv_cancel_pdf);
        mMarkdownContent = findViewById(R.id.md_content_markdown);
        mTextContent = findViewById(R.id.tv_content_text);
        mPdfContent = findViewById(R.id.pdf_content);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mButtonsHolder = findViewById(R.id.ll_buttons_holder);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarHolder = findViewById(R.id.display_content_toolbar);
    }

    private void setupListeners() {
        if (mAcceptButton != null) {
            mAcceptButton.setOnClickListener(this);
        }
        if (mCancelButton != null) {
            mCancelButton.setOnClickListener(this);
        }
        if (mToolbar != null) {
            mToolbar.setOnClickListener(this);
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        mAcceptButton.setTextColor(primaryColor);
        Drawable closeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_close);
        closeIcon.setColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(closeIcon);
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
    }
}
