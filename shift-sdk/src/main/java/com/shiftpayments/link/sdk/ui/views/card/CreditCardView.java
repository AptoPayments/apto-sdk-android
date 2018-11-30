package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.images.GenericImageLoader;


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
    private ImageView mBackgroundImage;

    private EditText mCardNumberView1;
    private EditText mCardNumberView2;
    private EditText mCardNumberView3;
    private EditText mCardNumberView4;
    private EditText mCardNameView;
    private EditText mExpiryDateView;
    private EditText mCvvView;

    private TextView mExpiryDateLabel;
    private TextView mCvvLabel;

    private ImageView mCardLogoView;
    private LinearLayout mDisabledCardOverlay;
    private LinearLayout mCheckCardOverlay;
    private LinearLayout mErrorCardOverlay;

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

        mCardNumberView1 = findViewById(R.id.et_card_number_1);
        mCardNumberView2 = findViewById(R.id.et_card_number_2);
        mCardNumberView3 = findViewById(R.id.et_card_number_3);
        mCardNumberView4 = findViewById(R.id.et_card_number_4);
        mCardNameView = findViewById(R.id.et_card_name);
        mExpiryDateLabel = findViewById(R.id.tv_expiration_label);
        mExpiryDateView = findViewById(R.id.et_expiry_date);
        mCvvLabel = findViewById(R.id.tv_cvv_label);
        mCvvView = findViewById(R.id.et_cvv);
        mCardLogoView = findViewById(R.id.iv_card_logo);
        mCheckCardOverlay = findViewById(R.id.check_card_overlay);
        mDisabledCardOverlay = findViewById(R.id.card_disabled_overlay);
        mErrorCardOverlay = findViewById(R.id.card_error_overlay);
        mBackgroundImage = findViewById(R.id.iv_background_image);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setFonts();
        enableCard();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(50);
        }
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
        if (cardNumber!=null && cardNumber.length() > 12) {
            mCardNumberView1.setText(cardNumber.substring(0,4));
            mCardNumberView2.setText(cardNumber.substring(4,8));
            mCardNumberView3.setText(cardNumber.substring(8,12));
            mCardNumberView4.setText(cardNumber.substring(12,cardNumber.length()));
        }
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
        return mCardNumberView1;
    }

    public void setDefaultBackground() {
        mBackgroundImage.setImageDrawable(mContext.getDrawable(R.drawable.card_enabled_background));
    }

    public void setCardBackgroundColor(int color) {
        GradientDrawable shapeDrawable = (GradientDrawable) mBackgroundImage.getBackground();
        shapeDrawable.setColor(color);
    }

    public void setCardBackgroundImage(String imageUrl) {
        GenericImageLoader imageLoader = ShiftPlatform.getImageLoader();
        imageLoader.setResponseObserver(new GenericImageLoader.ResponseObserver() {
            @Override
            public void onSuccess() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mBackgroundImage.setClipToOutline(true);
                }
            }

            @Override
            public void onError() {
                setDefaultBackground();
            }
        });
        imageLoader.load(imageUrl, mBackgroundImage);
    }

    private void setFonts() {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/ocraextended.ttf");
        mCardNumberView1.setTypeface(typeface);
        mCardNumberView2.setTypeface(typeface);
        mCardNumberView3.setTypeface(typeface);
        mCardNumberView4.setTypeface(typeface);
        mCardNameView.setTypeface(typeface);
        mExpiryDateView.setTypeface(typeface);
        mCvvView.setTypeface(typeface);
    }

    private void enableCard() {
        mCardNumberView1.setTextColor(mCardNumberTextColor);
        mCardNumberView2.setTextColor(mCardNumberTextColor);
        mCardNumberView3.setTextColor(mCardNumberTextColor);
        mCardNumberView4.setTextColor(mCardNumberTextColor);
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
        mDisabledCardOverlay.setVisibility(GONE);
        mErrorCardOverlay.setVisibility(GONE);
        mCheckCardOverlay.setVisibility(GONE);
    }

    private void disableCard() {
        mDisabledCardOverlay.setVisibility(VISIBLE);
    }

    public void showCheckCardOverlay() {
        mCheckCardOverlay.setVisibility(VISIBLE);
    }

    public void showCardError() {
        mErrorCardOverlay.setVisibility(VISIBLE);
    }
}