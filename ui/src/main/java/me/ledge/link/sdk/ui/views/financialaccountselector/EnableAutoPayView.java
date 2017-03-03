package me.ledge.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the enable auto-pay screen.
 * @author Adrian
 */
public class EnableAutoPayView
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the enable Auto-Pay button has been pressed.
         */
        void enableAutoPayClickHandler();
    }

    private TextView mEnableAutoPayButton;
    private TextView mFinancialAccountInfo;
    private EnableAutoPayView.ViewListener mListener;
    private Toolbar mToolbar;

    public EnableAutoPayView(Context context) {
        super(context);
    }

    public EnableAutoPayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void findAllViews() {
        mEnableAutoPayButton = (TextView) findViewById(R.id.tv_enable_auto_pay_bttn);
        mFinancialAccountInfo = (TextView) findViewById(R.id.tv_financial_account_label);
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
    }

    protected void setupListeners() {
        if (mEnableAutoPayButton != null) {
            mEnableAutoPayButton.setOnClickListener(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_enable_auto_pay_bttn) {
            mListener.enableAutoPayClickHandler();
        }
    }

    /**
     * Stores a new {@link EnableAutoPayView.ViewListener}.
     * @param listener New {@link EnableAutoPayView.ViewListener}.
     */
    public void setListener(EnableAutoPayView.ViewListener listener) {
        mListener = listener;
    }

    public void displayFinancialAccountInfo(String info) {
        mFinancialAccountInfo.setText(info);
    }
}
