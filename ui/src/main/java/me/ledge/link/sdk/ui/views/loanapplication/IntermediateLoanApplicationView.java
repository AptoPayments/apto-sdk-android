package me.ledge.link.sdk.ui.views.loanapplication;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the intermediate loan state.
 * @author Wijnand
 */
public class IntermediateLoanApplicationView extends RelativeLayout implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks that this View will invoke.
     */
    public interface ViewListener {

        /**
         * Called when the "Get offers" button has been clicked.
         */
        void offersClickHandler();

        /**
         * Called when the "More info" button has been clicked.
         */
        void infoClickHandler();
    }

    private Toolbar mToolbar;

    private ImageView mCloudImage;
    private TextView mExplanationField;
    private TextView mOffersButton;
    private TextView mInfoButton;

    private ViewListener mListener;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public IntermediateLoanApplicationView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public IntermediateLoanApplicationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    private void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);

        mCloudImage = (ImageView) findViewById(R.id.iv_cloud);
        mExplanationField = (TextView) findViewById(R.id.tv_explanation);
        mOffersButton = (TextView) findViewById(R.id.tv_bttn_get_offers);
        mInfoButton = (TextView) findViewById(R.id.tv_bttn_more_info);
    }

    /**
     * Sets up all required listeners.
     */
    private void setupListeners() {
        mOffersButton.setOnClickListener(this);
        mInfoButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_bttn_get_offers) {
            mListener.offersClickHandler();
        } else if (id == R.id.tv_bttn_more_info) {
            mListener.infoClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new callback listener that this View will invoke.
     * @param listener New callback listener.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * Displays the latest data.
     * @param data Data to show.
     */
    public void setData(IntermediateLoanApplicationModel data) {
        mCloudImage.setImageResource(data.getCloudImageResource());
        mExplanationField.setText(data.getExplanationText(getResources()));

        if (data.showOffersButton()) {
            mOffersButton.setVisibility(VISIBLE);
        } else {
            mOffersButton.setVisibility(GONE);
        }
    }
}
