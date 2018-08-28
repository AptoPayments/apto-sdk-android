package com.shiftpayments.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

/**
 * Created by adrian on 17/01/2017.
 */

public class SelectPendingApplicationListView extends CoordinatorLayout
        implements ViewWithToolbar, View.OnClickListener {

    private Toolbar mToolbar;
    private LinearLayout mApplicationsList;
    private TextView mNewApplicationButton;

    private SelectPendingApplicationListView.ViewListener mListener;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public SelectPendingApplicationListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public SelectPendingApplicationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_new_application_bttn) {
            mListener.newApplicationClickHandler();
        }
        else {
            SelectLoanApplicationModel model = ((SelectApplicationCardView) view).getData();
            mListener.applicationClickHandler(model);
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

    public interface ViewListener {
        /**
         * Called when one of the application has been clicked.
         * @param model Application data.
         */
        void applicationClickHandler(SelectLoanApplicationModel model);

        void newApplicationClickHandler();
    }

    /**
     * Stores a new reference to a {@link SelectPendingApplicationListView.ViewListener} that will be invoked by this View.
     * @param listener The new {@link SelectPendingApplicationListView.ViewListener} to store.
     */
    public void setViewListener(SelectPendingApplicationListView.ViewListener listener) {
        mListener = listener;
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(SelectLoanApplicationModel[] data) {
        mApplicationsList.removeAllViews();

        SelectApplicationCardView view;
        for (SelectLoanApplicationModel model : data) {
            view = (SelectApplicationCardView) LayoutInflater.from(getContext())
                    .inflate(R.layout.cv_select_application, mApplicationsList, false);

            view.setData(model);
            view.setOnClickListener(this);
            mApplicationsList.addView(view);
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        mToolbar.setBackgroundDrawable(new ColorDrawable(primaryColor));
        mToolbar.setTitleTextColor(contrastColor);
        mNewApplicationButton.setBackgroundColor(primaryColor);
        mNewApplicationButton.setTextColor(contrastColor);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mApplicationsList = findViewById(R.id.ll_select_application_list);
        mNewApplicationButton = findViewById(R.id.tv_new_application_bttn);
    }

    private void setupListeners() {
        if (mNewApplicationButton != null) {
            mNewApplicationButton.setOnClickListener(this);
        }
    }
}
