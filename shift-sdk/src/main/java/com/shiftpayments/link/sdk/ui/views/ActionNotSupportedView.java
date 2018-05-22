package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays the disclaimer webview.
 * @author Adrian
 */
public class ActionNotSupportedView extends RelativeLayout implements ViewWithToolbar {

    private TextView mTextView;
    private Toolbar mToolbar;

    public ActionNotSupportedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setErrorText(String error) {
        mTextView.setText(error);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
    }

    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mTextView = findViewById(R.id.tv_status_text);
    }
}
