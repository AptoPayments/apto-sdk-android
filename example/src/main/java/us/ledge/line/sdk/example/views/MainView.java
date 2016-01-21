package us.ledge.line.sdk.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import us.ledge.line.sdk.example.R;

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
        void offersPressedHandler();
    }

    private Button mOffersButton;
    private ViewListener mListener;

    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mOffersButton = (Button) findViewById(R.id.bttn_get_offers);
        mOffersButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.bttn_get_offers:
                mListener.offersPressedHandler();
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
