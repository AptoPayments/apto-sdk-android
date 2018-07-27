package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;

/**
 * Displays a single transaction
 * @author Adrian
 */
public class TransactionView extends FrameLayout {

    private ImageView mIconView;
    private TextView mAddress;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public TransactionView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public TransactionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mIconView = findViewById(R.id.iv_icon);
        mAddress = findViewById(R.id.tv_address);
    }

    public void setAddress(String address) {
        mAddress.setText(address);
    }
}
