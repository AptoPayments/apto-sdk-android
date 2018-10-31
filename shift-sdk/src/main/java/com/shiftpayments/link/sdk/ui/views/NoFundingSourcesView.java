package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays the view shown when there are no funding sources
 * @author Adrian
 */
public class NoFundingSourcesView extends LinearLayout {

    private TextView mTextView;
    private ImageView mImageView;

    public NoFundingSourcesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    private void setColors() {
        mTextView.setTextColor(UIStorage.getInstance().getTextTertiaryColor());
        mImageView.setColorFilter(UIStorage.getInstance().getIconSecondaryColor());
    }

    private void findAllViews() {
        mTextView = findViewById(R.id.tv_no_funding_sources_label);
        mImageView = findViewById(R.id.iv_no_funding_sources);
    }
}
