package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;


public class CardWelcomeView extends LinearLayout implements View.OnClickListener {

    private CardWelcomeView.ViewListener mListener;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mActivateCardButton;
    private CreditCardView mCardView;

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void activateCardClickHandler();
    }
    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public CardWelcomeView(Context context) {
        super(context);
    }

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs   See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public CardWelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_activate_card_bttn) {
            mListener.activateCardClickHandler();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    public void setCardNumber(String number) {
        mCardView.setCardNumber(number);
    }

    public void setExpiryDate(String expiryDate) {
        mCardView.setExpiryDate(expiryDate);
    }

    public void setCardholderName(String name) {
        mCardView.setCardName(name);
    }

    public void setCvv(String cvv) {
        mCardView.setCVV(cvv);
    }

    public void setCardLogo(Card.CardNetwork cardNetwork) {
        mCardView.setCardLogo(cardNetwork);
    }

    public void showCheckCardOverlay() {
        mCardView.showCheckCardOverlay();
    }

    private void findAllViews() {
        mTitle = findViewById(R.id.tv_card_welcome_title);
        mSubtitle = findViewById(R.id.tv_card_welcome_subtitle);
        mActivateCardButton = findViewById(R.id.tv_activate_card_bttn);
        mCardView = findViewById(R.id.credit_card_view);
    }

    private void setupListeners() {
        mActivateCardButton.setOnClickListener(this);
    }

    private void setColors() {
        mTitle.setTextColor(UIStorage.getInstance().getTextPrimaryColor());
        mSubtitle.setTextColor(UIStorage.getInstance().getTextSecondaryColor());
        mActivateCardButton.setBackgroundColor(UIStorage.getInstance().getUiPrimaryColor());
    }
}
