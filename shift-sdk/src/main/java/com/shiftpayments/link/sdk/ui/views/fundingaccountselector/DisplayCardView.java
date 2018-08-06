package com.shiftpayments.link.sdk.ui.views.fundingaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.vinaygaba.creditcardview.CreditCardView;

/**
 * Displays the display card screen.
 * @author Adrian
 */
public class DisplayCardView
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener {

    private CreditCardView mCreditCardView;
    private Toolbar mToolbar;
    private TextView mCardBalance;
    private TextView mPrimaryButton;
    private TextView mSecondaryButton;
    private ViewListener mListener;

    public DisplayCardView(Context context) {
        this(context, null);
    }

    public DisplayCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void primaryButtonClickHandler();
        void secondaryButtonClickHandler();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
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
        setColors();
        setUpListeners();
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_display_card_primary_bttn) {
            mListener.primaryButtonClickHandler();
        }
        else if(id == R.id.tv_display_card_secondary_bttn) {
            mListener.secondaryButtonClickHandler();
        }
    }

    public void setCardNumber(String cardNumber) {
        mCreditCardView.setCardNumber(cardNumber);
    }

    public void setExpiryDate(String expiryDate) {
        mCreditCardView.setExpiryDate(expiryDate);
    }

    public void setType(int type) {
        mCreditCardView.setType(type);
    }

    public void setCardName(String name) {
        mCreditCardView.setCardName(name);
    }

    public void setCardBalance(String amount) {
        mCardBalance.setText(amount);
    }

    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mCreditCardView = findViewById(R.id.credit_card_view);
        mCardBalance = findViewById(R.id.tv_current_balance);
        mPrimaryButton = findViewById(R.id.tv_display_card_primary_bttn);
        mSecondaryButton = findViewById(R.id.tv_display_card_secondary_bttn);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mCardBalance.setTextColor(primaryColor);
        mPrimaryButton.setTextColor(contrastColor);
        mPrimaryButton.setBackgroundColor(primaryColor);
        mSecondaryButton.setTextColor(primaryColor);
    }

    private void setUpListeners() {
        if (mPrimaryButton != null) {
            mPrimaryButton.setOnClickListener(this);
        }
        if (mSecondaryButton != null) {
            mSecondaryButton.setOnClickListener(this);
        }
    }
}
