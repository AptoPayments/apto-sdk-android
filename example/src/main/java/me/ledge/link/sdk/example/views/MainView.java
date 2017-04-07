package me.ledge.link.sdk.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import me.ledge.link.sdk.example.R;

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
    private TextView mSettingsButton;

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
    }

    /**
     * Finds all references to {@link View}s.
     */
    private void findAllViews() {
        mOffersButton = (Button) findViewById(R.id.bttn_get_offers);
        mSettingsButton = (TextView) findViewById(R.id.bttn_settings);
    }

    /**
     * Sets up all relevant callback listeners.
     */
    private void setUpListeners() {
        mOffersButton.setOnClickListener(this);
        mSettingsButton.setOnClickListener(this);
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
}
