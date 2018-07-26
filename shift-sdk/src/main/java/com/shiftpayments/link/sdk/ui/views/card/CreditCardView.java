package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.ui.R;


/**
 * Created by adrian on 28/03/2018.
 */

public class CreditCardView extends RelativeLayout {

    private final Context mContext;
    private final static double CARD_ASPECT_RATIO = 1.586;

    private final static int mCardNumberTextColor = Color.WHITE;
    private final static int mCardNameTextColor = Color.WHITE;
    private final static int mExpiryDateTextColor = Color.WHITE;
    private final static int mExpiryDateLabelTextColor = Color.WHITE;
    private final static int mCvvTextColor = Color.WHITE;
    private final static int mCvvLabelColor = Color.WHITE;
    private final static int mCardNotEnabledLabelColor = Color.DKGRAY;
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

        mCardNumberView = findViewById(R.id.et_card_number);
        mCardNameView = findViewById(R.id.et_card_name);
        mExpiryDateLabel = findViewById(R.id.tv_expiration_label);
        mExpiryDateView = findViewById(R.id.et_expiry_date);
        mCvvLabel = findViewById(R.id.tv_cvv_label);
        mCvvView = findViewById(R.id.et_cvv);
        mCardLogoView = findViewById(R.id.iv_card_logo);
        mCardNotEnabledLabel = findViewById(R.id.tv_card_disabled_label);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        enableCard();
        setBackgroundResource(mEnabledCardBackground);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width / CARD_ASPECT_RATIO);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
                mCardLogoView.setBackgroundResource(R.drawable.ic_visa_logo);
                break;
            case MASTERCARD:
                mCardLogoView.setBackgroundResource(R.drawable.ic_mastercard_logo);
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

    public EditText getCardNumberView() {
        return mCardNumberView;
    }

    private void enableCard() {
        mCardNumberView.setTextColor(mCardNumberTextColor);
        mCardNameView.setTextColor(mCardNameTextColor);
        mExpiryDateView.setTextColor(mExpiryDateTextColor);
        mExpiryDateLabel.setTextColor(mExpiryDateLabelTextColor);
        mCvvView.setTextColor(mCvvTextColor);
        mCvvLabel.setTextColor(mCvvLabelColor);
        if(mCardLogoView.getBackground() != null) {
            mCardLogoView.getBackground().setColorFilter(getResources().getColor(R.color.enabled_card_logo), PorterDuff.Mode.SRC_ATOP);
        }

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
        if(mCardLogoView.getBackground() != null) {
            mCardLogoView.getBackground().setColorFilter(getResources().getColor(R.color.disabled_card_logo), PorterDuff.Mode.SRC_ATOP);
        }

        mExpiryDateView.setVisibility(INVISIBLE);
        mExpiryDateLabel.setVisibility(INVISIBLE);
        mCvvView.setVisibility(INVISIBLE);
        mCvvLabel.setVisibility(INVISIBLE);
        mCardNotEnabledLabel.setVisibility(VISIBLE);
        setBackgroundResource(mDisabledCardBackground);
    }
}