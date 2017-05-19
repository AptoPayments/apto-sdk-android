package me.ledge.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;

/**
 * Displays the plaid webview.
 * @author Adrian
 */
public class DisclaimerView extends RelativeLayout implements View.OnClickListener {

    private Button mAcceptButton;
    private ViewListener mListener;

    public DisclaimerView(Context context) {
        this(context, null);
    }
    public DisclaimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the re-send code button has been pressed.
         */
        void acceptClickHandler();
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
        setColors();
    }

    protected void setupListeners() {
        if (mAcceptButton != null) {
            mAcceptButton.setOnClickListener(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.btn_accept_pdf) {
            mListener.acceptClickHandler();
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        mAcceptButton.setBackgroundColor(primaryColor);
    }

    protected void findAllViews() {
        mAcceptButton = (Button) findViewById(R.id.btn_accept_pdf);
    }
}
