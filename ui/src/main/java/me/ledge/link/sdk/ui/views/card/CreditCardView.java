package me.ledge.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.ledge.link.sdk.api.vos.Card;
import me.ledge.link.sdk.ui.R;


/**
 * Created by adrian on 28/03/2018.
 */

public class CreditCardView extends RelativeLayout {

    private final Context mContext;

    private int mCardNumberTextColor = Color.WHITE;
    private int mCardNameTextColor = Color.WHITE;
    private int mExpiryDateTextColor = Color.WHITE;
    private int mExpiryDateLabelTextColor = Color.WHITE;
    private int mCvvTextColor = Color.WHITE;
    private int mCvvLabelColor = Color.WHITE;
    private int mCardNotEnabledLabelColor = Color.DKGRAY;
    private int mEnabledCardBackground = R.drawable.card_enabled_background;
    private int mDisabledCardBackground = R.drawable.card_disabled_background;

    private EditText mCardNumberView;
    private EditText mCardNameView;
    private EditText mExpiryDateView;
    private EditText mCvvView;

    private TextView mExpiryDateLabel;
    private TextView mCvvLabel;
    private TextView mCardNotEnabledLabel;

    private ImageView mCardLogoView;

    public CreditCardView(Context context) {
        this(context, null);
    }

    public CreditCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (context != null) {
            this.mContext = context;
        } else {
            this.mContext = getContext();
        }

        init();
    }

    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.credit_card_view, this, true);

        mCardNumberView = (EditText) findViewById(R.id.et_card_number);
        mCardNameView = (EditText) findViewById(R.id.et_card_name);
        mExpiryDateLabel = (TextView) findViewById(R.id.tv_expiration_label);
        mExpiryDateView = (EditText) findViewById(R.id.et_expiry_date);
        mCvvLabel = (TextView) findViewById(R.id.tv_cvv_label);
        mCvvView = (EditText) findViewById(R.id.et_cvv);
        mCardLogoView = (ImageView) findViewById(R.id.iv_card_logo);
        mCardNotEnabledLabel = (TextView) findViewById(R.id.tv_card_disabled_label);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        enableCard();
        setBackgroundResource(mEnabledCardBackground);
    }

    public void setExpiryDate(String expiryDate) {
        mExpiryDateView.setText(expiryDate);
    }

    public void setCardNumber(String cardNumber) {
        mCardNumberView.setText(cardNumber);
    }

    public void setCardName(String cardName) {
        mCardNameView.setText(cardName);
    }

    public void setCVV(String cvv) {
        mCvvView.setText(cvv);
    }

    public void setCardLogo(Card.CardNetwork cardNetwork) {
        switch (cardNetwork) {
            case VISA:
                mCardLogoView.setBackgroundResource(R.drawable.visa);
                break;
            case MASTERCARD:
                mCardLogoView.setBackgroundResource(R.drawable.mastercard);
                break;
            case AMEX:
                mCardLogoView.setBackgroundResource(R.drawable.amex);
                break;
            default:
                mCardLogoView.setVisibility(GONE);
        }
    }

    public void setCardEnabled(boolean enabled) {
        if(enabled) {
            enableCard();
        }
        else {
            disableCard();
        }
    }

    private void enableCard() {
        mCardNumberView.setTextColor(mCardNumberTextColor);
        mCardNameView.setTextColor(mCardNameTextColor);
        mExpiryDateView.setTextColor(mExpiryDateTextColor);
        mExpiryDateLabel.setTextColor(mExpiryDateLabelTextColor);
        mCvvView.setTextColor(mCvvTextColor);
        mCvvLabel.setTextColor(mCvvLabelColor);


        mExpiryDateView.setVisibility(VISIBLE);
        mExpiryDateLabel.setVisibility(VISIBLE);
        mCvvView.setVisibility(VISIBLE);
        mCvvLabel.setVisibility(VISIBLE);
        mCardNotEnabledLabel.setVisibility(GONE);
        setBackgroundResource(mEnabledCardBackground);
    }

    private void disableCard() {
        mCardNumberView.setTextColor(mCardNotEnabledLabelColor);
        mCardNameView.setTextColor(mCardNotEnabledLabelColor);
        mCardNotEnabledLabel.setTextColor(mCardNotEnabledLabelColor);

        mExpiryDateView.setVisibility(INVISIBLE);
        mExpiryDateLabel.setVisibility(INVISIBLE);
        mCvvView.setVisibility(INVISIBLE);
        mCvvLabel.setVisibility(INVISIBLE);
        mCardNotEnabledLabel.setVisibility(VISIBLE);
        setBackgroundResource(mDisabledCardBackground);
    }
}