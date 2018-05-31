package com.shiftpayments.link.sdk.ui.views.custodianselector;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.custodianselector.AddCustodianModel;


/**
 * Displays a single request to add a custodian.
 * @author Adrian
 */
public class AddCustodianCardView extends CardView {

    private ImageView mIconView;
    private TextView mTitleField;
    private TextView mDescriptionField;
    private AddCustodianModel mData;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public AddCustodianCardView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public AddCustodianCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mIconView = findViewById(R.id.iv_icon);
        mTitleField = findViewById(R.id.tv_title);
        mDescriptionField = findViewById(R.id.tv_description);
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
    public void setData(AddCustodianModel data) {
        if (data == null) {
            return;
        }

        mData = data;
        mTitleField.setText(data.getTitleResourceId());
        mIconView.setImageResource(data.getIconResourceId());
        mDescriptionField.setText(data.getDescription());
    }

    /**
     * @return The data this View is showing.
     */
    public AddCustodianModel getData() {
        return mData;
    }
}
