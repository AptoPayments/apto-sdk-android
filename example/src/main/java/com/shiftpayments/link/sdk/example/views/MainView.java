package com.shiftpayments.link.sdk.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shiftpayments.link.sdk.example.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;

/**
 * Displays the main View.
 * @author Wijnand
 */
public class MainView extends RelativeLayout implements View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "Get loan offers" button has been pressed.
         */
        void offersClickedHandler();

        /**
         * Called when the "Settings" button has been pressed.
         */
        void settingsClickedHandler();
    }

    private Button mOffersButton;
    private ImageView mSettingsButton;
    private ImageView mLogoImageView;
    private TextView mSummaryTextView;
    private LoadingView mLoadingView;

    private ViewListener mListener;

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     */
    public MainView(Context context) {
        super(context);
    }

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs See {@link ScrollView#ScrollView}.
     */
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setColors();
    }

    /**
     * Finds all references to {@link View}s.
     */
    private void findAllViews() {
        mOffersButton = (Button) findViewById(R.id.bttn_get_offers);
        mSettingsButton = (ImageView) findViewById(R.id.bttn_settings);
        mLogoImageView = (ImageView) findViewById(R.id.iv_logo);
        mSummaryTextView = (TextView) findViewById(R.id.tv_project_summary);
        mLoadingView = (LoadingView) findViewById(com.shiftpayments.link.sdk.ui.R.id.rl_loading_overlay);
    }

    /**
     * Sets up all relevant callback listeners.
     */
    private void setUpListeners() {
        mOffersButton.setOnClickListener(this);
        mSettingsButton.setOnClickListener(this);
    }

    public void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        mOffersButton.setBackgroundColor(color);
        mOffersButton.setVisibility(VISIBLE);
    }

    public void setLogo() {
        mLogoImageView.setImageResource(R.drawable.icon_ledge_logo);
    }

    public void setLogo(String logoURL) {
        ShiftPlatform.getImageLoader().load(logoURL, mLogoImageView);
    }

    public void setSummary(String summary) {
        mSummaryTextView.setText(summary);
        mSummaryTextView.setVisibility(VISIBLE);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.bttn_get_offers:
                mListener.offersClickedHandler();
                break;
            case R.id.bttn_settings:
                mListener.settingsClickedHandler();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener The new listener.
     */
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
    }
}
