package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.card.BalanceModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.NoFundingSourcesView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.EmptyRecyclerView;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;


/**
 * Displays the card settings
 * @author Adrian
 */
public class CardSettingsView extends CoordinatorLayout implements ViewWithToolbar,
        View.OnClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener,
        ViewWithIndeterminateLoading {

    private static boolean mIsShowCardInfoSwitchTouched;
    private static boolean mIsEnableCardSwitchTouched;
    private ViewListener mListener;
    private Toolbar mToolbar;
    private EmptyRecyclerView mFundingSourcesListView;
    private NoFundingSourcesView mNoFundingSourcesView;
    private TextView mFundingSourceLabel;
    private TextView mAddFundingSourceButton;
    private FrameLayout mPinView;
    private TextView mChangePinButton;
    private TextView mContactSupportButton;
    private SwitchCompat mShowCardInfoSwitch;
    private SwitchCompat mEnableCardSwitch;
    private TextView mFaqLabel;
    private TextView mFaqButton;
    private TextView mCardholderAgreementLabel;
    private TextView mCardholderAgreementButton;
    private TextView mTermsAndConditionsLabel;
    private TextView mTermsAndConditionsButton;
    private TextView mPrivacyPolicyLabel;
    private TextView mPrivacyPolicyButton;
    private LoadingView mLoadingView;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (mListener == null) {
            return;
        }
        int id = compoundButton.getId();

        if (id == R.id.sw_show_card_info) {
            if (mIsShowCardInfoSwitchTouched) {
                mIsShowCardInfoSwitchTouched = false;
                mListener.showCardInfoClickHandler(isChecked);
            }
        } else if (id == R.id.sw_enable_card) {
            if (mIsEnableCardSwitchTouched) {
                mIsEnableCardSwitchTouched = false;
                mListener.disableCardClickHandler(isChecked);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.sw_show_card_info) {
            mIsShowCardInfoSwitchTouched = true;
        } else if (view.getId() == R.id.sw_enable_card) {
            mIsEnableCardSwitchTouched = true;
        }
        return false;
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        void addFundingSource();
        void changePinClickHandler();
        void contactSupportClickHandler();
        void showCardInfoClickHandler(boolean show);
        void disableCardClickHandler(boolean disable);
        void faqClickHandler();
        void cardholderAgreementClickHandler();
        void termsAndConditionsClickHandler();
        void privacyPolicyClickHandler();
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     */
    public CardSettingsView(Context context) {
        super(context);
    }

    /**
     * @see CardView#CardView
     * @param context See {@link CardView#CardView}.
     * @param attrs See {@link CardView#CardView}.
     */
    public CardSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setupRecyclerView();
        setColors();
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_add_funding_source_button) {
            mListener.addFundingSource();
        } else if (id == R.id.tv_change_pin) {
            mListener.changePinClickHandler();
        } else if (id == R.id.tv_report_stolen_card) {
            mListener.contactSupportClickHandler();
        } else if (id == R.id.tv_cardholder_agreement || id == R.id.tv_cardholder_agreement_description) {
            mListener.cardholderAgreementClickHandler();
        } else if (id == R.id.tv_terms_and_conditions || id == R.id.tv_terms_and_conditions_description) {
            mListener.termsAndConditionsClickHandler();
        } else if (id == R.id.tv_privacy_policy || id == R.id.tv_privacy_policy_description) {
            mListener.privacyPolicyClickHandler();
        } else if (id == R.id.tv_faq || id == R.id.tv_faq_description) {
            mListener.faqClickHandler();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<BalanceModel, FundingSourceView> adapter) {
        mFundingSourcesListView.setAdapter(adapter);
    }

    public void showFundingSourceLabel(boolean show) {
        if (show) {
            mFundingSourceLabel.setVisibility(VISIBLE);
        } else {
            mFundingSourceLabel.setVisibility(GONE);
        }
    }

    public void showAddFundingSourceButton(boolean show) {
        if(show) {
            mAddFundingSourceButton.setVisibility(VISIBLE);
        }
        else {
            mAddFundingSourceButton.setVisibility(GONE);
        }
    }

    public void showPinFragment(boolean show) {
        if(show) {
            mPinView.setVisibility(VISIBLE);
        }
        else {
            mPinView.setVisibility(GONE);
        }
    }

    public void setShowCardInfoSwitch(boolean enable) {
        mShowCardInfoSwitch.setChecked(enable);
    }

    public void setEnableCardSwitch(boolean enable) {
        mEnableCardSwitch.setChecked(enable);
    }

    public void showFaq(boolean show) {
        if(show) {
            mFaqButton.setVisibility(VISIBLE);
            mFaqLabel.setVisibility(VISIBLE);
        }
        else {
            mFaqButton.setVisibility(GONE);
            mFaqLabel.setVisibility(GONE);
        }
    }

    public void showCardholderAgreement(boolean show) {
        if(show) {
            mCardholderAgreementLabel.setVisibility(VISIBLE);
            mCardholderAgreementButton.setVisibility(VISIBLE);
        }
        else {
            mCardholderAgreementLabel.setVisibility(GONE);
            mCardholderAgreementButton.setVisibility(GONE);
        }
    }

    public void showTermsAndConditions(boolean show) {
        if(show) {
            mTermsAndConditionsLabel.setVisibility(VISIBLE);
            mTermsAndConditionsButton.setVisibility(VISIBLE);
        }
        else {
            mTermsAndConditionsLabel.setVisibility(GONE);
            mTermsAndConditionsButton.setVisibility(GONE);
        }
    }

    public void showPrivacyPolicy(boolean show) {
        if(show) {
            mPrivacyPolicyLabel.setVisibility(VISIBLE);
            mPrivacyPolicyButton.setVisibility(VISIBLE);
        }
        else {
            mPrivacyPolicyLabel.setVisibility(GONE);
            mPrivacyPolicyButton.setVisibility(GONE);
        }
    }

    private void setupListeners() {
        mAddFundingSourceButton.setOnClickListener(this);
        mChangePinButton.setOnClickListener(this);
        mContactSupportButton.setOnClickListener(this);
        mShowCardInfoSwitch.setOnCheckedChangeListener(this);
        mShowCardInfoSwitch.setOnTouchListener(this);
        mEnableCardSwitch.setOnCheckedChangeListener(this);
        mEnableCardSwitch.setOnTouchListener(this);
        mCardholderAgreementLabel.setOnClickListener(this);
        mCardholderAgreementButton.setOnClickListener(this);
        mTermsAndConditionsLabel.setOnClickListener(this);
        mTermsAndConditionsButton.setOnClickListener(this);
        mPrivacyPolicyLabel.setOnClickListener(this);
        mPrivacyPolicyButton.setOnClickListener(this);
        mFaqLabel.setOnClickListener(this);
        mFaqButton.setOnClickListener(this);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.toolbar);
        mFundingSourcesListView = findViewById(R.id.rv_funding_sources_list);
        mFundingSourceLabel = findViewById(R.id.tv_funding_sources_header);
        mNoFundingSourcesView = findViewById(R.id.ll_no_funding_sources);
        mAddFundingSourceButton = findViewById(R.id.tv_add_funding_source_button);
        mPinView = findViewById(R.id.pin_fragment);
        mChangePinButton = findViewById(R.id.tv_change_pin);
        mContactSupportButton = findViewById(R.id.tv_report_stolen_card);
        mShowCardInfoSwitch = findViewById(R.id.sw_show_card_info);
        mEnableCardSwitch = findViewById(R.id.sw_enable_card);
        mFaqLabel = findViewById(R.id.tv_faq);
        mFaqButton = findViewById(R.id.tv_faq_description);
        mCardholderAgreementLabel = findViewById(R.id.tv_cardholder_agreement);
        mCardholderAgreementButton = findViewById(R.id.tv_cardholder_agreement_description);
        mTermsAndConditionsLabel = findViewById(R.id.tv_terms_and_conditions);
        mTermsAndConditionsButton = findViewById(R.id.tv_terms_and_conditions_description);
        mPrivacyPolicyLabel = findViewById(R.id.tv_privacy_policy);
        mPrivacyPolicyButton = findViewById(R.id.tv_privacy_policy_description);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        Drawable closeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_close);
        closeIcon.setColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(closeIcon);
        mAddFundingSourceButton.setBackgroundColor(UIStorage.getInstance().getUiPrimaryColor());
        ColorStateList foregroundColors = UIStorage.getInstance().getSwitchForegroundColors();
        ColorStateList backgroundColors = UIStorage.getInstance().getSwitchBackgroundColors();
        DrawableCompat.setTintList(DrawableCompat.wrap(mShowCardInfoSwitch.getThumbDrawable()), foregroundColors);
        DrawableCompat.setTintList(DrawableCompat.wrap(mShowCardInfoSwitch.getTrackDrawable()), backgroundColors);
        DrawableCompat.setTintList(DrawableCompat.wrap(mEnableCardSwitch.getThumbDrawable()), foregroundColors);
        DrawableCompat.setTintList(DrawableCompat.wrap(mEnableCardSwitch.getTrackDrawable()), backgroundColors);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFundingSourcesListView.setEmptyView(mNoFundingSourcesView);
    }
}
