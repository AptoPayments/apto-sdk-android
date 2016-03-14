package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Displays the offers list.
 * @author Wijnand
 */
public class OffersListView extends RelativeLayout implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {

        /**
         * Called when the empty case "Update loan request" button has been pressed.
         */
        void updateClickedHandler();
    }

    private ViewListener mListener;

    private Toolbar mToolbar;
    private RecyclerView mOffersListView;
    private LinearLayout mEmptyCaseHolder;
    private TextView mUpdateButton;
    private RelativeLayout mLoadingOverlay;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersListView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public OffersListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mOffersListView = (RecyclerView) findViewById(R.id.rv_offers_list);
        mEmptyCaseHolder = (LinearLayout) findViewById(R.id.ll_empty_case);
        mUpdateButton = (TextView) findViewById(R.id.tv_bttn_edit_info);
        mLoadingOverlay = (RelativeLayout) findViewById(R.id.rl_loading_overlay);
    }

    /**
     * Sets up all child View listeners.
     */
    private void setUpListeners() {
        mUpdateButton.setOnClickListener(this);
    }

    /**
     * Sets up the {@link RecyclerView}.
     */
    private void setupRecyclerView() {
        mOffersListView.setHasFixedSize(true);
        mOffersListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();

        showEmptyCase(false);
        showLoading(false);
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener != null && view.getId() == R.id.tv_bttn_edit_info) {
            mListener.updateClickedHandler();
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener New {@link ViewListener}.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<OfferSummaryModel, OfferSummaryView> adapter) {
        mOffersListView.setAdapter(adapter);
    }

    /**
     * Updates the empty case visibility.
     * @param show Whether the empty should be shown.
     */
    public void showEmptyCase(boolean show) {
        if (show) {
            mEmptyCaseHolder.setVisibility(VISIBLE);
        } else {
            mEmptyCaseHolder.setVisibility(GONE);
        }
    }

    /**
     * Updates the loading overlay visibility.
     * @param show Whether the loading overlay be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingOverlay.setVisibility(VISIBLE);
        } else {
            mLoadingOverlay.setVisibility(GONE);
        }
    }
}
