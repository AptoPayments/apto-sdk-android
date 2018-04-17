package com.shift.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Displays a modal overlay with an indeterminate progress bar.
 * @author Wijnand
 */
public class LoadingView extends RelativeLayout {

    /**
     * @see RelativeLayout#RelativeLayout
     */
    public LoadingView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     */
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void showLoading(boolean show) {
        if (show) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    public boolean isLoading() {
        return this.getVisibility()==VISIBLE;
    }
}
