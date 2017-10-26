package me.ledge.link.sdk.ui.views.fundingaccountselector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaygaba.creditcardview.CreditCardView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.DisplayErrorMessage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the display card screen.
 * @author Adrian
 */
public class DisplayCardView
        extends RelativeLayout
        implements DisplayErrorMessage, ViewWithToolbar {

    private CreditCardView mCreditCardView;
    private Toolbar mToolbar;
    private TextView mCardBalance;
    private TextView mPrimaryButton;
    private TextView mSecondaryButton;

    public DisplayCardView(Context context) {
        this(context, null);
    }


    public DisplayCardView(Context context, AttributeSet attrs) {
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
        setColors();
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

    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mCreditCardView = (CreditCardView) findViewById(R.id.credit_card_view);
        mCardBalance = (TextView) findViewById(R.id.tv_card_balance);
        mPrimaryButton = (TextView) findViewById(R.id.tv_display_card_primary_bttn);
        mSecondaryButton = (TextView) findViewById(R.id.tv_display_card_secondary_bttn);
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
}
