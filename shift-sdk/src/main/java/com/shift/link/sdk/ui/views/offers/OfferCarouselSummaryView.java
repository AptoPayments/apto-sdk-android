package com.shift.link.sdk.ui.views.offers;

import android.content.Context;
import android.util.AttributeSet;

import com.shift.link.sdk.ui.storages.UIStorage;

/**
 * Displays an offer summary in carousel mode.
 * @author Adrian
 */
public class OfferCarouselSummaryView extends OfferBaseSummaryView {

    public OfferCarouselSummaryView(Context context) {
        super(context);
    }

    public OfferCarouselSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfferCarouselSummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        mApplyButton.setBackgroundColor(color);
    }

    @Override
    protected void updateAllFields() {
        mMoreInfoButton.setVisibility(GONE);

        if (mData == null) {
            mLenderNameField.setText("");
            mLenderLogo.setImageResource(android.R.color.transparent);
            mInterestField.setText("");
            mAmountField.setText("");
            mMonthlyPaymentField.setText("");
        } else {
            if(!mData.isTextOnlyOffer()) {
                mInterestField.setText(mData.getInterestText());
                mAmountField.setText(mData.getAmountText());
                mMonthlyPaymentField.setText(mData.getMonthlyPaymentText());

                if (mData.hasDisclaimer()) {
                    mMoreInfoButton.setVisibility(VISIBLE);
                }
            }
            else {
                mInterestField.setVisibility(GONE);
                mAmountField.setVisibility(GONE);
                mMonthlyPaymentField.setVisibility(GONE);
                mDisclaimerField.setVisibility(VISIBLE);
                mDisclaimerField.setText(mData.getDisclaimer());
            }

            if (mData.hasImageLoader() && mData.getLenderImage() != null) {
                mLenderNameField.setVisibility(GONE);
                mLenderLogo.setVisibility(VISIBLE);
                mData.getImageLoader().load(mData.getLenderImage(), mLenderLogo);
            } else {
                mLenderNameField.setText(mData.getLenderName());
                mLenderLogo.setVisibility(GONE);
            }
        }
    }
}
