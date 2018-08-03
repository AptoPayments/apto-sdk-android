package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.card.FundingSourceModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;

/**
 * Displays the manage account screen.
 *
 * @author Adrian
 */
public class ManageAccountView
        extends CoordinatorLayout implements View.OnClickListener, ViewWithToolbar {

    private Toolbar mToolbar;
    private ViewListener mListener;
    private RecyclerView mFundingSourcesListView;
    private TextView mFundingSourceLabel;
    private TextView mSignOutButton;
    private LinearLayout mAddFundingSourceHolder;
    private ImageButton mAddFundingSourceButton;
    private TextView mContactSupportButton;
    private TextView mSpendableAmount;
    private TextView mSpendableAmountLabel;

    public ManageAccountView(Context context) {
        this(context, null);
    }

    public ManageAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }
        int id = view.getId();

        if (id == R.id.tv_sign_out) {
            mListener.signOut();
        } else if (id == R.id.ib_add_funding_source) {
            mListener.addFundingSource();
        } else if (id == R.id.tv_contact_support) {
            mListener.contactSupport();
        } else if (id == R.id.toolbar) {
            mListener.onBack();
        }
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();
        setColors();
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    public void showFundingSourceLabel(boolean show) {
        if (show) {
            mFundingSourceLabel.setVisibility(VISIBLE);
        } else {
            mFundingSourceLabel.setVisibility(GONE);
        }
    }

    public void showSpendableAmountLabel(boolean show) {
        if (show) {
            mSpendableAmountLabel.setVisibility(VISIBLE);
        } else {
            mSpendableAmountLabel.setVisibility(GONE);
        }
    }

    public void setSpendableAmount(String amount) {
        mSpendableAmount.setText(amount);
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<FundingSourceModel, FundingSourceView> adapter) {
        mFundingSourcesListView.setAdapter(adapter);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {

        void signOut();
        void addFundingSource();
        void contactSupport();
        void onBack();
    }

    public void showAddFundingSourceButton(boolean show) {
        if(show) {
            mAddFundingSourceHolder.setVisibility(VISIBLE);
        }
        else {
            mAddFundingSourceHolder.setVisibility(GONE);
        }
    }

    private void findAllViews() {
        mFundingSourcesListView = findViewById(R.id.rv_funding_sources_list);
        mFundingSourceLabel = findViewById(R.id.tv_funding_sources);
        mSignOutButton = findViewById(R.id.tv_sign_out);
        mAddFundingSourceButton = findViewById(R.id.ib_add_funding_source);
        mAddFundingSourceHolder = findViewById(R.id.ll_add_funding_source);
        mContactSupportButton = findViewById(R.id.tv_contact_support);
        mToolbar = findViewById(R.id.toolbar);
        mSpendableAmount = findViewById(R.id.tv_spendable_amount);
        mSpendableAmountLabel = findViewById(R.id.tv_spendable_amount_label);
    }

    private void setUpListeners() {
        mSignOutButton.setOnClickListener(this);
        mAddFundingSourceButton.setOnClickListener(this);
        mContactSupportButton.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(this);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mFundingSourcesListView.setHasFixedSize(true);
        mFundingSourcesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        mSpendableAmount.setTextColor(primaryColor);
        mAddFundingSourceButton.setColorFilter(primaryColor);
        mSignOutButton.setBackgroundColor(primaryColor);
        Drawable backArrow = ContextCompat.getDrawable(getContext(), R.drawable.abc_ic_ab_back_material);
        int actionBarColor = getResources().getColor(R.color.llsdk_actionbar_background);
        mToolbar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        mToolbar.setNavigationIcon(backArrow);
    }
}
