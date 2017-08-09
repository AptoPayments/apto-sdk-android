package me.ledge.link.sdk.ui.utils;

import me.ledge.link.sdk.ui.views.LoadingView;
import me.ledge.link.sdk.ui.views.ViewWithIndeterminateLoading;

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
}
