package com.shift.link.sdk.ui.views.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import me.ledge.common.views.RowView;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.card.FundingSourceModel;
import com.shift.link.sdk.ui.storages.UIStorage;


/**
 * Displays a single transaction
 * @author Adrian
 */
public class FundingSourceView extends CardView implements RowView<FundingSourceModel>,View.OnClickListener {

    private TextView mTitleField;
    private TextView mDescriptionField;
    private RadioButton mButton;
    private ViewListener mListener;
    private FundingSourceModel mData;

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "funding source" has been clicked.
         * @param fundingSource The selected funding source.
         */
        void fundingSourceClickHandler(FundingSourceModel fundingSource);
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
        mTitleField = (TextView) findViewById(R.id.tv_title);
        mDescriptionField = (TextView) findViewById(R.id.tv_description);
        mButton = (RadioButton) findViewById(R.id.rb_funding_source);
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
        if (id == R.id.rb_funding_source) {
            mListener.fundingSourceClickHandler(mData);
        }
    }

    @Override
    public void setData(FundingSourceModel data) {
        mData = data;
        mTitleField.setText(data.getFundingSourceName());
        mDescriptionField.setText(data.getFundingSourceBalance());
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
