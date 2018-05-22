package com.shiftpayments.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;

/**
 * Displays a single financial account.
 * @author Adrian
 */
public class SelectFinancialAccountCardView extends CardView {

    private ImageView mIconView;
    private TextView mTitleField;
    private SelectFinancialAccountModel mData;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public SelectFinancialAccountCardView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public SelectFinancialAccountCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mIconView = findViewById(R.id.iv_icon);
        mTitleField = findViewById(R.id.tv_title);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(SelectFinancialAccountModel data) {
        if (data == null) {
            return;
        }

        mData = data;
        mTitleField.setText(data.getDescription());
        mIconView.setImageResource(data.getIconResourceId());
    }

    /**
     * @return The data this View is showing.
     */
    public SelectFinancialAccountModel getData() {
        return mData;
    }
}
