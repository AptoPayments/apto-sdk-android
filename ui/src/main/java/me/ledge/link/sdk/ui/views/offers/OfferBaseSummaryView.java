package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.ledge.common.views.RowView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;

/**
 * Displays an offer summary.
 * @author wijnand
 */

abstract class OfferBaseSummaryView extends CardView implements RowView<OfferSummaryModel>,View.OnClickListener {
    TextView mLenderNameField;
    ImageView mLenderLogo;
    TextView mInterestField;
    TextView mAmountField;
    TextView mMonthlyPaymentField;
    TextView mMoreInfoButton;
    TextView mDisclaimerField;
    protected TextView mApplyButton;
    private ViewListener mListener;
    OfferSummaryModel mData;

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     * @param defStyleAttr See {@link CardView#CardView}.
     */
    public OfferBaseSummaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public OfferBaseSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public OfferBaseSummaryView(Context context) {
        super(context);
    }

    /**
     * Locates all child Views.
     */
    private void findAllViews() {
        mLenderNameField = (TextView) findViewById(R.id.tv_lender);
        mLenderLogo = (ImageView) findViewById(R.id.iv_lender_logo);

        mInterestField = (TextView) findViewById(R.id.tv_interest);
        mAmountField = (TextView) findViewById(R.id.tv_amount);
        mMonthlyPaymentField = (TextView) findViewById(R.id.tv_monthly);
        mMoreInfoButton = (TextView) findViewById(R.id.tv_bttn_more_info);
        mApplyButton = (TextView) findViewById(R.id.tv_apply);
        mDisclaimerField = (TextView) findViewById(R.id.tv_disclaimer);

    }

    /**
     * Sets up child View callback listeners.
     */
    private void setUpListeners() {
        mApplyButton.setOnClickListener(this);
        mMoreInfoButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_apply) {
            mListener.applyClickHandler(mData);
        } else if (id == R.id.tv_bttn_more_info) {
            mListener.moreInfoClickHandler(mData);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setData(OfferSummaryModel data) {
        mData = data;
        updateAllFields();
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {
        if (mData != null && mData.hasImageLoader() && mData.getLenderImage() != null) {
            mData.getImageLoader().cancel(mData.getLenderImage(), mLenderLogo);
        }

        mData = null;
        updateAllFields();
    }

    /**
     * Stores a new callback listener that this View will invoke.
     * @param listener New callback listener.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    protected abstract void setColors();

    /**
     * Updates all fields with the offer data OR empty Strings when no data is set.
     */
    protected abstract void updateAllFields();

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "apply now" button has been clicked.
         * @param offer The associated loan offer.
         */
        void applyClickHandler(OfferSummaryModel offer);

        void moreInfoClickHandler(OfferSummaryModel offer);
    }
}
