package me.ledge.link.sdk.cardexample.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import me.ledge.link.sdk.cardexample.R;
import me.ledge.link.sdk.ui.ShiftUi;
import me.ledge.link.sdk.ui.storages.UIStorage;

/**
 * Created by adrian on 27/11/2017.
 */
public class MainView extends RelativeLayout implements View.OnClickListener {

    private Button mGetStartedButton;
    private ImageView mLogoImageView;
    private TextView mSummaryTextView;
    private ProgressBar mLoadingView;

    private ViewListener mListener;

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public MainView(Context context) {
        super(context);
    }

    /**
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs   See {@link ScrollView#ScrollView}.
     * @see ScrollView#ScrollView
     */
    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
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
        mGetStartedButton = (Button) findViewById(R.id.bttn_main_button);
        mLogoImageView = (ImageView) findViewById(R.id.iv_logo);
        mSummaryTextView = (TextView) findViewById(R.id.tv_project_summary);
        mLoadingView = (ProgressBar) findViewById(R.id.pb_loading);
    }

    /**
     * Sets up all relevant callback listeners.
     */
    private void setUpListeners() {
        mGetStartedButton.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.bttn_main_button) {
            mListener.getStartedClickedHandler();
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     *
     * @param listener The new listener.
     */
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "Get Started" button has been pressed.
         */
        void getStartedClickedHandler();
    }

    public void setLogo() {
        mLogoImageView.setImageResource(R.drawable.icon_ledge_logo);
    }

    public void setLogo(String logoURL) {
        ShiftUi.getImageLoader().load(logoURL, mLogoImageView);
    }

    public void setSummary(String summary) {
        mSummaryTextView.setText(summary);
        mSummaryTextView.setVisibility(VISIBLE);
    }

    public void showLoading(boolean show) {
        if(show) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        else {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    public void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        mGetStartedButton.setBackgroundColor(color);
        mGetStartedButton.setVisibility(VISIBLE);
    }
}
