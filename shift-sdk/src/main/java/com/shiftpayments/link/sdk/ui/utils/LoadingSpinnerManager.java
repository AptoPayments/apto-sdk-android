package com.shiftpayments.link.sdk.ui.utils;

import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;

import static com.shiftpayments.link.sdk.ui.views.LoadingView.Position.CENTER;

/**
 * Created by adrian on 26/07/2017.
 */

public class LoadingSpinnerManager {

    private LoadingView mLoadingView;

    public LoadingSpinnerManager(ViewWithIndeterminateLoading view) {
        mLoadingView = view.getLoadingView();
    }

    public void showLoading(boolean show) {
        mLoadingView.showLoading(show, CENTER, true);
    }

    public void showLoading(boolean show, LoadingView.Position position) {
        mLoadingView.showLoading(show, position, true);
    }

    public void showLoading(boolean show, LoadingView.Position position, boolean blockScreen) {
        mLoadingView.showLoading(show, position, blockScreen);
    }

    public boolean isLoading() {
        return mLoadingView.isLoading();
    }
}
