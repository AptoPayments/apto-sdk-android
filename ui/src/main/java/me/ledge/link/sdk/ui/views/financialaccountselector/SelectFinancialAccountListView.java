package me.ledge.link.sdk.ui.views.financialaccountselector;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Created by adrian on 17/01/2017.
 */

public class SelectFinancialAccountListView extends CoordinatorLayout implements ViewWithToolbar, View.OnClickListener {

    private Toolbar mToolbar;
    private LinearLayout mAccountsList;

    private SelectFinancialAccountListView.ViewListener mListener;
    private SelectFinancialAccountModel[] mData;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public SelectFinancialAccountListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public SelectFinancialAccountListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mAccountsList = (LinearLayout) findViewById(R.id.ll_select_accounts_list);
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

        SelectFinancialAccountModel model = ((SelectFinancialAccountCardView) view).getData();
        mListener.accountClickHandler(model);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    public interface ViewListener {
        /**
         * Called when one of the document cards has been clicked.
         * @param model Card data.
         */
        void accountClickHandler(SelectFinancialAccountModel model);
    }

    /**
     * Stores a new reference to a {@link SelectFinancialAccountListView.ViewListener} that will be invoked by this View.
     * @param listener The new {@link SelectFinancialAccountListView.ViewListener} to store.
     */
    public void setViewListener(SelectFinancialAccountListView.ViewListener listener) {
        mListener = listener;
    }

    /**
     * Updates this View with the latest data.
     * @param data Latest data.
     */
    public void setData(SelectFinancialAccountModel[] data) {
        mData = data;
        mAccountsList.removeAllViews();

        SelectFinancialAccountCardView view;
        for (SelectFinancialAccountModel model : data) {
            view = (SelectFinancialAccountCardView) LayoutInflater.from(getContext())
                    .inflate(R.layout.cv_select_account, mAccountsList, false);

            view.setData(model);
            view.setOnClickListener(this);
            mAccountsList.addView(view);
        }
    }
}
