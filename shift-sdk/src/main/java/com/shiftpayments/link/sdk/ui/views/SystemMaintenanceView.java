package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays the system maintenance screen.
 * @author Adrian
 */
public class SystemMaintenanceView extends RelativeLayout implements View.OnClickListener {

    private TextView mHeader;
    private TextView mBody;
    private TextView mTryAgainButton;
    private ViewListener mListener;

    public SystemMaintenanceView(Context context) {
        super(context);
    }
    public SystemMaintenanceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void tryAgainClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_try_again_bttn) {
            mListener.tryAgainClickHandler();
        }
    }

    private void findAllViews() {
        mHeader = findViewById(R.id.tv_header_text);
        mBody = findViewById(R.id.tv_body_text);
        mTryAgainButton = findViewById(R.id.tv_try_again_bttn);
    }

    private void setupListeners() {
        if (mTryAgainButton != null) {
            mTryAgainButton.setOnClickListener(this);
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        this.setBackgroundColor(primaryColor);
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mHeader.setTextColor(contrastColor);
        mBody.setTextColor(contrastColor);
        mTryAgainButton.setTextColor(primaryColor);
        mTryAgainButton.setBackgroundColor(contrastColor);
    }
}
