package me.ledge.link.sdk.ui.views.offers;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.LoadingView;

/**
 * Displays the offers list.
 * @author Wijnand
 */
public class OffersListView extends OffersBaseView {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends LoanOfferErrorView.ViewListener {

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

    private LoanOfferErrorView mErrorView;
    private LoadingView mLoadingView;

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
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mOffersListView = (RecyclerView) findViewById(R.id.rv_offers_list);
        mEmptyCaseHolder = (LinearLayout) findViewById(R.id.ll_empty_case);
        mUpdateButton = (TextView) findViewById(R.id.tv_bttn_edit_info);

        mErrorView = (LoanOfferErrorView) findViewById(R.id.ll_loan_error);
        mLoadingView = (LoadingView) findViewById(R.id.rl_loading_overlay);
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

    /**
     * Updates a {@link View}'s visibility.
     * @param show Whether the view should be shown.
     * @param view The view to update.
     */
    private void showView(boolean show, View view) {
        if (show) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
        setupRecyclerView();
        setColors();

        showEmptyCase(false);
        showError(false);
        showLoading(false);
    }

    private void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        if (mUpdateButton != null) {
            mUpdateButton.setBackgroundColor(color);
            mUpdateButton.setTextColor(contrastColor);
        }
        mToolbar.setBackgroundDrawable(new ColorDrawable(color));
        mToolbar.setTitleTextColor(contrastColor);
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

    /** {@inheritDoc} */
    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListener(OffersListPresenter offersListPresenter) {
        this.setListener((ViewListener) offersListPresenter);
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener New {@link ViewListener}.
     */
    public void setListener(ViewListener listener) {
        mListener = listener;
        mErrorView.setListener(listener);
    }

    /**
     * Stores a new {@link PagedListRecyclerAdapter} for the {@link RecyclerView} to use.
     * @param adapter The adapter to use.
     */
    public void setAdapter(PagedListRecyclerAdapter<OfferSummaryModel, OfferListSummaryView> adapter) {
        mOffersListView.setAdapter(adapter);
    }

    @Override
    public void setData(IntermediateLoanApplicationModel data) {
        mErrorView.setData(data);
    }

    /**
     * Updates the empty case visibility.
     * @param show Whether the empty case should be shown.
     */
    public void showEmptyCase(boolean show) {
        showView(show, mEmptyCaseHolder);
    }

    /**
     * Updates the error visibility.
     * @param show Whether the error should be shown.
     */
    public void showError(boolean show) {
        showView(show, mErrorView);
    }

    /** {@inheritDoc} */
    @Override
    public void showLoading(boolean show) {
        mLoadingView.showLoading(show);
    }
}
