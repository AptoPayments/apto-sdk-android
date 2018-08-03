package com.shiftpayments.link.sdk.ui.views.offers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.shiftpayments.link.sdk.ui.storages.UIStorage;

/**
 * Displays an offer summary in list mode.
 * @author wijnand
 */
public class OfferListSummaryView extends OfferBaseSummaryView {

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public OfferListSummaryView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public OfferListSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     * @param defStyleAttr See {@link CardView#CardView}.
     */
    public OfferListSummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        mApplyButton.setTextColor(color);
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
            mLenderNameField.setText(mData.getLenderName());
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
                mLenderLogo.setVisibility(VISIBLE);
                mData.getImageLoader().load(mData.getLenderImage(), mLenderLogo);
            } else {
                mLenderLogo.setVisibility(GONE);
            }
        }
    }

}
