package com.shiftpayments.link.sdk.ui.views.custodianselector;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;


/**
 * Created by adrian on 17/01/2017.
 */

public class AddCustodianListView extends CoordinatorLayout
        implements ViewWithToolbar, View.OnClickListener, ViewWithIndeterminateLoading {

    private Toolbar mToolbar;
    private TextView mSubmitButton;
    private AddCustodianListView.ViewListener mListener;
    private LoadingView mLoadingView;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public AddCustodianListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public AddCustodianListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mSubmitButton = findViewById(R.id.tv_submit_bttn);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void setupListeners() {
        if (mSubmitButton != null) {
            mSubmitButton.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_submit_bttn) {
            mListener.submitClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public interface ViewListener {
        void submitClickHandler();
    }

    /**
     * Stores a new reference to a {@link AddCustodianListView.ViewListener} that will be invoked by this View.
     * @param listener The new {@link AddCustodianListView.ViewListener} to store.
     */
    public void setViewListener(AddCustodianListView.ViewListener listener) {
        mListener = listener;
    }

    public void setColors() {
        mSubmitButton.setBackgroundColor(UIStorage.getInstance().getUiPrimaryColor());
    }
}
