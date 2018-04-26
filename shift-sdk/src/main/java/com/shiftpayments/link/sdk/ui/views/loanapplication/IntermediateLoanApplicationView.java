package com.shiftpayments.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.views.offers.LoanOfferErrorView;
import com.shiftpayments.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;

/**
 * Displays the intermediate loan state.
 * @author Wijnand
 */
public class IntermediateLoanApplicationView extends RelativeLayout implements ViewWithToolbar {

    private Toolbar mToolbar;
    private LoanOfferErrorView mErrorView;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public IntermediateLoanApplicationView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public IntermediateLoanApplicationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mErrorView = (LoanOfferErrorView) findViewById(R.id.ll_loan_error);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setColors();
    }

    private void setColors() {
        mToolbar.setBackgroundDrawable(new ColorDrawable(UIStorage.getInstance().getPrimaryColor()));
        mToolbar.setTitleTextColor(UIStorage.getInstance().getPrimaryContrastColor());
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new callback listener that this View will invoke.
     * @param listener New callback listener.
     */
    public void setListener(LoanOfferErrorView.ViewListener listener) {
        mErrorView.setListener(listener);
    }

    /**
     * Displays the latest data.
     * @param data Data to show.
     */
    public void setData(IntermediateLoanApplicationModel data) {
        mErrorView.setData(data);
    }
}
