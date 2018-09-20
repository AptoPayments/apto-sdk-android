package com.shiftpayments.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.utils.KeyboardUtil;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.vinaygaba.creditcardview.CreditCardView;

/**
 * Displays the add card screen.
 * @author Adrian
 */
public class AddCardView
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the scan card button has been pressed.
         */
        void scanClickHandler();

        /**
         * Called when the add card button has been pressed.
         */
        void addCardClickHandler();
    }

    private TextView mAddCardButton;
    private TextView mScanCardButton;
    private CreditCardView mCreditCardView;
    private Toolbar mToolbar;
    private AddCardView.ViewListener mListener;

    public AddCardView(Context context) {
        this(context, null);
    }

    /**
     * @see AddCardView#AddCardView
     * @param context See {@link AddCardView#AddCardView}.
     * @param attrs See {@link AddCardView#AddCardView}.
     */
    public AddCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
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
        if (id == R.id.tv_add_bttn) {
            mListener.addCardClickHandler();
        }
        else if (id == R.id.tv_scan_bttn) {
            mListener.scanClickHandler();
        }
    }

    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    public void setCardName(String name) {
        mCreditCardView.setCardName(name);
    }

    public boolean isCreditCardInputValid() {
        // TODO
        return true;
    }

    public String getCardNumber() {
        // TODO
        return "";
    }

    public String getSecurityCode() {
        // TODO
        return "";
    }

    public String getCardNetwork() {
        // TODO
        return "";
    }

    public String getLastFourDigits() {
        // TODO
        /*String cardNumber = mCreditCardForm.getCreditCard().getCardNumber();
        return cardNumber.substring(cardNumber.length() - 4);*/
        return "";
    }

    public String getExpirationDate() {
        // TODO
        return "";
    }

    private void findAllViews() {
        mAddCardButton = findViewById(R.id.tv_add_bttn);
        mScanCardButton = findViewById(R.id.tv_scan_bttn);
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mCreditCardView = findViewById(R.id.credit_card_view);
    }

    private void setupListeners() {
        if (mAddCardButton != null) {
            mAddCardButton.setOnClickListener(this);
        }
        if (mScanCardButton != null) {
            mScanCardButton.setOnClickListener(this);
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mAddCardButton.setBackgroundColor(primaryColor);
        mAddCardButton.setTextColor(contrastColor);
        mScanCardButton.setTextColor(primaryColor);
    }
}
