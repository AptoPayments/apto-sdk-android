package me.ledge.link.sdk.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;

/**
 * Displays the disclaimer webview.
 * @author Adrian
 */
public class DisclaimerView extends RelativeLayout implements View.OnClickListener {

    private TextView mAcceptButton;
    private TextView mCancelButton;
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
        void acceptClickHandler();
        void cancelClickHandler();
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
        if (mCancelButton != null) {
            mCancelButton.setOnClickListener(this);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_accept_pdf) {
            mListener.acceptClickHandler();
        }
        else if(id == R.id.tv_cancel_pdf) {
            mListener.cancelClickHandler();
        }
    }

    private void setColors() {
        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        mAcceptButton.setTextColor(primaryColor);
    }

    protected void findAllViews() {
        mAcceptButton = (TextView) findViewById(R.id.tv_accept_pdf);
        mCancelButton = (TextView) findViewById(R.id.tv_cancel_pdf);
    }
}
