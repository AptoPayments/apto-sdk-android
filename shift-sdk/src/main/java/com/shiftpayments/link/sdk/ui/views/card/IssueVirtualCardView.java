package com.shiftpayments.link.sdk.ui.views.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.shiftpayments.link.sdk.ui.R;

/**
 * Created by adrian on 27/11/2017.
 */
public class IssueVirtualCardView extends RelativeLayout {

    private ProgressBar mSpinner;

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public IssueVirtualCardView(Context context) {
        super(context);
    }

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs   See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public IssueVirtualCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSpinner = (ProgressBar) findViewById(R.id.pb_progress);
    }

    public void showLoading(boolean show) {
        if(show) {
            mSpinner.setVisibility(View.VISIBLE);
        }
        else {
            mSpinner.setVisibility(View.GONE);
        }
    }
}
