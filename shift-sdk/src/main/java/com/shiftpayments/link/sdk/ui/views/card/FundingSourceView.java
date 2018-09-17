package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.card.BalanceModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

import me.ledge.common.views.RowView;


/**
 * Displays a single transaction
 * @author Adrian
 */
public class FundingSourceView extends CardView implements RowView<BalanceModel>,View.OnClickListener {

    private TextView mTitleField;
    private TextView mSubtitleField;
    private TextView mDescriptionField;
    private RadioButton mButton;
    private ViewListener mListener;
    private BalanceModel mData;

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the balance has been clicked.
         * @param selectedBalance The selected balance
         */
        void balanceClickHandler(BalanceModel selectedBalance);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public FundingSourceView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public FundingSourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mTitleField = findViewById(R.id.tv_title);
        mSubtitleField = findViewById(R.id.tv_subtitle);
        mDescriptionField = findViewById(R.id.tv_description);
        mButton = findViewById(R.id.rb_funding_source);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColor();
    }

    /**
     * Sets up child View callback listeners.
     */
    private void setUpListeners() {
        mButton.setOnClickListener(this);
        mTitleField.setOnClickListener(this);
        mSubtitleField.setOnClickListener(this);
        mDescriptionField.setOnClickListener(this);
    }

    private void setColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList colorStateList = UIStorage.getInstance().getRadioButtonColors();
            mButton.setButtonTintList(colorStateList);
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.rb_funding_source || id == R.id.tv_title || id == R.id.tv_description ||
                id == R.id.tv_subtitle) {
            mListener.balanceClickHandler(mData);
        }
    }

    @Override
    public void setData(BalanceModel data) {
        mData = data;
        mTitleField.setText(data.getBalanceName());
        mSubtitleField.setText(data.getBalanceAmount());
        mDescriptionField.setText(data.toString());
        mButton.setChecked(data.isSelected());
    }

    @Override
    public void reset() {

    }

    /**
     * Stores a new callback listener that this View will invoke.
     * @param listener New callback listener.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }
}
