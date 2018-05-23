package com.shiftpayments.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.utils.DateUtil;

/**
 * Displays a single loan application.
 * @author Adrian
 */
public class SelectApplicationCardView extends CardView {

    private TextView mTitleField;
    private SelectLoanApplicationModel mData;

    private static final String DATE_FORMAT = "MM-dd-yyyy HH:mm:ss";

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public SelectApplicationCardView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public SelectApplicationCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
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
    public void setData(SelectLoanApplicationModel data) {
        if (data == null) {
            return;
        }

        mData = data;
        mTitleField.setText(data.getProjectName() + ": " + DateUtil.formatISO8601Timestamp(data.getTimestamp(), DATE_FORMAT));
    }

    /**
     * @return The data this View is showing.
     */
    public SelectLoanApplicationModel getData() {
        return mData;
    }
}
