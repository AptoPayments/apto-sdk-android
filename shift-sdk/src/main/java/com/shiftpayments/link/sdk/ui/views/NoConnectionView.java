package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays the no internet connection screen
 * @author Adrian
 */
public class NoConnectionView extends RelativeLayout {

    private TextView mHeader;
    private TextView mBody;

    public NoConnectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    private void findAllViews() {
        mHeader = findViewById(R.id.tv_header_text);
        mBody = findViewById(R.id.tv_body_text);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        this.setBackgroundColor(primaryColor);
        int textColor = UIStorage.getInstance().getPrimaryContrastColor();
        mHeader.setTextColor(textColor);
        mBody.setTextColor(textColor);
    }
}
