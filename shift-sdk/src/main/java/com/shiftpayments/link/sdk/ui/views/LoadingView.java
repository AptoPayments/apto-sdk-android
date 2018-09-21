package com.shiftpayments.link.sdk.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shiftpayments.link.sdk.ui.R;

/**
 * Displays a modal overlay with an indeterminate progress bar.
 * @author Wijnand
 */
public class LoadingView extends RelativeLayout {

    public enum Position {
        TOP,
        CENTER,
        BOTTOM
    }

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

    public void showLoading(boolean show, Position position, boolean shouldBlockScreen) {
        if (show) {
            blockScreen(shouldBlockScreen);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout mProgressBarContainer = findViewById(R.id.ll_progress_bar_container);
            switch (position) {
                case TOP:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case CENTER:
                    layoutParams.addRule(CENTER_IN_PARENT);
                    break;
                case BOTTOM:
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM|RelativeLayout.CENTER_HORIZONTAL);
                    break;
            }
            mProgressBarContainer.setLayoutParams(layoutParams);
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
    }

    public boolean isLoading() {
        return this.getVisibility()==VISIBLE;
    }

    private void blockScreen(boolean shouldBlockScreen) {
        if(shouldBlockScreen) {
            this.setBackgroundColor(Color.parseColor("#60000000"));
            this.setClickable(true);
        }
        else {
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setClickable(false);
        }
    }
}
