package com.shiftpayments.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
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
    private CreditCardForm mCreditCardForm;
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
        return mCreditCardForm.isCreditCardValid();
    }

    public String getCardNumber() {
        return mCreditCardForm.getCreditCard().getCardNumber();
    }

    public String getSecurityCode() {
        return mCreditCardForm.getCreditCard().getSecurityCode();
    }

    public String getCardNetwork() {
        return mCreditCardForm.getCreditCard().getCardType().name();
    }

    public String getLastFourDigits() {
        String cardNumber = mCreditCardForm.getCreditCard().getCardNumber();
        return cardNumber.substring(cardNumber.length() - 4);
    }

    public String getExpirationDate() {
        return mCreditCardForm.getCreditCard().getExpDate();
    }

    private void findAllViews() {
        mAddCardButton = findViewById(R.id.tv_add_bttn);
        mScanCardButton = findViewById(R.id.tv_scan_bttn);
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mCreditCardForm = findViewById(R.id.credit_card_form);
        mCreditCardView = findViewById(R.id.credit_card_view);
    }

    private void setupListeners() {
        if (mAddCardButton != null) {
            mAddCardButton.setOnClickListener(this);
        }
        if (mScanCardButton != null) {
            mScanCardButton.setOnClickListener(this);
        }

        mCreditCardForm.setOnCardValidCallback(creditCard -> {
            updateCreditCardView(creditCard);
            KeyboardUtil.hideKeyboard(AddCardView.super.getContext());
        });
    }

    private void updateCreditCardView(CreditCard creditCard) {
        mCreditCardView.setCardNumber(creditCard.getCardNumber());
        mCreditCardView.setExpiryDate(creditCard.getExpDate());
        mCreditCardView.setType(creditCard.getCardType().ordinal());
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mAddCardButton.setBackgroundColor(primaryColor);
        mAddCardButton.setTextColor(contrastColor);
        mScanCardButton.setTextColor(primaryColor);
    }
}
