package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays the KYC status screen
 * @author Adrian
 */
public class KycStatusView extends RelativeLayout implements ViewWithToolbar, View.OnClickListener {

    private ViewListener mListener;
    private TextView mTextView;
    private Toolbar mToolbar;
    private Button mRefreshButton;

    public KycStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.bttn_refresh) {
            mListener.refresh();
        }
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void refresh();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    public void setStatusText(String status) {
        mTextView.setText(status);
    }

    private void setUpListeners() {
     mRefreshButton.setOnClickListener(this);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mRefreshButton.setBackgroundColor(primaryColor);

    }

    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mTextView = findViewById(R.id.tv_status_text);
        mRefreshButton = findViewById(R.id.bttn_refresh);
    }
}
