package com.shiftpayments.link.sdk.ui.utils;

import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;

/**
 * Created by adrian on 26/07/2017.
 */

public class LoadingSpinnerManager {

    private LoadingView mLoadingView;

    public LoadingSpinnerManager(ViewWithIndeterminateLoading view) {
        mLoadingView = view.getLoadingView();
    }

    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
    }

    public boolean isLoading() {
        return mLoadingView.isLoading();
    }
}
